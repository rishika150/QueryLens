package com.rishika.sqlanalyzer.ui.panels;

import com.rishika.sqlanalyzer.model.DashboardStats;
import com.rishika.sqlanalyzer.ui.components.RoundedPanel;
import com.rishika.sqlanalyzer.ui.theme.AppTheme;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class AnalyzerPanel extends JPanel {

    private final JLabel totalQueriesLabel = new JLabel("0");
    private final JLabel averageTimeLabel = new JLabel("0.00 ms");
    private final JLabel averageScoreLabel = new JLabel("0.00");
    private final JLabel highestScoreLabel = new JLabel("0");
    private final JLabel lowestScoreLabel = new JLabel("0");

    private final JLabel performanceScoreLabel = new JLabel("-- / 100");
    private final JLabel queryTypeLabel = new JLabel("-");
    private final JLabel rowsReturnedLabel = new JLabel("-");

    private final JTextArea analysisArea = createTextArea();
    private final JTextArea indexArea = createTextArea();
    private final JTextArea rewriteArea = createTextArea();

    private final JTabbedPane tabbedPane = new JTabbedPane();

    public AnalyzerPanel() {
        super(new MigLayout(
                "insets 12, fill",
                "[grow]",
                "[][grow 30][][][grow 70]"
        ));

        setBackground(AppTheme.BACKGROUND);

        JLabel dashboardTitle = createSectionTitle("Dashboard");
        JLabel analyzerTitle = createSectionTitle("SQL Analyzer");

        analysisArea.setText(
                "Run a query to view issues and recommendations."
        );

        indexArea.setText(
                "Index recommendations will appear here."
        );

        rewriteArea.setText(
                "Query rewrite suggestions will appear here."
        );

        tabbedPane.addTab(
                "Analysis",
                createScrollPane(analysisArea)
        );

        tabbedPane.addTab(
                "Index Advisor",
                createScrollPane(indexArea)
        );

        tabbedPane.addTab(
                "Rewrite",
                createScrollPane(rewriteArea)
        );

        tabbedPane.setBackground(AppTheme.CARD);
        tabbedPane.setForeground(AppTheme.TEXT);

        add(dashboardTitle, "wrap");
        add(createDashboardGrid(), "growx, wrap");

        add(analyzerTitle, "gaptop 10, wrap");
        add(createScoreCard(), "growx, wrap");
        add(createInfoRow(), "growx, wrap");
        add(tabbedPane, "grow");
    }

    private JPanel createDashboardGrid() {
        JPanel dashboard = new JPanel(
                new GridLayout(2, 3, 10, 10)
        );

        dashboard.setOpaque(false);

        dashboard.add(
                createStatCard(
                        "Queries",
                        totalQueriesLabel
                )
        );

        dashboard.add(
                createStatCard(
                        "Average Time",
                        averageTimeLabel
                )
        );

        dashboard.add(
                createStatCard(
                        "Average Score",
                        averageScoreLabel
                )
        );

        dashboard.add(
                createStatCard(
                        "Highest Score",
                        highestScoreLabel
                )
        );

        dashboard.add(
                createStatCard(
                        "Lowest Score",
                        lowestScoreLabel
                )
        );

        dashboard.add(
                createStatCard(
                        "Status",
                        new JLabel("Active")
                )
        );

        return dashboard;
    }

    private JPanel createStatCard(
            String titleText,
            JLabel valueLabel
    ) {
        RoundedPanel card = new RoundedPanel(18);
        card.setLayout(new BorderLayout(0, 8));

        JLabel title = new JLabel(titleText);

        title.setFont(
                new Font("Arial", Font.PLAIN, 12)
        );

        title.setForeground(AppTheme.SUBTEXT);

        valueLabel.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        valueLabel.setForeground(AppTheme.TEXT);

        card.add(title, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createScoreCard() {
        RoundedPanel card = new RoundedPanel(18);
        card.setLayout(new BorderLayout(0, 8));

        JLabel title = new JLabel("Performance Score");

        title.setFont(
                new Font("Arial", Font.PLAIN, 12)
        );

        title.setForeground(AppTheme.SUBTEXT);

        performanceScoreLabel.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        performanceScoreLabel.setForeground(
                AppTheme.GREEN
        );

        card.add(title, BorderLayout.NORTH);
        card.add(performanceScoreLabel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createInfoRow() {
        JPanel row = new JPanel(
                new GridLayout(1, 2, 10, 0)
        );

        row.setOpaque(false);

        row.add(
                createInfoCard(
                        "Query Type",
                        queryTypeLabel
                )
        );

        row.add(
                createInfoCard(
                        "Rows Returned",
                        rowsReturnedLabel
                )
        );

        return row;
    }

    private JPanel createInfoCard(
            String titleText,
            JLabel valueLabel
    ) {
        RoundedPanel card = new RoundedPanel(18);
        card.setLayout(new BorderLayout(0, 8));

        JLabel title = new JLabel(titleText);

        title.setFont(
                new Font("Arial", Font.PLAIN, 12)
        );

        title.setForeground(AppTheme.SUBTEXT);

        valueLabel.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        valueLabel.setForeground(AppTheme.TEXT);

        card.add(title, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private JLabel createSectionTitle(String text) {
        JLabel label = new JLabel(text);

        label.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        label.setForeground(AppTheme.TEXT);

        return label;
    }

    private JTextArea createTextArea() {
        JTextArea area = new JTextArea();

        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        area.setFont(
                new Font(
                        Font.MONOSPACED,
                        Font.PLAIN,
                        13
                )
        );

        area.setBackground(AppTheme.CARD);
        area.setForeground(AppTheme.TEXT);
        area.setCaretColor(AppTheme.TEXT);

        area.setMargin(
                new Insets(12, 12, 12, 12)
        );

        return area;
    }

    private JScrollPane createScrollPane(
            JTextArea area
    ) {
        JScrollPane scrollPane = new JScrollPane(
                area,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrollPane.setBackground(AppTheme.CARD);

        scrollPane.getViewport().setBackground(
                AppTheme.CARD
        );

        return scrollPane;
    }

    public void setAnalysis(String text) {
        analysisArea.setText(
                text == null || text.isBlank()
                        ? "No analysis available."
                        : text
        );

        analysisArea.setCaretPosition(0);
        tabbedPane.setSelectedIndex(0);
    }

    public void setIndexRecommendation(String text) {
        indexArea.setText(
                text == null || text.isBlank()
                        ? "No index recommendation available."
                        : text
        );

        indexArea.setCaretPosition(0);
    }

    public void setRewriteSuggestion(String text) {
        rewriteArea.setText(
                text == null || text.isBlank()
                        ? "No rewrite suggestion available."
                        : text
        );

        rewriteArea.setCaretPosition(0);
    }

    public void setSuggestions(String text) {
        setAnalysis(text);
    }

    public void setPerformanceScore(String score) {
        performanceScoreLabel.setText(score);
        updateScoreColor(score);
    }

    private void updateScoreColor(String scoreText) {
        try {
            int score = Integer.parseInt(
                    scoreText.split("/")[0].trim()
            );

            if (score >= 90) {
                performanceScoreLabel.setForeground(
                        AppTheme.GREEN
                );
            } else if (score >= 70) {
                performanceScoreLabel.setForeground(
                        AppTheme.ORANGE
                );
            } else {
                performanceScoreLabel.setForeground(
                        AppTheme.RED
                );
            }

        } catch (Exception ignored) {
            performanceScoreLabel.setForeground(
                    AppTheme.SUBTEXT
            );
        }
    }

    public void setQueryType(String type) {
        queryTypeLabel.setText(type);
    }

    public void setRowsReturned(String rows) {
        rowsReturnedLabel.setText(rows);
    }

    public void updateDashboard(DashboardStats stats) {
        totalQueriesLabel.setText(
                String.valueOf(stats.getTotalQueries())
        );

        averageTimeLabel.setText(
                String.format(
                        "%.2f ms",
                        stats.getAverageExecutionTime()
                )
        );

        averageScoreLabel.setText(
                String.format(
                        "%.2f",
                        stats.getAverageScore()
                )
        );

        highestScoreLabel.setText(
                String.valueOf(stats.getHighestScore())
        );

        lowestScoreLabel.setText(
                String.valueOf(stats.getLowestScore())
        );
    }
}