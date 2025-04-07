package com.mycompany.app;

import java.sql.*;

public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://db:3306/db";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";
    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
}
