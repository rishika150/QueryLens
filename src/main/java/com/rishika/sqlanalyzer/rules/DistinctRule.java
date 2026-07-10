package com.rishika.sqlanalyzer.rules;

public class DistinctRule implements SqlRule {

    @Override
    public boolean matches(String sql) {
        return sql.startsWith("SELECT") && sql.contains(" DISTINCT ");
    }

    @Override
    public String getIssue() {
        return "DISTINCT detected.";
    }

    @Override
    public String getRecommendation() {
        return "Use DISTINCT only when duplicate removal is truly required.";
    }

    @Override
    public int getPenalty() {
        return 10;
    }
}