package com.rishika.sqlanalyzer.model;

import javax.swing.table.DefaultTableModel;

public final class QueryResult {

    private final boolean success;
    private final String message;
    private final long executionTimeMillis;
    private final DefaultTableModel tableModel;

    public QueryResult(boolean success, String message, long executionTimeMillis, DefaultTableModel tableModel) {
        this.success = success;
        this.message = message;
        this.executionTimeMillis = executionTimeMillis;
        this.tableModel = tableModel;
    }

    public static QueryResult forSelect(DefaultTableModel tableModel, long executionTimeMillis) {
        int rowCount = tableModel.getRowCount();
        String message = rowCount + " row(s) returned.";
        return new QueryResult(true, message, executionTimeMillis, tableModel);
    }

    public static QueryResult forUpdate(int rowsAffected, long executionTimeMillis) {
        String message = rowsAffected + " row(s) affected.";
        return new QueryResult(true, message, executionTimeMillis, new DefaultTableModel());
    }

    public static QueryResult forFailure(String errorMessage, long executionTimeMillis) {
        return new QueryResult(false, errorMessage, executionTimeMillis, new DefaultTableModel());
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public long getExecutionTimeMillis() {
        return executionTimeMillis;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
