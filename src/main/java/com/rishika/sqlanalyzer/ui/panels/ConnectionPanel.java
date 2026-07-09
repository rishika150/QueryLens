package com.rishika.sqlanalyzer.ui.panels;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class ConnectionPanel extends JPanel {

    private final JTextField hostField = new JTextField("localhost");
    private final JTextField portField = new JTextField("3306");
    private final JTextField databaseField = new JTextField("test");
    private final JTextField usernameField = new JTextField("root");
    private final JPasswordField passwordField = new JPasswordField();
    private final JButton connectButton = new JButton("Connect");

    public ConnectionPanel() {
        super(new MigLayout(
                "insets 10 14 10 14, fillx",
                "[][grow][][80!][][grow][][grow][][grow][]",
                "[]"
        ));

        JLabel label = new JLabel("Database Connection");
        label.setFont(new Font("Arial", Font.BOLD, 14));

        connectButton.setPreferredSize(new Dimension(120, 34));
        connectButton.setFont(new Font("Arial", Font.BOLD, 13));

        add(label, "span, wrap");

        add(new JLabel("Host"));
        add(hostField, "growx");

        add(new JLabel("Port"));
        add(portField);

        add(new JLabel("Database"));
        add(databaseField, "growx");

        add(new JLabel("Username"));
        add(usernameField, "growx");

        add(new JLabel("Password"));
        add(passwordField, "growx");

        add(connectButton);
    }

    public String getHost() { return hostField.getText().trim(); }

    public String getPort() { return portField.getText().trim(); }

    public String getDatabase() { return databaseField.getText().trim(); }

    public String getUsername() { return usernameField.getText().trim(); }

    public String getPassword() { return new String(passwordField.getPassword()); }

    public JButton getConnectButton() { return connectButton; }
}