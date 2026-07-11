package com.rishika.sqlanalyzer.ui.panels;

import com.rishika.sqlanalyzer.ui.theme.AppTheme;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class StatusBar extends JPanel {

    private final JLabel statusLabel = new JLabel("Ready");
    private final JLabel connectionIndicator = new JLabel("Disconnected");

    public StatusBar() {
        super(new MigLayout(
                "insets 7 12 7 12, fillx",
                "[grow][]",
                "[]"
        ));

        setBackground(AppTheme.CARD);

        setBorder(BorderFactory.createMatteBorder(
                1,
                0,
                0,
                0,
                AppTheme.BORDER
        ));

        statusLabel.setForeground(AppTheme.SUBTEXT);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        connectionIndicator.setFont(new Font("Arial", Font.BOLD, 12));

        add(statusLabel, "growx");
        add(connectionIndicator, "align right");

        setConnected(false);
    }

    public void setStatus(String text) {
        statusLabel.setText(text);
    }

    public void setConnected(boolean connected) {
        if (connected) {
            connectionIndicator.setText("● Connected to MySQL");
            connectionIndicator.setForeground(AppTheme.GREEN);
        } else {
            connectionIndicator.setText("● Disconnected");
            connectionIndicator.setForeground(AppTheme.RED);
        }
    }
}