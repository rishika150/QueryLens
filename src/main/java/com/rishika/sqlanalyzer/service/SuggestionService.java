package com.rishika.sqlanalyzer.service;

import com.rishika.sqlanalyzer.model.Suggestion;
import com.rishika.sqlanalyzer.rules.*;

import java.util.ArrayList;
import java.util.List;

public class SuggestionService {

    private final List<SqlRule> rules = List.of(
            new SelectStarRule(),
            new DeleteWithoutWhereRule(),
            new UpdateWithoutWhereRule(),
            new LeadingWildcardLikeRule(),
            new OrderByWithoutLimitRule(),
            new DistinctRule(),
            new SelectWithoutWhereRule(),
            new MultipleOrRule(),
            new NotInRule(),
            new UnionRule()
    );



    public Suggestion analyze(String sql) {

        if (sql == null || sql.isBlank()) {
            return Suggestion.emptyQuery();
        }

        String normalizedSql = sql
                .trim()
                .replaceAll("\\s+", " ")
                .toUpperCase();

        int[] score = {100};

        List<String> issues = new ArrayList<>();
        List<String> recommendations = new ArrayList<>();

        for (SqlRule rule : rules) {
            rule.apply(
                    normalizedSql,
                    issues,
                    recommendations,
                    score
            );
        }

        score[0] = Math.max(score[0], 0);

        return new Suggestion(
                score[0],
                getSeverity(score[0]),
                issues,
                recommendations
        );
    }

    private String getSeverity(int score) {

        if (score >= 90)
            return "Low";

        if (score >= 70)
            return "Medium";

        if (score >= 40)
            return "High";

        return "Critical";
    }

}