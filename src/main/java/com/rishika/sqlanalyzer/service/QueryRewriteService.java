package com.rishika.sqlanalyzer.service;

public class QueryRewriteService {

    public String suggestRewrite(String sql) {
        if (sql == null || sql.isBlank()) {
            return "";
        }

        String normalized = sql
                .trim()
                .replaceAll("\\s+", " ");

        String upper = normalized.toUpperCase();

        if (upper.startsWith("DELETE") && !upper.contains(" WHERE ")) {
            return normalized.replaceFirst(";$", "")
                    + "\nWHERE id = ?;";
        }

        if (upper.startsWith("UPDATE") && !upper.contains(" WHERE ")) {
            return normalized.replaceFirst(";$", "")
                    + "\nWHERE id = ?;";
        }

        if (upper.contains(" UNION ") && !upper.contains(" UNION ALL ")) {
            return normalized.replaceAll("(?i)\\s+UNION\\s+", "\nUNION ALL\n");
        }

        if (upper.contains(" ORDER BY ") && !upper.contains(" LIMIT ")) {
            return normalized.replaceFirst(";$", "")
                    + "\nLIMIT 100;";
        }

        return "";
    }
}