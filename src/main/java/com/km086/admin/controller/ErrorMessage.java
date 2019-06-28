package com.km086.admin.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ErrorMessage {

    private final Date timestamp;
    private final int status;

    private final String exception;

    private final String message;

    public ErrorMessage(Date timestamp, int status, String exception, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.exception = exception;
        this.message = message;
    }

}

