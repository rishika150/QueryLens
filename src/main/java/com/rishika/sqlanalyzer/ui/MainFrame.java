package com.rishika.sqlanalyzer.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("SQL Performance Analyzer");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("SQL Performance Analyzer", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));

        add(title, BorderLayout.CENTER);
    }
}
