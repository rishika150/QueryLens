package com.rishika.sqlanalyzer.rules;

public class SelectStarRule implements SqlRule {

    @Override
    public boolean matches(String sql) {
        return sql.startsWith("SELECT") && sql.contains("SELECT *");
    }

    @Override
    public String getIssue() {
        return "SELECT * detected.";
    }

    @Override
    public String getRecommendation() {
        return "Select only required columns instead of fetching every column.";
    }

    @Override
    public int getPenalty() {
        return 15;
    }
}