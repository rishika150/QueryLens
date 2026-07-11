package com.rishika.sqlanalyzer.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IndexRecommendationService {

    private static final Pattern TABLE_PATTERN =
            Pattern.compile("\\bFROM\\s+([a-zA-Z_][\\w]*)", Pattern.CASE_INSENSITIVE);

    private static final Pattern SIMPLE_WHERE_COLUMN_PATTERN =
            Pattern.compile(
                    "\\bWHERE\\s+([a-zA-Z_][\\w.]*)\\s*(=|>|<|>=|<=|LIKE|IN)\\b",
                    Pattern.CASE_INSENSITIVE
            );

    private static final Pattern ORDER_BY_COLUMN_PATTERN =
            Pattern.compile(
                    "\\bORDER\\s+BY\\s+([a-zA-Z_][\\w.]*)",
                    Pattern.CASE_INSENSITIVE
            );

    private static final Pattern FUNCTION_IN_WHERE_PATTERN =
            Pattern.compile(
                    "\\bWHERE\\s+(YEAR|MONTH|DAY|DATE|LOWER|UPPER|SUBSTRING)\\s*\\(",
                    Pattern.CASE_INSENSITIVE
            );

    public String recommend(String sql) {
        if (sql == null || sql.isBlank()) {
            return "";
        }

        String normalizedSql = sql
                .trim()
                .replaceAll("\\s+", " ");

        String upperSql = normalizedSql.toUpperCase();

        // Index recommendations are currently limited to SELECT queries.
        if (!upperSql.startsWith("SELECT")) {
            return "";
        }

        String table = extract(TABLE_PATTERN, normalizedSql);

        if (table == null) {
            return "";
        }

        /*
         * Do not generate indexes such as:
         * ON employees(YEAR)
         *
         * YEAR is a function, not a column.
         */
        if (FUNCTION_IN_WHERE_PATTERN.matcher(normalizedSql).find()) {
            return """
                    Index recommendation skipped.

                    A SQL function is applied inside the WHERE clause.
                    Create an index on the underlying column only after
                    reviewing the query rewrite suggested by the analyzer.
                    """.trim();
        }

        String whereColumn = removeTableAlias(
                extract(SIMPLE_WHERE_COLUMN_PATTERN, normalizedSql)
        );

        String orderColumn = removeTableAlias(
                extract(ORDER_BY_COLUMN_PATTERN, normalizedSql)
        );

        if (whereColumn != null
                && orderColumn != null
                && !whereColumn.equalsIgnoreCase(orderColumn)) {

            return """
                    Suggested Composite Index

                    CREATE INDEX idx_%s_%s_%s
                    ON %s(%s, %s);

                    Reason:
                    %s is used for filtering and %s is used for sorting.
                    """
                    .formatted(
                            table,
                            whereColumn,
                            orderColumn,
                            table,
                            whereColumn,
                            orderColumn,
                            whereColumn,
                            orderColumn
                    )
                    .trim();
        }

        if (whereColumn != null) {
            return """
                    Suggested Index

                    CREATE INDEX idx_%s_%s
                    ON %s(%s);

                    Reason:
                    %s is used in the WHERE clause.
                    """
                    .formatted(
                            table,
                            whereColumn,
                            table,
                            whereColumn,
                            whereColumn
                    )
                    .trim();
        }

        if (orderColumn != null) {
            return """
                    Suggested Index

                    CREATE INDEX idx_%s_%s
                    ON %s(%s);

                    Reason:
                    %s is used in the ORDER BY clause.
                    """
                    .formatted(
                            table,
                            orderColumn,
                            table,
                            orderColumn,
                            orderColumn
                    )
                    .trim();
        }

        return "";
    }

    private String extract(Pattern pattern, String sql) {
        Matcher matcher = pattern.matcher(sql);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }

    private String removeTableAlias(String column) {
        if (column == null) {
            return null;
        }

        int dotIndex = column.lastIndexOf('.');

        return dotIndex >= 0
                ? column.substring(dotIndex + 1)
                : column;
    }
}