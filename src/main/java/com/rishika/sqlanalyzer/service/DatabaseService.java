package com.rishika.sqlanalyzer.service;

import com.rishika.sqlanalyzer.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseService {

    public Connection connect(DatabaseConfig config) throws SQLException {

        return DriverManager.getConnection(
                config.getJdbcUrl(),
                config.getUsername(),
                config.getPassword()
        );

    }

}
