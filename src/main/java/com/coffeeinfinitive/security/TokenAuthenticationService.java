package com.coffeeinfinitive.security;

import com.coffeeinfinitive.constants.ResultCode;
import com.coffeeinfinitive.dao.entity.OrgUser;
import com.coffeeinfinitive.dao.entity.User;
import com.coffeeinfinitive.exception.CoffeeAuthException;
import com.coffeeinfinitive.model.OrganizationForm;
import com.coffeeinfinitive.model.RoleForm;
import com.coffeeinfinitive.model.UserForm;
import com.coffeeinfinitive.model.UserOrgDetailForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jinz on 4/27/17.
 */
@Service
public class TokenAuthenticationService {
    private static final String TOKEN_PREFIX = "X-Token";
    public static final String JWT_TOKEN_HEADER_PARAM = "X-Authorization";
    private final JwtTokenUtil jwtTokenUtil;
    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    public TokenAuthenticationService(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public void addJwtTokenToHeader(HttpServletResponse response, User user) {
        response.addHeader("access-control-expose-headers",JWT_TOKEN_HEADER_PARAM);
        response.addHeader(JWT_TOKEN_HEADER_PARAM,TOKEN_PREFIX +" "+ jwtTokenUtil.generateToken(user));
    }
    public void addDataToBody(HttpServletResponse response,
                              User user, Set<OrgUser> orgUsers) throws Exception{


        UserForm userForm = new UserForm();
        userForm.setName(user.getName());

        Set<UserOrgDetailForm> userOrgDetailForms = new HashSet<>();
        orgUsers.forEach(orgUser -> {
            OrganizationForm organizationForm = new OrganizationForm(false,orgUser.getOrganization().getId(), orgUser.getOrganization().getName());
            RoleForm roleForm = new RoleForm(orgUser.getRole().getId(), orgUser.getRole().getName());
            UserOrgDetailForm userOrgDetailForm = new UserOrgDetailForm(organizationForm,roleForm);
            userOrgDetailForms.add(userOrgDetailForm);
        });

        userForm.setUserOrgDetailForms(userOrgDetailForms);
        userForm.setEmail(user.getEmail());
        userForm.setSex(user.isSex());
        userForm.setUsername(user.getUsername());
        userForm.setAddress(user.getAddress());

        Gson gson = new Gson();
        gson.toJson(userForm,userForm.getClass(),response.getWriter());
    }

    public Authentication generateAuthenticationFromRequest(HttpServletRequest request) {
        String token = request.getHeader(JWT_TOKEN_HEADER_PARAM);
        if (token == null || token.isEmpty())
            return null;
        token = token.replace(TOKEN_PREFIX+" ","");
        User user = jwtTokenUtil
                .getUserFromToken(token);

        return new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(),null);
    }

}
