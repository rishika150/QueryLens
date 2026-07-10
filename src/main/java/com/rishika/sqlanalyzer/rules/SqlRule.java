package com.rishika.sqlanalyzer.rules;

import java.util.List;

public interface SqlRule {

    boolean matches(String normalizedSql);

    String getIssue();

    String getRecommendation();

    int getPenalty();

    default void apply(String normalizedSql, List<String> issues, List<String> recommendations, int[] score) {
        if (matches(normalizedSql)) {
            issues.add(getIssue());
            recommendations.add(getRecommendation());
            score[0] -= getPenalty();
        }
    }
}