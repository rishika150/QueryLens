package com.rishika.sqlanalyzer.service;

import com.rishika.sqlanalyzer.model.ExplainAnalysis;
import com.rishika.sqlanalyzer.model.QueryResult;

import javax.swing.table.DefaultTableModel;

public class ExplainAnalyzerService {

    public ExplainAnalysis analyze(QueryResult explainResult) {
        if (explainResult == null || !explainResult.isSuccess()) {
            return new ExplainAnalysis(0, "Invalid", "Could not analyze EXPLAIN plan.");
        }

        DefaultTableModel model = explainResult.getTableModel();

        if (model == null || model.getRowCount() == 0) {
            return new ExplainAnalysis(0, "Invalid", "EXPLAIN plan returned no rows.");
        }

        int score = 100;
        StringBuilder message = new StringBuilder();

        for (int row = 0; row < model.getRowCount(); row++) {
            String type = getValue(model, row, "type");
            String key = getValue(model, row, "key");
            String rows = getValue(model, row, "rows");
            String extra = getValue(model, row, "Extra");

            if ("ALL".equalsIgnoreCase(type)) {
                score -= 35;
                message.append("🚨 Full table scan detected.\n");
                message.append("Recommendation: Add an index on columns used in WHERE or JOIN conditions.\n\n");
            }

            if (key == null || key.equalsIgnoreCase("null") || key.isBlank()) {
                score -= 20;
                message.append("⚠ No index is being used.\n");
                message.append("Recommendation: Check possible_keys and add/use a suitable index.\n\n");
            }

            int scannedRows = parseInt(rows);
            if (scannedRows > 1000) {
                score -= 15;
                message.append("⚠ Large row scan detected: ").append(scannedRows).append(" rows.\n");
                message.append("Recommendation: Add filtering conditions or indexes.\n\n");
            }

            if (extra != null && extra.toUpperCase().contains("USING FILESORT")) {
                score -= 15;
                message.append("⚠ Filesort detected.\n");
                message.append("Recommendation: Add an index that supports ORDER BY.\n\n");
            }

            if (extra != null && extra.toUpperCase().contains("USING TEMPORARY")) {
                score -= 15;
                message.append("⚠ Temporary table detected.\n");
                message.append("Recommendation: Review GROUP BY, ORDER BY, and JOIN usage.\n\n");
            }
        }

        score = Math.max(score, 0);

        if (message.isEmpty()) {
            message.append("✅ EXPLAIN plan looks healthy.\n");
            message.append("No major execution-plan issues detected.");
        }

        return new ExplainAnalysis(score, getSeverity(score), message.toString());
    }

    private String getValue(DefaultTableModel model, int row, String columnName) {
        for (int col = 0; col < model.getColumnCount(); col++) {
            if (columnName.equalsIgnoreCase(model.getColumnName(col))) {
                Object value = model.getValueAt(row, col);
                return value == null ? null : value.toString();
            }
        }
        return null;
    }

    private int parseInt(String value) {
        try {
            return value == null ? 0 : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String getSeverity(int score) {
        if (score >= 90) return "Low";
        if (score >= 70) return "Medium";
        if (score >= 40) return "High";
        return "Critical";
    }
}