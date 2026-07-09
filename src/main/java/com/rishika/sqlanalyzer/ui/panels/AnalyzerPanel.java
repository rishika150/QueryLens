package com.rishika.sqlanalyzer.ui.panels;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class AnalyzerPanel extends JPanel {

    private final JLabel performanceScoreLabel = new JLabel("-- / 100");
    private final JLabel queryTypeLabel = new JLabel("-");
    private final JLabel rowsReturnedLabel = new JLabel("-");
    private final JTextArea suggestionsArea = new JTextArea();

    public AnalyzerPanel() {
        super(new MigLayout("insets 12, fill", "[grow]", "[][][][][grow]"));

        JLabel title = new JLabel("SQL Analyzer");
        title.setFont(new Font("Arial", Font.BOLD, 16));

        performanceScoreLabel.setFont(new Font("Arial", Font.BOLD, 22));
        performanceScoreLabel.setForeground(new Color(80, 220, 120));

        queryTypeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        rowsReturnedLabel.setFont(new Font("Arial", Font.BOLD, 14));

        suggestionsArea.setEditable(false);
        suggestionsArea.setLineWrap(true);
        suggestionsArea.setWrapStyleWord(true);
        suggestionsArea.setFont(new Font("Arial", Font.PLAIN, 13));
        suggestionsArea.setMargin(new Insets(10, 10, 10, 10));
        suggestionsArea.setText("Run a query to see optimization suggestions.");

        add(title, "wrap");
        add(createInfoCard("Performance Score", performanceScoreLabel), "growx, wrap");
        add(createInfoCard("Query Type", queryTypeLabel), "growx, wrap");
        add(createInfoCard("Rows Returned", rowsReturnedLabel), "growx, wrap");
        add(new JScrollPane(suggestionsArea), "grow");
    }

    private JPanel createInfoCard(String title, JLabel valueLabel) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 80)),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    public void setSuggestions(String text) {
        suggestionsArea.setText(text);
    }

    public void setPerformanceScore(String score) {
        performanceScoreLabel.setText(score);
    }

    public void setQueryType(String type) {
        queryTypeLabel.setText(type);
    }

    public void setRowsReturned(String rows) {
        rowsReturnedLabel.setText(rows);
    }
}