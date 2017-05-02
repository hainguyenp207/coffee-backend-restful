package com.coffeeinfinitive.security;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.coffeeinfinitive.exception.CoffeeAuthException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jinz on 4/27/17.
 */
public class CoffeeJwtAuthenticationFilter extends GenericFilterBean {

    private final ObjectMapper mapper;
    private final TokenAuthenticationService tokenAuthenticationService;

    public CoffeeJwtAuthenticationFilter(ObjectMapper mapper, TokenAuthenticationService tokenAuthenticationService) {
        this.mapper = mapper;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            Authentication authentication = tokenAuthenticationService.generateAuthenticationFromRequest((HttpServletRequest) req);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, res);
            SecurityContextHolder.getContext().setAuthentication(null);
        } catch (AuthenticationException | JWTCreationException e) {
            SecurityContextHolder.clearContext();
            ((HttpServletResponse) res).setStatus(HttpStatus.UNAUTHORIZED.value());
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);

            JsonObject result = new JsonObject();
            if (e instanceof CoffeeAuthException) {
                result.addProperty("code",((CoffeeAuthException) e).getCode());
            }
            result.addProperty("message", e.getMessage());
            mapper.writeValue(res.getWriter(), result.toString());
        }
        catch (ServletException e){
            e.printStackTrace();
        }
    }
}
