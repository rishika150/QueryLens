package com.rishika.sqlanalyzer.rules;

public class CrossJoinRule implements SqlRule {

    @Override
    public boolean matches(String sql) {
        return sql.contains(" CROSS JOIN ");
    }

    @Override
    public String getIssue() {
        return "CROSS JOIN detected.";
    }

    @Override
    public String getRecommendation() {
        return "Confirm the Cartesian product is intentional because row counts can grow rapidly.";
    }

    @Override
    public int getPenalty() {
        return 25;
    }
}
