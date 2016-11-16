package com.lissa.utils.exceptions;

import lombok.Getter;

@Getter
public class InvalidFieldTypeException extends Exception {

    private String type;

    public InvalidFieldTypeException(String message, String type) {
        super(message);
        this.type = type;
    }

    public String getReason() {
        return String.format(super.getMessage(), this.type);
    }
}
