package com.rishika.sqlanalyzer.ui.components;

import com.rishika.sqlanalyzer.ui.theme.AppTheme;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {

    private final int cornerRadius;

    public RoundedPanel() {
        this(16);
    }

    public RoundedPanel(int cornerRadius) {
        this.cornerRadius = cornerRadius;

        setOpaque(false);

        setBorder(BorderFactory.createEmptyBorder(
                14,
                16,
                14,
                16
        ));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(AppTheme.CARD);

        g2.fillRoundRect(
                0,
                0,
                getWidth(),
                getHeight(),
                cornerRadius,
                cornerRadius
        );

        g2.setColor(AppTheme.BORDER);

        g2.drawRoundRect(
                0,
                0,
                getWidth() - 1,
                getHeight() - 1,
                cornerRadius,
                cornerRadius
        );

        g2.dispose();

        super.paintComponent(graphics);
    }
}