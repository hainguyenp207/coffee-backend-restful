package com.coffeeinfinitive.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by jinz on 4/28/17.
 */
public class CoffeeException extends Exception{
    private int code;
    public CoffeeException(String msg, Throwable t) {
        super(msg, t);
    }

    public CoffeeException(int code, String msg ) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
