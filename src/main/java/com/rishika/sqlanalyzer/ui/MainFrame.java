package com.rishika.sqlanalyzer.ui;

import com.rishika.sqlanalyzer.config.DatabaseConfig;
import com.rishika.sqlanalyzer.model.QueryResult;
import com.rishika.sqlanalyzer.service.ConnectionTester;
import com.rishika.sqlanalyzer.service.ExplainService;
import com.rishika.sqlanalyzer.service.QueryExecutor;
import com.rishika.sqlanalyzer.ui.panels.*;
import com.rishika.sqlanalyzer.model.Suggestion;
import com.rishika.sqlanalyzer.service.SuggestionService;
import com.rishika.sqlanalyzer.model.ExplainAnalysis;
import com.rishika.sqlanalyzer.service.ExplainAnalyzerService;
import com.rishika.sqlanalyzer.model.QueryHistory;
import com.rishika.sqlanalyzer.service.HistoryService;
import com.rishika.sqlanalyzer.util.CSVExporter;
import com.rishika.sqlanalyzer.model.DashboardStats;
import com.rishika.sqlanalyzer.service.IndexRecommendationService;
import com.rishika.sqlanalyzer.service.QueryRewriteService;
import java.awt.Taskbar;
import java.io.File;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final ConnectionPanel connectionPanel = new ConnectionPanel();
    private final EditorPanel editorPanel = new EditorPanel();
    private final ToolbarPanel toolbarPanel = new ToolbarPanel();
    private final ResultPanel resultPanel = new ResultPanel();
    private final AnalyzerPanel analyzerPanel = new AnalyzerPanel();
    private final StatusBar statusBar = new StatusBar();

    private final ConnectionTester connectionTester = new ConnectionTester();
    private final QueryExecutor queryExecutor = new QueryExecutor();
    private final ExplainService explainService = new ExplainService();
    private final ExplainAnalyzerService explainAnalyzerService = new ExplainAnalyzerService();
    private final SuggestionService suggestionService = new SuggestionService();
    private final IndexRecommendationService indexRecommendationService = new IndexRecommendationService();
    private final HistoryService historyService = new HistoryService();
    private final CSVExporter csvExporter = new CSVExporter();
    private final QueryRewriteService queryRewriteService = new QueryRewriteService();

    private DatabaseConfig activeConfig;

    public MainFrame() {
        setTitle("QueryLens — Rule-Based SQL Performance Analyzer");
        try {
            ImageIcon icon = new ImageIcon(
                    MainFrame.class.getResource("/icons/querylens.png")
            );

            setIconImage(icon.getImage());

            if (Taskbar.isTaskbarSupported()) {
                Taskbar.getTaskbar().setIconImage(icon.getImage());
            }
        } catch (Exception e) {
            System.out.println("Application icon could not be loaded: " + e.getMessage());
        }


        setSize(1350, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(
                new Color(18, 18, 20)
        );

        setLayout(new BorderLayout());

        add(buildHeader(), BorderLayout.NORTH);
        add(buildWorkspace(), BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);

        wireEvents();
        editorPanel.registerExecuteShortcut(
                this::executeQuery
        );
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());

        JLabel title = new JLabel("QueryLens  |  SQL Performance Analyzer");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(12, 14, 8, 14));

        header.add(title, BorderLayout.NORTH);
        header.add(connectionPanel, BorderLayout.CENTER);
        header.add(toolbarPanel, BorderLayout.SOUTH);

        return header;
    }

    private JSplitPane buildWorkspace() {
        JSplitPane editorAndAnalyzer = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                editorPanel,
                analyzerPanel
        );

        editorAndAnalyzer.setResizeWeight(0.65);
        editorAndAnalyzer.setContinuousLayout(true);

        JSplitPane workspace = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                editorAndAnalyzer,
                resultPanel
        );

        workspace.setResizeWeight(0.56);
        workspace.setDividerLocation(0.56);
        workspace.setContinuousLayout(true);

        return workspace;
    }

    private void wireEvents() {
        connectionPanel.getConnectButton().addActionListener(e -> testConnection());
        toolbarPanel.getExecuteButton().addActionListener(e -> executeQuery());
        toolbarPanel.getExplainButton().addActionListener(e -> explainQuery());
        toolbarPanel.getClearButton().addActionListener(e -> clearEditor());
        toolbarPanel.getHistoryButton().addActionListener(e -> showHistory());
        toolbarPanel.getExportButton().addActionListener(e -> exportResults());
    }

    private void testConnection() {
        DatabaseConfig config = buildConfigFromPanel();

        boolean connected = connectionTester.test(config);

        if (connected) {
            activeConfig = config;
            statusBar.setConnected(true);
            statusBar.setStatus("Connected to database: " + connectionPanel.getDatabase());

            JOptionPane.showMessageDialog(
                    this,
                    "Database connected successfully.",
                    "Connection Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            activeConfig = null;
            statusBar.setConnected(false);
            statusBar.setStatus("Connection failed. Check database credentials.");

            JOptionPane.showMessageDialog(
                    this,
                    "Could not connect to database.",
                    "Connection Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void executeQuery() {
        if (activeConfig == null) {
            statusBar.setStatus("Please connect to a database first.");
            JOptionPane.showMessageDialog(this, "Please connect to a database first.");
            return;
        }

        String sql = editorPanel.getQuery();
        QueryResult result = queryExecutor.execute(activeConfig, sql);

        resultPanel.setTableModel(result.getTableModel());
        resultPanel.setExecutionTime(result.getExecutionTimeMillis());

        analyzerPanel.setRowsReturned(String.valueOf(result.getTableModel().getRowCount()));
        analyzerPanel.setQueryType(getQueryType(sql));


        statusBar.setStatus(result.getMessage());

        if (result.isSuccess()) {
            Suggestion suggestion = suggestionService.analyze(sql);

            String indexRecommendation =
                    indexRecommendationService.recommend(sql);

            String rewriteSuggestion =
                    queryRewriteService.suggestRewrite(sql);

            analyzerPanel.setPerformanceScore(
                    suggestion.getPerformanceScore() + " / 100"
            );

            String analysis =
                    "Analysis Summary\n"
                            + "────────────────────────────\n\n"
                            + "Severity\n"
                            + suggestion.getSeverity()
                            + "\n\n"
                            + suggestion.getFormattedMessage();

            analyzerPanel.setAnalysis(analysis);

            analyzerPanel.setIndexRecommendation(indexRecommendation);
            analyzerPanel.setRewriteSuggestion(rewriteSuggestion);

            historyService.add(new QueryHistory(
                    sql,
                    getQueryType(sql),
                    result.getExecutionTimeMillis(),
                    suggestion.getPerformanceScore(),
                    "Success"
            ));

            DashboardStats stats = historyService.getDashboardStats();
            analyzerPanel.updateDashboard(stats);
        } else {
            analyzerPanel.setPerformanceScore("-- / 100");
            analyzerPanel.setAnalysis("Error: " + result.getMessage());
            analyzerPanel.setIndexRecommendation("");
            analyzerPanel.setRewriteSuggestion("");

            JOptionPane.showMessageDialog(
                    this,
                    result.getMessage(),
                    "SQL Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void explainQuery() {
        if (activeConfig == null) {
            statusBar.setStatus("Please connect to a database first.");
            JOptionPane.showMessageDialog(this, "Please connect to a database first.");
            return;
        }

        String sql = editorPanel.getQuery();
        QueryResult result = explainService.explain(activeConfig, sql);

        resultPanel.setTableModel(result.getTableModel());
        resultPanel.setExecutionTime(result.getExecutionTimeMillis());

        analyzerPanel.setQueryType("EXPLAIN");
        analyzerPanel.setRowsReturned(String.valueOf(result.getTableModel().getRowCount()));
        analyzerPanel.setPerformanceScore(result.isSuccess() ? "EXPLAIN" : "--");

        statusBar.setStatus(result.getMessage());

        if (!result.isSuccess()) {
            analyzerPanel.setSuggestions("Error: " + result.getMessage());
            JOptionPane.showMessageDialog(this, result.getMessage(), "EXPLAIN Error", JOptionPane.ERROR_MESSAGE);
        } else {
            ExplainAnalysis analysis = explainAnalyzerService.analyze(result);

            analyzerPanel.setPerformanceScore(analysis.getScore() + " / 100");
            analyzerPanel.setSuggestions(
                    "Severity: " + analysis.getSeverity() + "\n\n" + analysis.getMessage()
            );
        }
    }

    private void clearEditor() {
        editorPanel.clearQuery();
        statusBar.setStatus("SQL editor cleared.");
    }

    private DatabaseConfig buildConfigFromPanel() {
        return new DatabaseConfig(
                connectionPanel.getHost(),
                connectionPanel.getPort(),
                connectionPanel.getDatabase(),
                connectionPanel.getUsername(),
                connectionPanel.getPassword()
        );
    }

    private String getQueryType(String sql) {
        if (sql == null || sql.isBlank()) return "-";

        String upper = sql.trim().toUpperCase();

        if (upper.startsWith("SELECT")) return "SELECT";
        if (upper.startsWith("INSERT")) return "INSERT";
        if (upper.startsWith("UPDATE")) return "UPDATE";
        if (upper.startsWith("DELETE")) return "DELETE";
        if (upper.startsWith("CREATE")) return "CREATE";
        if (upper.startsWith("ALTER")) return "ALTER";
        if (upper.startsWith("DROP")) return "DROP";

        return "OTHER";
    }
    private void showHistory() {
        if (historyService.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No query history available yet.");
            return;
        }

        String[] columns = {"Executed At", "Type", "Time (ms)", "Score", "Status", "Query"};
        Object[][] data = historyService.getHistory().stream()
                .map(h -> new Object[]{
                        h.getExecutedAt(),
                        h.getQueryType(),
                        h.getExecutionTimeMillis(),
                        h.getPerformanceScore(),
                        h.getStatus(),
                        h.getQuery()
                })
                .toArray(Object[][]::new);

        JTable table = new JTable(data, columns);
        table.setRowHeight(26);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 350));

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Query History",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    private void exportResults() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export Query Results");
        fileChooser.setSelectedFile(new File("query_results.csv"));

        int userChoice = fileChooser.showSaveDialog(this);

        if (userChoice == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                csvExporter.exportTable(resultPanel.getResultTable(), file);
                statusBar.setStatus("Results exported successfully.");
                JOptionPane.showMessageDialog(this, "Results exported successfully.");
            } catch (Exception e) {
                statusBar.setStatus("Export failed.");
                JOptionPane.showMessageDialog(this, e.getMessage(), "Export Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}