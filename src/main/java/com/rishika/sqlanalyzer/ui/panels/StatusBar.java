package com.rishika.sqlanalyzer.ui.panels;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class StatusBar extends JPanel {

    private final JLabel statusLabel = new JLabel("Ready");
    private final JLabel connectionIndicator = new JLabel("● Disconnected");

    public StatusBar() {
        super(new MigLayout("insets 5 10 5 10", "[grow][]", "[]"));

        add(statusLabel, "growx");
        add(connectionIndicator, "align right");

        setConnected(false);
    }

    public void setStatus(String text) {
        statusLabel.setText(text);
    }

    public void setConnected(boolean connected) {
        if (connected) {
            connectionIndicator.setText("● Connected");
            connectionIndicator.setForeground(new Color(80, 220, 120));
        } else {
            connectionIndicator.setText("● Disconnected");
            connectionIndicator.setForeground(new Color(255, 90, 90));
        }
    }
}
