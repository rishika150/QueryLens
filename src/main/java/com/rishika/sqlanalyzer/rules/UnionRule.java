package com.rishika.sqlanalyzer.rules;

public class UnionRule implements SqlRule {

    @Override
    public boolean matches(String sql) {
        return sql.contains(" UNION ") && !sql.contains(" UNION ALL ");
    }

    @Override
    public String getIssue() {
        return "UNION performs duplicate removal.";
    }

    @Override
    public String getRecommendation() {
        return "Use UNION ALL when duplicate removal is unnecessary to avoid extra sorting work.";
    }

    @Override
    public int getPenalty() {
        return 10;
    }
}
