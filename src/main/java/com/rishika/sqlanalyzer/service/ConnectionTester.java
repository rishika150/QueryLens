package com.rishika.sqlanalyzer.service;

import com.rishika.sqlanalyzer.config.DatabaseConfig;

import java.sql.Connection;

public class ConnectionTester {

    private final DatabaseService databaseService = new DatabaseService();

    public boolean test(DatabaseConfig config) {

        try (Connection connection = databaseService.connect(config)) {
            return connection != null && !connection.isClosed();
        } catch (Exception e) {
            return false;
        }

    }
}
