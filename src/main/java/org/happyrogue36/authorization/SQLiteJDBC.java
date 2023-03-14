package org.happyrogue36.authorization;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteJDBC {
    public static Connection connection;
    public static Statement statement;
    public static void createConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:jbd.db");
        connection.setAutoCommit(true);
        statement = connection.createStatement();
        statement.setQueryTimeout(30);
        statement.execute("CREATE TABLE if not exists User (\n" +
                "    UserName    TEXT PRIMARY KEY\n" +
                "               NOT NULL,\n" +
                "    code TEXT,\n" +
                "    abilityToConnect INT NOT NULL,\n" +
                "    time INT NOT NULL,\n" +
                "    tryToConnect INT NOT NULL,\n" +
                "    UserId TEXT,\n" +
                "    channel TEXT" +
                ");");
        statement.closeOnCompletion();
    }
}
