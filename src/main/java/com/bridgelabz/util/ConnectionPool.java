package com.bridgelabz.util;

import com.bridgelabz.exception.DatabaseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayDeque;
import java.util.Queue;

public class ConnectionPool {

    private static final ConnectionPool INSTANCE = new ConnectionPool();

    private final Queue<Connection> availableConnections = new ArrayDeque<>();
    private final String url;
    private final String username;
    private final String password;

    private ConnectionPool() {
        ApplicationConfig config = ApplicationConfig.getInstance();
        this.url = config.getProperty("db.url");
        this.username = config.getProperty("db.username");
        this.password = config.getProperty("db.password");

        int poolSize = config.getIntProperty("db.pool.size");
        for (int i = 0; i < poolSize; i++) {
            availableConnections.offer(createConnection());
        }
        initializeSchema(config.getProperty("db.schema.path"));
    }

    public static ConnectionPool getInstance() {
        return INSTANCE;
    }

    public synchronized Connection getConnection() {
        try {
            if (availableConnections.isEmpty() || availableConnections.peek().isClosed()) {
                return createConnection();
            }
            return availableConnections.poll();
        } catch (SQLException e) {
            throw new DatabaseException("Unable to get database connection", e);
        }
    }

    public synchronized void releaseConnection(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            if (!connection.isClosed()) {
                availableConnections.offer(connection);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Unable to release database connection", e);
        }
    }

    private Connection createConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new DatabaseException("Unable to create database connection", e);
        }
    }

    private void initializeSchema(String schemaPath) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(schemaPath)) {
            if (inputStream == null) {
                throw new DatabaseException(schemaPath + " not found in classpath");
            }

            String schema = readSchema(inputStream);
            Connection connection = getConnection();
            try (Statement statement = connection.createStatement()) {
                statement.execute(schema);
            } finally {
                releaseConnection(connection);
            }
        } catch (IOException | SQLException e) {
            throw new DatabaseException("Unable to initialize database schema", e);
        }
    }

    private String readSchema(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append(System.lineSeparator());
            }
        }
        return builder.toString();
    }
}
