package com.rishika.sqlanalyzer.model;

import java.util.ArrayList;
import java.util.List;

public class Suggestion {

    private final int performanceScore;
    private final String severity;
    private final List<String> issues;
    private final List<String> recommendations;

    public Suggestion(int performanceScore, String severity, List<String> issues, List<String> recommendations) {
        this.performanceScore = performanceScore;
        this.severity = severity;
        this.issues = issues;
        this.recommendations = recommendations;
    }

    public int getPerformanceScore() {
        return performanceScore;
    }

    public String getSeverity() {
        return severity;
    }

    public List<String> getIssues() {
        return issues;
    }

    public List<String> getRecommendations() {
        return recommendations;
    }

    public String getFormattedMessage() {
        StringBuilder message = new StringBuilder();

        if (issues.isEmpty()) {
            message.append("✅ No major SQL anti-patterns detected.\n");
            message.append("Query looks clean based on current rules.");
            return message.toString();
        }

        message.append("Issues Detected:\n");
        for (String issue : issues) {
            message.append("• ").append(issue).append("\n");
        }

        message.append("\nRecommendations:\n");
        for (String recommendation : recommendations) {
            message.append("• ").append(recommendation).append("\n");
        }

        return message.toString();
    }

    public static Suggestion emptyQuery() {
        List<String> issues = new ArrayList<>();
        issues.add("SQL query is empty.");

        List<String> recommendations = new ArrayList<>();
        recommendations.add("Write a valid SQL query before running analysis.");

        return new Suggestion(0, "Invalid", issues, recommendations);
    }
}