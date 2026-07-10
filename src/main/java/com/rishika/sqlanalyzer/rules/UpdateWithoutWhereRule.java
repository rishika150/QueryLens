package com.rishika.sqlanalyzer.rules;

public class UpdateWithoutWhereRule implements SqlRule {

    @Override
    public boolean matches(String sql) {
        return sql.startsWith("UPDATE") && !sql.contains(" WHERE ");
    }

    @Override
    public String getIssue() {
        return "UPDATE without WHERE detected.";
    }

    @Override
    public String getRecommendation() {
        return "Always use a WHERE clause before updating rows.";
    }

    @Override
    public int getPenalty() {
        return 70;
    }
}