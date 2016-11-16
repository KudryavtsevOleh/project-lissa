package com.lissa.utils;

public class Queries {

    public static final String MYSQL_CONNECTION_URL = "jdbc:mysql://localhost/%s?user=%s&password=%s&createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public static final String LEFT_BRACKET = "(";
    public static final String RIGHT_BRACKET = ")";
    public static final String COMMA = ",";

    public static final String MYSQL_CREATE_TABLE = "CREATE TABLE %s";
    public static final String MYSQL_ADD_COLUMN_WHEN_CREATE = "%s %s";
    public static final String MYSQL_ADD_AUTO_INCREMENT_COLUMN_WHEN_CREATE = "%s %s AUTO_INCREMENT";
    public static final String MYSQL_ADD_PRIMARY_KEY_AUTO_INCREMENT_COLUMN_WHEN_CREATE = "%s %s AUTO_INCREMENT PRIMARY KEY";
    public static final String MYSQL_ADD_PRIMARY_KEY_COLUMN_WHEN_CREATE = "%s %s PRIMARY KEY";

    public static final String MYSQL_INSERT_COLUMN = "ALTER TABLE %s ADD COLUMN %s %s;";
    public static final String MYSQL_INSERT_PRIMARY_KEY_AUTOINCREMENT_COLUMN = "ALTER TABLE %s ADD COLUMN %s %s PRIMARY KEY AUTO_INCREMENT;";
    public static final String MYSQL_INSERT_PRIMARY_KEY_COLUMN = "ALTER TABLE %s ADD COLUMN %s %s PRIMARY KEY;";
}