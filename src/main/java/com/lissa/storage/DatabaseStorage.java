package com.lissa.storage;

import com.lissa.objects.Entity;
import com.lissa.objects.Ignore;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * Implementation of {@link com.geekhub.lesson9_JDBC.storage.Storage} that uses database as a storage for objects.
 * It uses simple object type names to define target table to save the object.
 * It uses reflection to access objects fields and retrieve data to map to database tables.
 * As an identifier it uses field id of {@link com.geekhub.lesson9_JDBC.objects.Entity} class.
 * Could be created only with {@link java.sql.Connection} specified.
 */
public class DatabaseStorage implements Storage {
    private Connection connection;

    public DatabaseStorage(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T extends Entity> T get(Class<T> clazz, Integer id) throws Exception {
        //this method is fully implemented, no need to do anything, it's just an example
        String sql = "SELECT * FROM " + clazz.getSimpleName() + " WHERE id = " + id;
        try(Statement statement = connection.createStatement()) {
            List<T> result = extractResult(clazz, statement.executeQuery(sql));
            return result.isEmpty() ? null : result.get(0);
        }
    }

    @Override
    public <T extends Entity> List<T> list(Class<T> clazz) throws Exception {
        //implement me according to interface by using extractResult method
        String sql = "SELECT * FROM " + clazz.getSimpleName();
        try (Statement statement = connection.createStatement()) {
            List<T> list = extractResult(clazz, statement.executeQuery(sql));
            Iterator<T> iterator = list.iterator();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                T o = iterator.next();
                o.setId(resultSet.getInt("id"));
            }
            return list;
        }
    }

    @Override
    public <T extends Entity> boolean delete(T entity) throws Exception {
        //implement me
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DELETE FROM " + entity.getClass().getSimpleName() + " WHERE id=" + entity.getId());
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    }

    @Override
    public <T extends Entity> void save(T entity) throws Exception {
        Map<String, Object> data = prepareEntity(entity);
        String sql = null;
        if (entity.isNew()) {
            //implement me
            //need to define right SQL query to create object
        try (Statement statement = connection.createStatement()) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                sql = "INSERT INTO " + entity.getClass().getSimpleName() + "(" + entry.getKey() + ") " +
                      "VALUES " + "(" + entry.getValue() + ")";
                statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                ResultSet resultSet = statement.getGeneratedKeys();
                while (resultSet.next()) {
                    entity.setId(resultSet.getInt(1));
                }
            }
        }
        } else {
            //implement me
            //need to define right SQL query to update object
            try (Statement statement = connection.createStatement()) {
                sql = "UPDATE " + entity.getClass().getSimpleName() + " SET ";
                for (Field field : entity.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    if (!field.isAnnotationPresent(Ignore.class)) {
                        if (field.getType().getSimpleName().equals("String")) {
                            sql += field.getName() + " = '" + field.get(entity) + "',";
                        } else {
                            sql += field.getName() + " = " + field.get(entity) + ",";
                        }
                    }
                    field.setAccessible(false);
                }
                sql = sql.substring(0, sql.lastIndexOf(",")) + " WHERE id=" + entity.getId();
                statement.executeUpdate(sql);
            }
        }
        //implement me, need to save/update object and update it with new id if it's a creation
    }

    //converts object to map, could be helpful in save method
    private <T extends Entity> Map<String, Object> prepareEntity(T entity) throws Exception {
        //implement me
        Map<String, Object> map = new HashMap<>();
        String fieldName = "";
        String fieldValue = "";
        for (Field field : entity.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (!field.isAnnotationPresent(Ignore.class)) {
                fieldName += field.getName() + ",";
                if (!field.getType().getSimpleName().equals("String")) {
                    fieldValue += field.get(entity) + ",";
                } else {
                    fieldValue += "'" + field.get(entity) + "'" + ",";
                }
                field.setAccessible(false);
            }
        }
        fieldName = fieldName.substring(0, fieldName.lastIndexOf(","));
        fieldValue = fieldValue.substring(0, fieldValue.lastIndexOf(","));
        map.put(fieldName, fieldValue);
        return map;
    }

    //creates list of new instances of clazz by using data from resultset
    private <T extends Entity> List<T> extractResult(Class<T> clazz, ResultSet resultset) throws Exception {
        //implement me
        List<T> classInstances = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        int listElement = 0;
        while (resultset.next()) {
            classInstances.add(clazz.newInstance());
            classInstances.get(listElement).setId(resultset.getInt("id"));
            for (Field field : fields) {
                if (!field.isAnnotationPresent(Ignore.class)) {
                    field.setAccessible(true);
                    field.set(classInstances.get(listElement), resultset.getObject(field.getName()));
                    field.setAccessible(false);
                }
            }
            listElement++;
        }
        return classInstances;
    }
}
