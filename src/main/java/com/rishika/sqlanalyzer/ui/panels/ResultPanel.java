package com.rishika.sqlanalyzer.ui.panels;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ResultPanel extends JPanel {

    private final JTable resultTable = new JTable();
    private final JLabel executionTimeLabel = new JLabel("Execution time: -- ms");

    public ResultPanel() {
        super(new MigLayout("insets 10, fill", "[grow]", "[][grow][]"));

        JLabel title = new JLabel("Query Results");
        title.setFont(new Font("Arial", Font.BOLD, 15));

        resultTable.setRowHeight(30);
        resultTable.setFillsViewportHeight(true);
        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        resultTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        resultTable.setFont(new Font("Arial", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        executionTimeLabel.setFont(new Font("Arial", Font.BOLD, 13));

        add(title, "split 2");
        add(executionTimeLabel, "align right, wrap");
        add(scrollPane, "grow, wrap");
    }

    public void setTableModel(DefaultTableModel model) {
        resultTable.setModel(model);
    }

    public void setExecutionTime(long ms) {
        executionTimeLabel.setText("Execution time: " + ms + " ms");
    }

    public int getRowCount() {
        return resultTable.getRowCount();
    }
    public JTable getResultTable() {
        return resultTable;
    }
}