package com.coffeeinfinitive.security;

import com.coffeeinfinitive.constants.ResultCode;
import com.coffeeinfinitive.dao.entity.User;
import com.coffeeinfinitive.exception.CoffeeAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jinz on 4/27/17.
 */
@Service
public class TokenAuthenticationService {
    private static final String TOKEN_PREFIX = "x-token";
    public static final String JWT_TOKEN_HEADER_PARAM = "X-Authorization";
    private final JwtTokenUtil jwtTokenUtil;
    @Autowired
    public TokenAuthenticationService(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public void addJwtTokenToHeader(HttpServletResponse response,
                                    UserDetails user) {
        response.addHeader(JWT_TOKEN_HEADER_PARAM,TOKEN_PREFIX +" "+ jwtTokenUtil.generateToken(user));
    }

    public Authentication generateAuthenticationFromRequest(HttpServletRequest request) {
        String token = request.getHeader(JWT_TOKEN_HEADER_PARAM);
        if (token == null || token.isEmpty())
            return null;
        token = token.replace(TOKEN_PREFIX+" ","");
        User user = jwtTokenUtil
                .getUserFromToken(token);
        return new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(),user.getAuthorities());
    }

}
