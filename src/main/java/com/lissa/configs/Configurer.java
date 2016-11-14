package com.lissa.configs;

import com.lissa.bean.DbPropertyBean;
import com.lissa.utils.Messages;
import com.lissa.utils.enums.DbTypes;
import com.lissa.utils.exceptions.InvalidDbTypeException;

public class Configurer {

    private DbPropertyBean properties;

    {
        DbGetPropertyValues dbPropertiesValues = new DbGetPropertyValues();
        properties = dbPropertiesValues.getProperties();
    }

    public void configure() throws InvalidDbTypeException {
        switch (DbTypes.fromValue(properties.getDbType())) {
            case MYSQL:

                break;
            default:
                throw new InvalidDbTypeException(Messages.INVALID_DB_TYPE, properties.getDbType());
        }
    }

}
