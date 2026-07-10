package com.rishika.sqlanalyzer.rules;

public class LeadingWildcardLikeRule implements SqlRule {

    @Override
    public boolean matches(String sql) {
        return sql.contains("LIKE '%");
    }

    @Override
    public String getIssue() {
        return "Leading wildcard LIKE detected.";
    }

    @Override
    public String getRecommendation() {
        return "Avoid LIKE '%text%' when possible because normal indexes may not be used.";
    }

    @Override
    public int getPenalty() {
        return 20;
    }
}