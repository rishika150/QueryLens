package com.rishika.sqlanalyzer.model;

public class DashboardStats {

    private final int totalQueries;
    private final double averageExecutionTime;
    private final double averageScore;
    private final int highestScore;
    private final int lowestScore;

    public DashboardStats(int totalQueries, double averageExecutionTime, double averageScore, int highestScore, int lowestScore) {
        this.totalQueries = totalQueries;
        this.averageExecutionTime = averageExecutionTime;
        this.averageScore = averageScore;
        this.highestScore = highestScore;
        this.lowestScore = lowestScore;
    }

    public int getTotalQueries() {
        return totalQueries;
    }

    public double getAverageExecutionTime() {
        return averageExecutionTime;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public int getLowestScore() {
        return lowestScore;
    }
}