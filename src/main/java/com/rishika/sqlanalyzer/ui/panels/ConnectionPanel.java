package com.rishika.sqlanalyzer.ui.panels;

import com.rishika.sqlanalyzer.ui.theme.AppTheme;
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
                "insets 12 14 12 14, fillx",
                "[][grow][][75!][][grow][][grow][][grow][]",
                "[]"
        ));

        setBackground(AppTheme.CARD);

        BorderFactory.createEmptyBorder(
                10,
                10,
                10,
                10
        );

        styleField(hostField);
        styleField(portField);
        styleField(databaseField);
        styleField(usernameField);
        styleField(passwordField);

        connectButton.setPreferredSize(new Dimension(115, 36));
        connectButton.setFont(new Font("Arial", Font.BOLD, 13));
        connectButton.setBackground(AppTheme.BLUE);
        connectButton.setForeground(Color.WHITE);
        connectButton.setFocusPainted(false);
        connectButton.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );

        connectButton.putClientProperty(
                "JButton.buttonType",
                "roundRect"
        );

        add(createLabel("Host"));
        add(hostField, "growx");

        add(createLabel("Port"));
        add(portField, "growx");

        add(createLabel("Database"));
        add(databaseField, "growx");

        add(createLabel("Username"));
        add(usernameField, "growx");

        add(createLabel("Password"));
        add(passwordField, "growx");

        add(connectButton);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(AppTheme.SUBTEXT);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        return label;
    }

    private void styleField(JTextField field) {
        field.setBackground(AppTheme.SURFACE);
        field.setForeground(AppTheme.TEXT);
        field.setCaretColor(AppTheme.TEXT);
        field.setSelectionColor(AppTheme.SELECTION);

        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.BORDER),
                BorderFactory.createEmptyBorder(7, 9, 7, 9)
        ));
    }

    public String getHost() {
        return hostField.getText().trim();
    }

    public String getPort() {
        return portField.getText().trim();
    }

    public String getDatabase() {
        return databaseField.getText().trim();
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public JButton getConnectButton() {
        return connectButton;
    }
}