package com.rishika.sqlanalyzer.rules;

public class MultipleOrRule implements SqlRule {

    @Override
    public boolean matches(String sql) {
        int count = 0;
        int position = 0;

        while ((position = sql.indexOf(" OR ", position)) != -1) {
            count++;
            position += 4;
        }

        return count >= 2;
    }

    @Override
    public String getIssue() {
        return "Multiple OR conditions detected.";
    }

    @Override
    public String getRecommendation() {
        return "Review indexes or consider UNION ALL when it produces a simpler execution plan.";
    }

    @Override
    public int getPenalty() {
        return 10;
    }
}
