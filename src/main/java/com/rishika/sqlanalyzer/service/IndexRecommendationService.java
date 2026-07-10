package com.rishika.sqlanalyzer.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IndexRecommendationService {

    private static final Pattern TABLE_PATTERN =
            Pattern.compile("FROM\\s+(\\w+)", Pattern.CASE_INSENSITIVE);

    private static final Pattern WHERE_PATTERN =
            Pattern.compile("WHERE\\s+(\\w+)", Pattern.CASE_INSENSITIVE);

    private static final Pattern ORDER_PATTERN =
            Pattern.compile("ORDER\\s+BY\\s+(\\w+)", Pattern.CASE_INSENSITIVE);

    public String recommend(String sql) {

        if (sql == null || sql.isBlank()) {
            return "";
        }

        String table = extract(TABLE_PATTERN, sql);

        if (table == null) {
            return "";
        }

        String whereColumn = extract(WHERE_PATTERN, sql);
        String orderColumn = extract(ORDER_PATTERN, sql);

        StringBuilder recommendation = new StringBuilder();

        if (whereColumn != null && orderColumn != null) {

            recommendation.append("Suggested Composite Index\n\n");

            recommendation.append(
                            "CREATE INDEX idx_")
                    .append(table)
                    .append("_")
                    .append(whereColumn)
                    .append("_")
                    .append(orderColumn)
                    .append("\nON ")
                    .append(table)
                    .append("(")
                    .append(whereColumn)
                    .append(", ")
                    .append(orderColumn)
                    .append(");");

            return recommendation.toString();
        }

        if (whereColumn != null) {

            recommendation.append("Suggested Index\n\n");

            recommendation.append(
                            "CREATE INDEX idx_")
                    .append(table)
                    .append("_")
                    .append(whereColumn)
                    .append("\nON ")
                    .append(table)
                    .append("(")
                    .append(whereColumn)
                    .append(");");

            return recommendation.toString();
        }

        if (orderColumn != null) {

            recommendation.append("Suggested Index\n\n");

            recommendation.append(
                            "CREATE INDEX idx_")
                    .append(table)
                    .append("_")
                    .append(orderColumn)
                    .append("\nON ")
                    .append(table)
                    .append("(")
                    .append(orderColumn)
                    .append(");");
        }

        return recommendation.toString();
    }

    private String extract(Pattern pattern, String sql) {

        Matcher matcher = pattern.matcher(sql);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }
}
