package com.lissa.configs;

import com.lissa.bean.DbPropertyBean;
import com.lissa.utils.DbProperties;
import com.lissa.utils.Messages;
import lombok.extern.java.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

@Log
public class DbGetPropertyValues {

    public DbPropertyBean getProperties() {
        Properties properties = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(DbProperties.DB_PROPERTY_FILE)) {
            if (is == null) throw new FileNotFoundException("Cannot find db.property file");
            properties.load(is);
            DbPropertyBean dbPropertyBean = readProperties(properties);
            return dbPropertyBean;
        } catch (IOException e) {
            log.log(Level.WARNING, String.format(Messages.ERROR_READ_PROPERTY_FILE, e.getMessage()));
            return null;
        }
    }

    private DbPropertyBean readProperties(Properties properties) {
        String dbType = properties.getProperty(DbProperties.DB_TYPE_PROPERTY);
        String dbName = properties.getProperty(DbProperties.DB_NAME_PROPERTY);
        String userName = properties.getProperty(DbProperties.DB_USERNAME_PROPERTY);
        String password = properties.getProperty(DbProperties.DB_PASSWORD_PROPERTY);
        String creatingStrategy = properties.getProperty(DbProperties.DB_CREATING_STRATEGY);
        DbPropertyBean bean = new DbPropertyBean(dbType, dbName, userName, password, creatingStrategy);
        return bean;
    }

}
