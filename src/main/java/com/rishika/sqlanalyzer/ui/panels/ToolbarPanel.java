package com.rishika.sqlanalyzer.ui.panels;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class ToolbarPanel extends JPanel {

    private final JButton executeButton = new JButton("▶ Execute");
    private final JButton explainButton = new JButton("📊 Explain");
    private final JButton clearButton = new JButton("🧹 Clear");
    private final JButton historyButton = new JButton("📜 History");
    private final JButton exportButton = new JButton("💾 Export CSV");

    public ToolbarPanel() {
        super(new MigLayout("insets 8 10 8 10", "[]10[]10[]push[]10[]", "[]"));

        executeButton.setFont(new Font("Arial", Font.BOLD, 13));
        executeButton.setPreferredSize(new Dimension(120, 34));

        explainButton.setPreferredSize(new Dimension(115, 34));
        clearButton.setPreferredSize(new Dimension(105, 34));
        historyButton.setPreferredSize(new Dimension(115, 34));
        exportButton.setPreferredSize(new Dimension(135, 34));

        add(executeButton);
        add(explainButton);
        add(clearButton);
        add(historyButton, "gap push");
        add(exportButton);
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