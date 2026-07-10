package com.rishika.sqlanalyzer.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QueryHistory {

    private final String query;
    private final String queryType;
    private final long executionTimeMillis;
    private final int performanceScore;
    private final String status;
    private final LocalDateTime executedAt;

    public QueryHistory(String query, String queryType, long executionTimeMillis, int performanceScore, String status) {
        this.query = query;
        this.queryType = queryType;
        this.executionTimeMillis = executionTimeMillis;
        this.performanceScore = performanceScore;
        this.status = status;
        this.executedAt = LocalDateTime.now();
    }

    public String getQuery() {
        return query;
    }

    public String getQueryType() {
        return queryType;
    }

    public long getExecutionTimeMillis() {
        return executionTimeMillis;
    }

    public int getPerformanceScore() {
        return performanceScore;
    }

    public String getStatus() {
        return status;
    }

    public String getExecutedAt() {
        return executedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}