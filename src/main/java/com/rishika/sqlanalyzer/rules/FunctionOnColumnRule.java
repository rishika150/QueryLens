package com.rishika.sqlanalyzer.rules;

import java.util.regex.Pattern;

public class FunctionOnColumnRule implements SqlRule {

    private static final Pattern FUNCTION_PATTERN = Pattern.compile(
            "\\b(YEAR|MONTH|DAY|LOWER|UPPER|DATE|SUBSTRING)\\s*\\(",
            Pattern.CASE_INSENSITIVE
    );

    @Override
    public boolean matches(String sql) {
        return FUNCTION_PATTERN.matcher(sql).find();
    }

    @Override
    public String getIssue() {
        return "Function applied to a filtered or sorted column detected.";
    }

    @Override
    public String getRecommendation() {
        return "Avoid wrapping indexed columns in functions when possible because it may prevent index usage.";
    }

    @Override
    public int getPenalty() {
        return 15;
    }
}
