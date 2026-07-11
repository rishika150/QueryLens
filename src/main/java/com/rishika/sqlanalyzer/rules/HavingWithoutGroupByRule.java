package com.rishika.sqlanalyzer.rules;

public class HavingWithoutGroupByRule implements SqlRule {

    @Override
    public boolean matches(String sql) {
        return sql.contains(" HAVING ") && !sql.contains(" GROUP BY ");
    }

    @Override
    public String getIssue() {
        return "HAVING without GROUP BY detected.";
    }

    @Override
    public String getRecommendation() {
        return "Use WHERE for row filtering unless aggregate filtering is genuinely required.";
    }

    @Override
    public int getPenalty() {
        return 15;
    }
}