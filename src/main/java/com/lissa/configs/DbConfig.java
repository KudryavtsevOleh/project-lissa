package com.lissa.configs;

import com.lissa.bean.DbPropertyBean;
import com.lissa.utils.exceptions.EmptyConnectionException;

import java.util.Optional;

public interface DbConfig {

    void configure(DbPropertyBean bean) throws EmptyConnectionException;

}
