package com.lissa.configs;

import com.lissa.bean.ColumnBean;
import com.lissa.bean.DbPropertyBean;
import com.lissa.bean.TableBean;
import com.lissa.utils.Messages;
import com.lissa.utils.Queries;
import com.lissa.utils.annotations.Column;
import com.lissa.utils.annotations.PrimaryKey;
import com.lissa.utils.annotations.Table;
import com.lissa.utils.enums.DbTypes;
import com.lissa.utils.enums.MySQLFieldsTypes;
import com.lissa.utils.exceptions.EmptyConnectionException;
import com.lissa.utils.exceptions.InvalidDbTypeException;
import com.lissa.utils.validation.Ignore;
import com.lissa.utils.validation.NotEmpty;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;

import javax.management.Query;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;

@Log
public class MySQLConfig implements DbConfig {

    private Optional<Connection> createConnection(DbPropertyBean bean) {
        checkConnector();
        Connection connection = getMysqlConnection(bean);
        return Optional.of(connection);
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

    @Override
    public void configure(DbPropertyBean properties) throws EmptyConnectionException {
        Optional<Connection> connectionOptional = createConnection(properties);
        connectionOptional.orElseThrow(() -> new EmptyConnectionException(Messages.CONNECTION_EMPTY));
        Connection connection = connectionOptional.get();
        List<TableBean> tableMeta = checkEntities();
        createTablesInDb(tableMeta, connection);
    }

    private List<TableBean> checkEntities() {
        List<TableBean> tablesMeta = new ArrayList<>();
        Reflections reflections = new Reflections("com.lissa");
        Set<Class<?>> tables = reflections.getTypesAnnotatedWith(Table.class);
        tables.forEach(t -> {
            TableBean tableMeta = new TableBean();
            getTableName(t, tableMeta);
            getFieldsNames(t, tableMeta);
            tablesMeta.add(tableMeta);
        });
        return tablesMeta;
    }

    private void getFieldsNames(Class<?> t, TableBean tableMeta) {
        List<Field> fields = Arrays.asList(t.getDeclaredFields());
        fields.stream()
                .filter(f -> (f.isAnnotationPresent(Column.class)) && !f.isAnnotationPresent(Ignore.class))
                .forEach(f -> {
                    f.setAccessible(true);
                    Column annotation = f.getAnnotation(Column.class);
                    String fieldName = StringUtils.isNotEmpty(annotation.name()) ? annotation.name() : f.getName();
                    String typeName = f.getType().getSimpleName();
                    ColumnBean columnMeta = new ColumnBean(fieldName, typeName);
                    getPrimaryKeyMeta(f, columnMeta);
                    tableMeta.getColumns().add(columnMeta);
                    f.setAccessible(false);
                });
    }

    private void getPrimaryKeyMeta(Field f, ColumnBean columnMeta) {
        if (f.isAnnotationPresent(PrimaryKey.class)) {
            PrimaryKey primaryKey = f.getAnnotation(PrimaryKey.class);
            columnMeta.setAutoIncrement(primaryKey.autoIncrement());
            columnMeta.setPrimaryKey(true);
        }
    }

    private void getTableName(Class<?> t, TableBean tableMeta) {
        Table annotation = t.getAnnotation(Table.class);
        String tableName = StringUtils.isNotEmpty(annotation.name()) ? annotation.name() : t.getSimpleName();
        tableMeta.setTableName(tableName);
    }

    private void createTablesInDb(List<TableBean> tablesProperties, Connection connection) {
        try (Statement statement = connection.createStatement()) {
            List<String> createTableQueries = generateTableQuery(tablesProperties);
            createTableQueries.forEach(q -> addToBatch(statement, q));
            statement.executeBatch();
        } catch (SQLException e) {
            log.log(Level.WARNING, String.format(Messages.ERROR_EXECUTING_QUERY, e.getMessage()));
        }
    }

    private void addToBatch(Statement s, String query) {
        try {
            s.addBatch(query);
        } catch (SQLException e) {
            log.log(Level.WARNING, String.format(Messages.ERROR_ADD_TO_BATCH, e.getMessage()));
        }
    }

    private List<String> generateTableQuery(List<TableBean> tablesProperties) {
        List<String> queries = new ArrayList<>();
        tablesProperties.forEach(p -> {
            StringBuilder queryBuilder = new StringBuilder();
            String createTableQuery = String.format(Queries.MYSQL_CREATE_TABLE, p.getTableName());
            queryBuilder.append(createTableQuery).append(Queries.LEFT_BRACKET);
            List<ColumnBean> columns = p.getColumns();
            columns.forEach(c -> {
                String query = getQuery(c.isPrimaryKey(), c.isAutoIncrement());
                String columnType = MySQLFieldsTypes.valueFrom(c.getType());
                queryBuilder.append(String.format(query, c.getName(), columnType)).append(Queries.COMMA);
            });
            queryBuilder.setLength(queryBuilder.length() - 1);
            queryBuilder.append(Queries.RIGHT_BRACKET);
            queries.add(queryBuilder.toString());
        });
        return queries;
    }

    private String getQuery(boolean isPrimaryKey, boolean isAutoIncrement) {
        return isPrimaryKey ?
                (
                        isAutoIncrement ? Queries.MYSQL_ADD_PRIMARY_KEY_AUTO_INCREMENT_COLUMN_WHEN_CREATE :
                                Queries.MYSQL_ADD_PRIMARY_KEY_COLUMN_WHEN_CREATE
                ) :
                (Queries.MYSQL_ADD_COLUMN_WHEN_CREATE);
    }

}
