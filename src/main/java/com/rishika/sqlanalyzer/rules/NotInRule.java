package com.rishika.sqlanalyzer.rules;

public class NotInRule implements SqlRule {

    @Override
    public boolean matches(String sql) {
        return sql.contains(" NOT IN ");
    }

    @Override
    public String getIssue() {
        return "NOT IN detected.";
    }

    @Override
    public String getRecommendation() {
        return "Consider NOT EXISTS, especially when the subquery may contain NULL values.";
    }

    @Override
    public int getPenalty() {
        return 10;
    }
}
