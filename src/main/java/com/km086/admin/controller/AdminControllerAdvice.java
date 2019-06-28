package com.km086.admin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@ControllerAdvice
public class AdminControllerAdvice {
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage errorResponse(Exception exception) {
        return new ErrorMessage(new Date(), HttpStatus.BAD_REQUEST.value(), exception.getClass().getName(), exception.getMessage());
    }

    @ExceptionHandler({AuthenticationException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorMessage handleAuthenticationException(Exception exception) {
        return new ErrorMessage(new Date(), HttpStatus.FORBIDDEN.value(), exception.getClass().getName(), exception.getMessage());
    }
}

