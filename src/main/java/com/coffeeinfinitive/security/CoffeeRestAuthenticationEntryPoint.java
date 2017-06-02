package com.coffeeinfinitive.security;

import com.coffeeinfinitive.constants.ResultCode;
import com.coffeeinfinitive.exception.CoffeeAuthException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jinz on 5/1/17.
 */
@Component( "restAuthenticationEntryPoint")
public class CoffeeRestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Chưa xác thực");
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        JsonObject result = new JsonObject();
        result.addProperty("code", HttpServletResponse.SC_UNAUTHORIZED);
        result.addProperty("message", "Bạn chưa đăng nhập, vui lòng đăng nhập lại");
        new Gson().toJson(result,response.getWriter());
    }
}
