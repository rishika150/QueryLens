package com.rishika.sqlanalyzer.rules;

public class OrderByWithoutLimitRule implements SqlRule {

    @Override
    public boolean matches(String sql) {
        return sql.contains("ORDER BY") && !sql.contains("LIMIT");
    }

    @Override
    public String getIssue() {
        return "ORDER BY without LIMIT detected.";
    }

    @Override
    public String getRecommendation() {
        return "Use LIMIT when only a small sorted result set is needed.";
    }

    @Override
    public int getPenalty() {
        return 10;
    }
}