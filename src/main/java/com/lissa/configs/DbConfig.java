package com.lissa.configs;

import com.lissa.bean.DbPropertyBean;
import java.util.Optional;

public interface DbConfig<T> {

    Optional<T> createConnection(DbPropertyBean bean);

}
