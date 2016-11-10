package com.lissa.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DbPropertyBean {

    private String dbType;
    private String dbName;
    private String userName;
    private String password;
    private String creatingStrategy;

}
