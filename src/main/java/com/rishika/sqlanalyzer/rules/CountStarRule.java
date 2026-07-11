package com.rishika.sqlanalyzer.rules;

public class CountStarRule implements SqlRule {

    @Override
    public boolean matches(String sql) {
        return sql.contains("COUNT(*)");
    }

    @Override
    public String getIssue() {
        return "COUNT(*) detected.";
    }

    @Override
    public String getRecommendation() {
        return "For large tables, review whether an indexed column or approximate count would be more appropriate.";
    }

    @Override
    public int getPenalty() {
        return 5;
    }
}
