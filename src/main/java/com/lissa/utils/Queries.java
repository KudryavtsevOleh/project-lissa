package com.lissa.utils;

public class Queries {

    public static final String MYSQL_CONNECTION_URL = "jdbc:mysql://localhost/%s?user=%s&password=%s&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public static final String MYSQL_PART_CONNECTION_URL = "jdbc:mysql://localhost/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public static final String CREATE_DATABASE_QUERY = "create database %s";

}
