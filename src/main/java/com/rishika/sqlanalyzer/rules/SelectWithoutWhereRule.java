package com.rishika.sqlanalyzer.rules;

public class SelectWithoutWhereRule implements SqlRule {

    @Override
    public boolean matches(String sql) {
        return sql.startsWith("SELECT")
                && !sql.contains(" WHERE ")
                && !sql.contains("LIMIT");
    }

    @Override
    public String getIssue() {
        return "SELECT without WHERE or LIMIT detected.";
    }

    @Override
    public String getRecommendation() {
        return "Filter large tables with WHERE or restrict results using LIMIT.";
    }

    @Override
    public int getPenalty() {
        return 10;
    }
}
