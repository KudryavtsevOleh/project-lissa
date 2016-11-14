package com.lissa.utils.enums;

public enum DbTypes {
    MYSQL("mysql"),
    NONE("none");

    private String value;

    private DbTypes(String value) {
        this.value = value;
    }

    public static DbTypes fromValue(String value) {
        if ("mysql".equals(value)) {
            return DbTypes.MYSQL;
        } else {
            return DbTypes.NONE;
        }
    }

}
