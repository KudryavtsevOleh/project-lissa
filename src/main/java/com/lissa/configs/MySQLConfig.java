package com.lissa.configs;

import com.lissa.bean.DbPropertyBean;
import com.lissa.utils.Messages;
import com.lissa.utils.Queries;
import com.lissa.utils.enums.DbTypes;
import com.lissa.utils.exceptions.InvalidDbTypeException;
import com.lissa.utils.validation.NotEmpty;
import lombok.extern.java.Log;

import javax.management.Query;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

@Log
public class MySQLConfig {

    public Connection createConnection(DbPropertyBean bean) {
        checkConnector();
        Connection connection = getMysqlConnection(bean);
        return connection;
    }

    private void checkConnector() {
        try {
            Class.forName("com.myql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.log(Level.WARNING, e.getLocalizedMessage());
        }
    }

    private Connection getMysqlConnection(DbPropertyBean bean) {
        Connection connection;
        try {
            connection = DriverManager.getConnection(String.format(Queries.MYSQL_CONNECTION_URL, bean.getDbName(), bean.getUserName(), bean.getPassword()));
        } catch (SQLException e) {
            log.log(Level.WARNING, String.format(Messages.ERROR_CREATING_DB_CONNECTION, e.getMessage()));
            connection = null;
        }
        return connection;
    }

}
