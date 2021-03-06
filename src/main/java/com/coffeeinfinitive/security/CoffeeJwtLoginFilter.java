package com.coffeeinfinitive.security;

import com.coffeeinfinitive.constants.ResultCode;
import com.coffeeinfinitive.dao.entity.OrgUser;
import com.coffeeinfinitive.dao.entity.User;
import com.coffeeinfinitive.exception.CoffeeAuthException;
import com.coffeeinfinitive.service.UserOrgService;
import com.coffeeinfinitive.service.UserService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Created by jinz on 4/27/17.
 */
public class CoffeeJwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final TokenAuthenticationService tokenAuthenticationService;
    private final UserService userService;
    private final UserOrgService userOrgService;

    public CoffeeJwtLoginFilter(String urlMapping, TokenAuthenticationService tokenAuthenticationService,
                                UserService userService, AuthenticationManager authenticationManager, UserOrgService userOrgService) {
        super(urlMapping);
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.userService = userService;
        this.userOrgService = userOrgService;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {
        try {
            CoffeeJwtAuthenticationRequest authRequest = toAuthRequest(req);
            if (authRequest.getUsername() == null || authRequest.getUsername().isEmpty()) {
                throw new CoffeeAuthException(ResultCode.USERNAME_INVALID_REQUIRED.getCode(), ResultCode.USERNAME_INVALID_REQUIRED.getMessageVn());
            }
            if (authRequest.getPassword() == null || authRequest.getPassword().isEmpty()) {
                throw new CoffeeAuthException(ResultCode.PASSWORD_INVALID_REQUIRED.getCode(), ResultCode.PASSWORD_INVALID_REQUIRED.getMessageVn());
            }
            final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword(), null);
            try {
                return getAuthenticationManager().authenticate(loginToken);
            } catch (CoffeeAuthException e) {
                return null;
            }
        } catch (JsonParseException e) {
            throw new CoffeeAuthException(ResultCode.PARSE_JSON_TO_OBJECT.getCode(), ResultCode.PARSE_JSON_TO_OBJECT.getMessageVn());
        }

    }

    private CoffeeJwtAuthenticationRequest toAuthRequest(HttpServletRequest request) throws IOException {
        return new ObjectMapper().readValue(request.getInputStream(), CoffeeJwtAuthenticationRequest.class);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {

        final User authenticatedUser = userService.findUserById(auth.getName());
        final Set<OrgUser> orgUsers = userOrgService.getUserOrgByUsername(auth.getName());
        res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        tokenAuthenticationService.addJwtTokenToHeader(res, authenticatedUser);
        User currentUser = userService.findUserById(auth.getName());
        try {
            tokenAuthenticationService.addDataToBody(res, currentUser, orgUsers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(),
                null));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException e) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        JsonObject result = new JsonObject();
        if (e instanceof CoffeeAuthException) {
            result.addProperty("code", ((CoffeeAuthException) e).getCode());

        }
        if (e.getMessage().equals("Bad credentials")) {
            result.addProperty("message", ResultCode.BAD_CREDENTIAL.getMessageVn());
        } else
            result.addProperty("message", e.getMessage());
        new Gson().toJson(result, response.getWriter());
    }
}
