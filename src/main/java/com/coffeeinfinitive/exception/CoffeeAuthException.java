package com.coffeeinfinitive.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by jinz on 4/28/17.
 */
public class CoffeeAuthException extends AuthenticationException{
    private int code;
    public CoffeeAuthException(String msg, Throwable t) {
        super(msg, t);
    }

    public CoffeeAuthException(int code, String msg ) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
