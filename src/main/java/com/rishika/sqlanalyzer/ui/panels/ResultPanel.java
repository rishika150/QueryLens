package com.rishika.sqlanalyzer.ui.panels;

import com.rishika.sqlanalyzer.ui.theme.AppTheme;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ResultPanel extends JPanel {

    private final JTable resultTable = new JTable();
    private final JLabel executionTimeLabel =
            new JLabel("Execution time: -- ms");

    public ResultPanel() {
        super(new MigLayout(
                "insets 10, fill",
                "[grow]",
                "[][grow]"
        ));

        setBackground(AppTheme.BACKGROUND);
        setBorder(
                BorderFactory.createEmptyBorder(8,8,8,8)
        );

        styleTable();

        executionTimeLabel.setForeground(AppTheme.SUBTEXT);
        executionTimeLabel.setFont(
                new Font("Arial", Font.BOLD, 12)
        );

        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(AppTheme.CARD);

        add(executionTimeLabel, "align right, wrap");
        add(scrollPane, "grow");
    }

    private void styleTable() {
        resultTable.setRowHeight(30);
        resultTable.setFillsViewportHeight(true);
        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        resultTable.setAutoCreateRowSorter(true);

        resultTable.setBackground(AppTheme.CARD);
        resultTable.setForeground(AppTheme.TEXT);
        resultTable.setSelectionBackground(AppTheme.SELECTION);
        resultTable.setSelectionForeground(Color.WHITE);

        resultTable.setShowGrid(false);
        resultTable.setIntercellSpacing(new Dimension(0, 0));

        resultTable.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );

        JTableHeader header = resultTable.getTableHeader();

        header.setBackground(AppTheme.SURFACE);
        header.setForeground(AppTheme.TEXT);
        header.setReorderingAllowed(false);
        header.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        DefaultTableCellRenderer headerRenderer =
                (DefaultTableCellRenderer) header.getDefaultRenderer();

        headerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );
    }

    public void setTableModel(DefaultTableModel model) {
        resultTable.setModel(model);

        for (int column = 0;
             column < resultTable.getColumnCount();
             column++) {

            String columnName =
                    resultTable.getColumnName(column).toUpperCase();

            resultTable.getColumnModel()
                    .getColumn(column)
                    .setHeaderValue(columnName);
        }

        resultTable.getTableHeader().repaint();
    }

    public void setExecutionTime(long ms) {
        executionTimeLabel.setText(
                "Execution time: " + ms + " ms"
        );
    }

    public int getRowCount() {
        return resultTable.getRowCount();
    }

    public JTable getResultTable() {
        return resultTable;
    }
}