package com.rishika.sqlanalyzer.model;

public class ExplainAnalysis {

    private final int score;
    private final String severity;
    private final String message;

    public ExplainAnalysis(int score, String severity, String message) {
        this.score = score;
        this.severity = severity;
        this.message = message;
    }

    public int getScore() {
        return score;
    }

    public String getSeverity() {
        return severity;
    }

    public String getMessage() {
        return message;
    }
}