package com.rishika.sqlanalyzer.service;

import com.rishika.sqlanalyzer.config.DatabaseConfig;
import com.rishika.sqlanalyzer.model.QueryResult;

public class ExplainService {

    private final QueryExecutor queryExecutor = new QueryExecutor();

    public QueryResult explain(DatabaseConfig config, String sql) {
        if (sql == null || sql.isBlank()) {
            return QueryResult.forFailure("SQL query cannot be empty.", 0);
        }

        String trimmedSql = sql.trim();

        if (!trimmedSql.toLowerCase().startsWith("select")) {
            return QueryResult.forFailure("EXPLAIN is currently supported only for SELECT queries.", 0);
        }

        return queryExecutor.execute(config, "EXPLAIN " + trimmedSql);
    }
}