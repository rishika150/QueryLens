package com.rishika.sqlanalyzer.rules;

public class DeleteWithoutWhereRule implements SqlRule {

    @Override
    public boolean matches(String sql) {
        return sql.startsWith("DELETE") && !sql.contains(" WHERE ");
    }

    @Override
    public String getIssue() {
        return "DELETE without WHERE detected.";
    }

    @Override
    public String getRecommendation() {
        return "Always use a WHERE clause to avoid deleting all rows.";
    }

    @Override
    public int getPenalty() {
        return 80;
    }
}