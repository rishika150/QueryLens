package com.rishika.sqlanalyzer.rules;

import java.util.regex.Pattern;

public class ImplicitJoinRule implements SqlRule {

    private static final Pattern MULTI_TABLE_FROM = Pattern.compile(
            "\\bFROM\\s+\\w+\\s*,\\s*\\w+",
            Pattern.CASE_INSENSITIVE
    );

    @Override
    public boolean matches(String sql) {
        return MULTI_TABLE_FROM.matcher(sql).find();
    }

    @Override
    public String getIssue() {
        return "Implicit comma-based join detected.";
    }

    @Override
    public String getRecommendation() {
        return "Use explicit JOIN ... ON syntax to improve clarity and reduce accidental Cartesian joins.";
    }

    @Override
    public int getPenalty() {
        return 20;
    }
}
