package com.lissa.configs;

import com.lissa.bean.DbPropertyBean;
import com.lissa.utils.Messages;
import com.lissa.utils.enums.DbTypes;
import com.lissa.utils.exceptions.EmptyConnectionException;
import com.lissa.utils.exceptions.InvalidDbTypeException;
import lombok.extern.java.Log;

@Log
public class Configurer {

    private static DbPropertyBean properties;

    static {
        DbGetPropertyValues dbPropertiesValues = new DbGetPropertyValues();
        properties = dbPropertiesValues.getProperties();
    }

    public static void configure() throws InvalidDbTypeException, EmptyConnectionException {
        switch (DbTypes.fromValue(properties.getDbType())) {
            case MYSQL:
                MySQLConfig config = new MySQLConfig();
                config.configure(properties);
                break;
            default:
                throw new InvalidDbTypeException(Messages.INVALID_DB_TYPE, properties.getDbType());
        }
    }

}
