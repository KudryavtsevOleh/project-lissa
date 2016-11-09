package com.lissa.configs;

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

    @NotEmpty
    private String dbName = "lissa_test";
    @NotEmpty
    private String userName = "root";
    @NotEmpty
    private String password = "1995";

    public Connection createConnection() {
        checkConnector();
        Connection connection = getMysqlConnection();
        return connection;
    }

    private void checkConnector() {
        try {
            Class.forName("com.myql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.log(Level.WARNING, e.getLocalizedMessage());
        }
    }

    private Connection getMysqlConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(String.format(Queries.MYSQL_CONNECTION_URL, this.dbName, this.userName, this.password));
        } catch (SQLException e) {
            createDatabase();
            try {
                connection = DriverManager.getConnection(String.format(Queries.MYSQL_CONNECTION_URL, this.dbName, this.userName, this.password));
            } catch (SQLException e1) {
                e1.printStackTrace();
                connection = null;
            }
        }
        return connection;
    }

    private void createDatabase() {
        try(
                Connection connection = DriverManager.getConnection(Queries.MYSQL_PART_CONNECTION_URL, this.userName, this.password);
                Statement statement = connection.createStatement()
                )  {
            statement.execute(String.format(Queries.CREATE_DATABASE_QUERY, this.dbName));
        } catch (SQLException e) {
            log.log(Level.WARNING, e.getMessage());
            System.exit(0);
        }
    }

}
