package com.coffeeinfinitive.exception;

import com.coffeeinfinitive.constants.ResultCode;
import com.google.gson.JsonObject;
import org.hibernate.loader.custom.ResultRowProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jinz on 4/28/17.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CoffeeSystemErrorException extends RuntimeException{

    private String detail;

    public CoffeeSystemErrorException(String msg, Throwable t) {
        super(msg, t);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code", ResultCode.INTERNAL_SYSTEM_ERROR.getCode());
        jsonObject.addProperty("message", ResultCode.INTERNAL_SYSTEM_ERROR.getMessageVn());
        jsonObject.addProperty("detail", t.getMessage());
        this.detail = jsonObject.toString();

    }

    public CoffeeSystemErrorException(String msg, JsonObject jsonObject, Throwable t) {
        super(msg, t);
        this.detail = jsonObject.toString();

    }


    @Override
    public String getMessage() {
        return this.detail;
    }
}
