package com.lissa.runner;

import com.lissa.utils.Queries;
import com.lissa.utils.enums.DbTypes;
import com.lissa.utils.exceptions.InvalidDbTypeException;
import com.lissa.utils.validation.NotEmpty;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

@Log
public class Runner {

    @NotEmpty
    private String dbType;
    @NotEmpty
    private String dbName;
    @NotEmpty
    private String userName;
    @NotEmpty
    private String password;


    public void run() {

    }

    /**
     * Create connection for specified database
     *
     * */
    private Connection createConnction() {
        checkConnector();
    }

    private void checkConnector() {
        try {
            if (DbTypes.fromValue(dbType).equals(DbTypes.MYSQL)) {
                Class.forName("com.myql.jdbc.Driver");
            }
        } catch (InvalidDbTypeException dbException) {
            log.log(Level.WARNING, dbException.getFullMessage());
        } catch (ClassNotFoundException e) {
            log.log(Level.WARNING, e.getLocalizedMessage());
        }
    }

    private Connection getMysqlConnection() {
        try {
            Connection connection = DriverManager.getConnection(String.format(Queries.MYSQL_CONNECTION_URL, this.dbName, this.userName, this.password));
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
