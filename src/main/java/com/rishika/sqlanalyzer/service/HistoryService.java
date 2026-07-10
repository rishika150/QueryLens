package com.rishika.sqlanalyzer.service;

import com.rishika.sqlanalyzer.model.QueryHistory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryService {

    private static final int MAX_HISTORY = 20;

    private final List<QueryHistory> history = new ArrayList<>();

    public void add(QueryHistory queryHistory) {

        history.add(0, queryHistory);

        if (history.size() > MAX_HISTORY) {
            history.remove(history.size() - 1);
        }
    }

    public List<QueryHistory> getHistory() {
        return Collections.unmodifiableList(history);
    }

    public void clearHistory() {
        history.clear();
    }

    public boolean isEmpty() {
        return history.isEmpty();
    }
    public com.rishika.sqlanalyzer.model.DashboardStats getDashboardStats() {
        if (history.isEmpty()) {
            return new com.rishika.sqlanalyzer.model.DashboardStats(0, 0, 0, 0, 0);
        }

        int total = history.size();

        double averageTime = history.stream()
                .mapToLong(com.rishika.sqlanalyzer.model.QueryHistory::getExecutionTimeMillis)
                .average()
                .orElse(0);

        double averageScore = history.stream()
                .mapToInt(com.rishika.sqlanalyzer.model.QueryHistory::getPerformanceScore)
                .average()
                .orElse(0);

        int highestScore = history.stream()
                .mapToInt(com.rishika.sqlanalyzer.model.QueryHistory::getPerformanceScore)
                .max()
                .orElse(0);

        int lowestScore = history.stream()
                .mapToInt(com.rishika.sqlanalyzer.model.QueryHistory::getPerformanceScore)
                .min()
                .orElse(0);

        return new com.rishika.sqlanalyzer.model.DashboardStats(
                total,
                averageTime,
                averageScore,
                highestScore,
                lowestScore
        );
    }

}