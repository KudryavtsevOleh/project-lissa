package com.lissa;

import com.lissa.objects.Cat;
import com.lissa.objects.user;
import com.lissa.storage.DatabaseStorage;
import com.lissa.storage.Storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {
        Connection connection = createConnection("root", "1995", "geekdb");

        Storage storage = new DatabaseStorage(connection);
        List<Cat> Cats = storage.list(Cat.class);
        for (Cat Cat : Cats) {
            storage.delete(Cat);
        }
        Cats = storage.list(Cat.class);
        if (!Cats.isEmpty()) throw new Exception("Cats should not be in database! ");

        for(int i = 1; i <= 20; i++) {
            Cat Cat = new Cat();
            Cat.setName("Cat" + i);
            Cat.setAge(i);
            storage.save(Cat);
        }

        Cats = storage.list(Cat.class);
        if (Cats.size() != 20) throw new Exception("Number of Cats in storage should be 20!");

        user user = new user();
        user.setAdmin(true);
        user.setAge(23);
        user.setName("Victor");
        user.setBalance(22.23);
        storage.save(user);

        user user1 = storage.get(user.class, user.getId());
        if (!user1.getName().equals(user.getName())) throw new Exception("Users should be equals!");

        user.setAdmin(false);
        storage.save(user);

        user user2 = storage.get(user.class, user.getId());
        if (!user.getAdmin().equals(user2.getAdmin())) throw new Exception("Users should be updated!");

        storage.delete(user1);

        user user3 = storage.get(user.class, user.getId());

        if (user3 != null) throw new Exception("User should be deleted!");

        connection.close();
    }

    private static Connection createConnection(String login, String password, String dbName) throws Exception {
        //implement me: initiate connection

        try {

            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return DriverManager.getConnection("jdbc:mysql://localhost/" + dbName + "?user=" + login + "&password=" + password);

    }
}
