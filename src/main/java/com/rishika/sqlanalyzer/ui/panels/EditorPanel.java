package com.rishika.sqlanalyzer.ui.panels;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class EditorPanel extends JPanel {

    private final JTextArea queryArea = new JTextArea();

    public EditorPanel() {
        super(new MigLayout("insets 10, fill", "[grow]", "[grow]"));

        setBorder(BorderFactory.createTitledBorder("SQL Editor"));

        queryArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        queryArea.setText("SELECT * FROM employees;");
        queryArea.setTabSize(4);
        queryArea.setMargin(new Insets(14, 14, 14, 14));
        queryArea.setLineWrap(false);
        queryArea.setWrapStyleWord(false);

        JScrollPane scrollPane = new JScrollPane(queryArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(scrollPane, "grow");
    }

    public String getQuery() {
        return queryArea.getText();
    }

    public void setQuery(String sql) {
        queryArea.setText(sql);
    }

    public void clearQuery() {
        queryArea.setText("");
    }
}