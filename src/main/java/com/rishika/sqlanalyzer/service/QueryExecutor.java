package com.rishika.sqlanalyzer.service;

import com.rishika.sqlanalyzer.config.DatabaseConfig;
import com.rishika.sqlanalyzer.model.QueryResult;

import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class QueryExecutor {

    private final DatabaseService databaseService = new DatabaseService();

    public QueryResult execute(DatabaseConfig config, String sql) {
        if (sql == null || sql.isBlank()) {
            return QueryResult.forFailure("SQL query cannot be empty.", 0);
        }

        String trimmedSql = sql.trim();
        long startTime = System.nanoTime();

        try (Connection connection = databaseService.connect(config);
             Statement statement = connection.createStatement()) {

            boolean hasResultSet = statement.execute(trimmedSql);

            long executionTimeMillis = (System.nanoTime() - startTime) / 1_000_000;

            if (hasResultSet) {
                try (ResultSet resultSet = statement.getResultSet()) {
                    DefaultTableModel model = buildTableModel(resultSet);
                    return QueryResult.forSelect(model, executionTimeMillis);
                }
            }

            int rowsAffected = statement.getUpdateCount();
            return QueryResult.forUpdate(rowsAffected, executionTimeMillis);

        } catch (SQLException e) {
            long executionTimeMillis = (System.nanoTime() - startTime) / 1_000_000;
            return QueryResult.forFailure(e.getMessage(), executionTimeMillis);
        }
    }

    private DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();

        int columnCount = metaData.getColumnCount();

        DefaultTableModel model = new DefaultTableModel();

        for (int i = 1; i <= columnCount; i++) {
            model.addColumn(metaData.getColumnLabel(i));
        }

        while (resultSet.next()) {
            Object[] row = new Object[columnCount];

            for (int i = 1; i <= columnCount; i++) {
                row[i - 1] = resultSet.getObject(i);
            }

            model.addRow(row);
        }

        return model;
    }
}
