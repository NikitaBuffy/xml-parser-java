package ru.pominov.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class JdbcConnection {

    private static final Logger LOGGER = AppLogger.getLogger();
    private final String jdbcUrl;
    private final String username;
    private final String password;

    public JdbcConnection(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Connection to database refused", e);
            throw new RuntimeException("Failed to get a connection to the database.", e);
        }
    }
}
