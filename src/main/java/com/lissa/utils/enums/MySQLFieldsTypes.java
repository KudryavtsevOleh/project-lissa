package com.lissa.utils.enums;

import com.lissa.utils.Messages;
import com.lissa.utils.exceptions.InvalidFieldTypeException;

public enum MySQLFieldsTypes {
    INT("Integer"),
    VARCHAR("String"),
    TIMESTAMP("Date"),
    TINYINT("Boolean");

    private String value;

    private MySQLFieldsTypes(String value) {
        this.value = value;
    }

    public static String valueFrom(String type) /*throws InvalidFieldTypeException*/ {
        if (INT.value.equals(type)) {
            return "INT";
        } else if (VARCHAR.value.equals(type)) {
            return "VARCHAR(255)";
        } else if (TIMESTAMP.value.equals(type)) {
            return "TIMESTAMP";
        } else if (TINYINT.value.equals(type)) {
            return "TINYINT(1)";
        } /*else {
            throw new InvalidFieldTypeException(Messages.INVALID_FIELD_TYPE, type);
        }*/
        return "";
    }

}
