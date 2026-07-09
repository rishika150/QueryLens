package com.rishika.sqlanalyzer.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import com.rishika.sqlanalyzer.config.DatabaseConfig;
import com.rishika.sqlanalyzer.service.ConnectionTester;
import com.rishika.sqlanalyzer.model.QueryResult;
import com.rishika.sqlanalyzer.service.QueryExecutor;

public class MainFrame extends JFrame {

    private JTextField hostField;
    private JTextField portField;
    private JTextField databaseField;
    private JTextField usernameField;
    private JPasswordField passwordField;

    private JButton connectButton;
    private JButton executeButton;
    private JButton explainButton;
    private JButton clearButton;

    private JTextArea queryArea;
    private JTable resultTable;

    private JLabel statusLabel;
    private JLabel executionTimeLabel;

    private JTextArea suggestionArea;
    private final ConnectionTester connectionTester = new ConnectionTester();
    private final QueryExecutor queryExecutor = new QueryExecutor();
    public MainFrame() {
        setTitle("SQL Performance Analyzer");
        setSize(1250, 820);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);
        add(createFooterPanel(), BorderLayout.SOUTH);
        attachEventHandlers();
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(35, 45, 60));
        header.setBorder(new EmptyBorder(14, 20, 14, 20));

        JLabel title = new JLabel("SQL Performance Analyzer");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        statusLabel = new JLabel("Not Connected");
        statusLabel.setForeground(new Color(255, 180, 80));
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));

        header.add(title, BorderLayout.WEST);
        header.add(statusLabel, BorderLayout.EAST);

        return header;
    }

    private JPanel createMainPanel() {
        JPanel main = new JPanel(new BorderLayout(12, 12));
        main.setBorder(new EmptyBorder(12, 12, 12, 12));

        main.add(createConnectionPanel(), BorderLayout.NORTH);

        JSplitPane verticalSplit = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                createEditorPanel(),
                createResultAndSuggestionPanel()
        );

        verticalSplit.setResizeWeight(0.35);
        verticalSplit.setDividerSize(8);

        main.add(verticalSplit, BorderLayout.CENTER);

        return main;
    }

    private JPanel createConnectionPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Database Connection"));

        JPanel fieldsPanel = new JPanel(new GridLayout(2, 5, 10, 8));

        hostField = new JTextField("localhost");
        portField = new JTextField("3306");
        databaseField = new JTextField("test");
        usernameField = new JTextField("root");
        passwordField = new JPasswordField();

        fieldsPanel.add(new JLabel("Host"));
        fieldsPanel.add(new JLabel("Port"));
        fieldsPanel.add(new JLabel("Database"));
        fieldsPanel.add(new JLabel("Username"));
        fieldsPanel.add(new JLabel("Password"));

        fieldsPanel.add(hostField);
        fieldsPanel.add(portField);
        fieldsPanel.add(databaseField);
        fieldsPanel.add(usernameField);
        fieldsPanel.add(passwordField);

        connectButton = new JButton("Test Connection");
        connectButton.setPreferredSize(new Dimension(150, 35));

        panel.add(fieldsPanel, BorderLayout.CENTER);
        panel.add(connectButton, BorderLayout.EAST);

        return panel;
    }

    private JPanel createEditorPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("SQL Editor"));

        queryArea = new JTextArea();
        queryArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
        queryArea.setText("SELECT * FROM your_table LIMIT 10;");
        queryArea.setBorder(new EmptyBorder(8, 8, 8, 8));

        JScrollPane queryScroll = new JScrollPane(queryArea);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        executeButton = new JButton("Execute Query");
        explainButton = new JButton("EXPLAIN Plan");
        clearButton = new JButton("Clear");

        buttonPanel.add(executeButton);
        buttonPanel.add(explainButton);
        buttonPanel.add(clearButton);

        panel.add(queryScroll, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createResultAndSuggestionPanel() {
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                createResultPanel(),
                createSuggestionPanel()
        );

        splitPane.setResizeWeight(0.72);
        splitPane.setDividerSize(8);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(splitPane, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Query Results"));

        resultTable = new JTable();
        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        resultTable.setRowHeight(24);

        JScrollPane tableScroll = new JScrollPane(resultTable);

        executionTimeLabel = new JLabel("Execution Time: -");
        executionTimeLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
        executionTimeLabel.setFont(new Font("Arial", Font.BOLD, 13));

        panel.add(tableScroll, BorderLayout.CENTER);
        panel.add(executionTimeLabel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createSuggestionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Optimization Suggestions"));

        suggestionArea = new JTextArea();
        suggestionArea.setEditable(false);
        suggestionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        suggestionArea.setBorder(new EmptyBorder(8, 8, 8, 8));
        suggestionArea.setText("Suggestions will appear here after running a query.");

        JScrollPane suggestionScroll = new JScrollPane(suggestionArea);

        panel.add(suggestionScroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createFooterPanel() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBorder(new EmptyBorder(6, 12, 6, 12));

        JLabel footerText = new JLabel("Developer Tool | Java + JDBC + MySQL");
        footerText.setFont(new Font("Arial", Font.PLAIN, 12));

        footer.add(footerText, BorderLayout.WEST);

        return footer;
    }
    private void attachEventHandlers() {
        connectButton.addActionListener(e -> testConnection());
        executeButton.addActionListener(e -> executeQuery());
        clearButton.addActionListener(e -> queryArea.setText(""));
    }

    private void testConnection() {
        DatabaseConfig config = getDatabaseConfig();

        boolean isConnected = connectionTester.test(config);

        if (isConnected) {
            statusLabel.setText("Connected");
            statusLabel.setForeground(new Color(80, 220, 120));

            JOptionPane.showMessageDialog(
                    this,
                    "Database connected successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            statusLabel.setText("Connection Failed");
            statusLabel.setForeground(new Color(255, 90, 90));

            JOptionPane.showMessageDialog(
                    this,
                    "Could not connect to database. Check host, port, database, username, and password.",
                    "Connection Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private DatabaseConfig getDatabaseConfig() {
        return new DatabaseConfig(
                hostField.getText().trim(),
                portField.getText().trim(),
                databaseField.getText().trim(),
                usernameField.getText().trim(),
                new String(passwordField.getPassword())
        );
    }
    private void executeQuery() {
        DatabaseConfig config = getDatabaseConfig();
        String sql = queryArea.getText();

        QueryResult result = queryExecutor.execute(config, sql);

        resultTable.setModel(result.getTableModel());
        executionTimeLabel.setText("Execution Time: " + result.getExecutionTimeMillis() + " ms");

        if (result.isSuccess()) {
            JOptionPane.showMessageDialog(
                    this,
                    result.getMessage(),
                    "Query Executed",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    result.getMessage(),
                    "SQL Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}