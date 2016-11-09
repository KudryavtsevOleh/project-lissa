package com.lissa.utils.exceptions;

import lombok.Getter;

public class InvalidDbTypeException extends Exception {

    @Getter
    private String fullMessage;

    public InvalidDbTypeException(String message, String dbName) {
        super(message);
        this.fullMessage = message + dbName;
    }

}
