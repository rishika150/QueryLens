package com.rishika.sqlanalyzer.ui.panels;

import com.rishika.sqlanalyzer.ui.theme.AppTheme;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class ToolbarPanel extends JPanel {

    private final JButton executeButton = new JButton("Execute");
    private final JButton explainButton = new JButton("Explain");
    private final JButton clearButton = new JButton("Clear");
    private final JButton historyButton = new JButton("History");
    private final JButton exportButton = new JButton("Export CSV");

    public ToolbarPanel() {
        super(new MigLayout(
                "insets 8 12 8 12",
                "[]10[]10[]push[]10[]",
                "[]"
        ));

        setBackground(AppTheme.BACKGROUND);

        stylePrimaryButton(executeButton);
        styleSecondaryButton(explainButton);
        styleDangerButton(clearButton);
        styleSecondaryButton(historyButton);
        styleSecondaryButton(exportButton);

        executeButton.setToolTipText("Execute query (Cmd/Ctrl + Enter)");
        explainButton.setToolTipText("Generate and analyze EXPLAIN plan");
        clearButton.setToolTipText("Clear SQL editor");
        historyButton.setToolTipText("View query history");
        exportButton.setToolTipText("Export current results as CSV");

        add(executeButton);
        add(explainButton);
        add(clearButton);
        add(historyButton, "gap push");
        add(exportButton);
    }

    private void stylePrimaryButton(JButton button) {
        styleBaseButton(button);

        button.setBackground(AppTheme.BLUE);
        button.setForeground(Color.WHITE);
    }

    private void styleSecondaryButton(JButton button) {
        styleBaseButton(button);

        button.setBackground(AppTheme.SURFACE);
        button.setForeground(AppTheme.TEXT);
    }

    private void styleDangerButton(JButton button) {
        styleBaseButton(button);

        button.setBackground(AppTheme.SURFACE);
        button.setForeground(AppTheme.RED);
    }

    private void styleBaseButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setPreferredSize(new Dimension(118, 36));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.putClientProperty(
                "JButton.buttonType",
                "roundRect"
        );
    }

    public JButton getExecuteButton() {
        return executeButton;
    }

    public JButton getExplainButton() {
        return explainButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JButton getHistoryButton() {
        return historyButton;
    }

    public JButton getExportButton() {
        return exportButton;
    }
}