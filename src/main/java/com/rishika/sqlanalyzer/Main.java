package com.rishika.sqlanalyzer;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
import com.rishika.sqlanalyzer.ui.MainFrame;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        FlatOneDarkIJTheme.setup();

        UIManager.put("Component.arc", 10);
        UIManager.put("Button.arc", 10);
        UIManager.put("TextComponent.arc", 8);
        UIManager.put("ProgressBar.arc", 10);
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.width", 12);

        UIManager.put("Panel.background", new Color(18, 18, 20));
        UIManager.put("Table.background", new Color(32, 33, 36));
        UIManager.put("Table.alternateRowColor", new Color(37, 38, 41));
        UIManager.put("Table.gridColor", new Color(62, 63, 68));
        UIManager.put("Table.selectionBackground", new Color(44, 83, 140));
        UIManager.put("Table.selectionForeground", Color.WHITE);

        UIManager.put("TabbedPane.showTabSeparators", true);
        UIManager.put("TabbedPane.tabHeight", 34);

        UIManager.put("SplitPaneDivider.gripDotCount", 0);
        UIManager.put("SplitPane.dividerSize", 7);

        FlatLaf.updateUI();

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}