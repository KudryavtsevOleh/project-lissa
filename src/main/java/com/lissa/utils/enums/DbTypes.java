package com.lissa.utils.enums;

import com.lissa.utils.exceptions.InvalidDbTypeException;
import org.apache.commons.lang3.StringUtils;

public enum DbTypes {
    MYSQL("mysql");

    private String value;

    private DbTypes(String value) {
        this.value = value;
    }

    public static DbTypes fromValue(String value) throws InvalidDbTypeException {
        if (StringUtils.isEmpty(value)) {
            throw new InvalidDbTypeException("Db type cannot be empty", value);
        }
        if ("mysql".equals(value)) {
            return DbTypes.MYSQL;
        } else {
            throw new InvalidDbTypeException("Unsupported database: ", value);
        }
    }

}
