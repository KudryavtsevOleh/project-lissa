package com.lissa.configs;

import com.lissa.bean.ColumnBean;
import com.lissa.bean.DbPropertyBean;
import com.lissa.bean.TableBean;
import com.lissa.objects.Entity;
import com.lissa.utils.Messages;
import com.lissa.utils.annotations.Column;
import com.lissa.utils.annotations.Table;
import com.lissa.utils.enums.DbTypes;
import com.lissa.utils.exceptions.EmptyConnectionException;
import com.lissa.utils.exceptions.InvalidDbTypeException;
import com.lissa.utils.validation.Ignore;
import org.reflections.Reflections;
import org.apache.commons.lang3.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.*;

public class Configurer {

    private static DbPropertyBean properties;

    static {
        DbGetPropertyValues dbPropertiesValues = new DbGetPropertyValues();
        properties = dbPropertiesValues.getProperties();
    }

    public static void configure() throws InvalidDbTypeException, EmptyConnectionException {
        switch (DbTypes.fromValue(properties.getDbType())) {
            case MYSQL:
                configureMysql();
                break;
            default:
                throw new InvalidDbTypeException(Messages.INVALID_DB_TYPE, properties.getDbType());
        }
    }

    private static void configureMysql() throws EmptyConnectionException {
        MySQLConfig config = new MySQLConfig();
        Optional<Connection> connectionOptional = config.createConnection(properties);
        connectionOptional.orElseThrow(() -> new EmptyConnectionException(Messages.CONNECTION_EMPTY));
        Connection connection = connectionOptional.get();
        List<TableBean> tableMeta = checkEntities();
        createTablesInDb(tableMeta, connection);
    }

    private static List<TableBean> checkEntities() {
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

    private static void getFieldsNames(Class<?> t, TableBean tableMeta) {
        List<Field> fields = Arrays.asList(t.getDeclaredFields());
        fields.stream()
                .filter(f -> f.isAnnotationPresent(Column.class) && !f.isAnnotationPresent(Ignore.class))
                .forEach(f -> {
                    f.setAccessible(true);
                    Column annotation = f.getAnnotation(Column.class);
                    String fieldName = StringUtils.isNotEmpty(annotation.name()) ? annotation.name() : f.getName();
                    String typeName = f.getType().getSimpleName();
                    tableMeta.getColumns().add(new ColumnBean(fieldName, typeName));
                    f.setAccessible(false);
                });
    }

    private static void getTableName(Class<?> t, TableBean tableMeta) {
        Table annotation = t.getAnnotation(Table.class);
        String tableName = StringUtils.isNotEmpty(annotation.name()) ? annotation.name() : t.getSimpleName();
        tableMeta.setTableName(tableName);
    }

    private static void createTablesInDb(List<TableBean> tablesProperties, Connection connection) {

    }

}
