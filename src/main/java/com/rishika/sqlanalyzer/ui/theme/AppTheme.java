package com.rishika.sqlanalyzer.ui.theme;

import java.awt.Color;

public final class AppTheme {

    private AppTheme() {
        // Prevents creating objects of this utility class.
    }

    // Main application background.
    public static final Color BACKGROUND =
            new Color(18, 18, 20);

    // Background for panels, cards, text areas and tables.
    public static final Color CARD =
            new Color(32, 33, 36);

    // Slightly lighter background for inputs and nested areas.
    public static final Color SURFACE =
            new Color(42, 43, 46);

    // Borders and divider lines.
    public static final Color BORDER =
            new Color(62, 63, 68);

    // Primary text.
    public static final Color TEXT =
            new Color(242, 242, 242);

    // Labels and secondary text.
    public static final Color SUBTEXT =
            new Color(170, 170, 178);

    // Main action color.
    public static final Color BLUE =
            new Color(59, 130, 246);

    // Successful state.
    public static final Color GREEN =
            new Color(34, 197, 94);

    // Warning state.
    public static final Color ORANGE =
            new Color(245, 158, 11);

    // Error state.
    public static final Color RED =
            new Color(239, 68, 68);

    // Selected rows and highlighted text.
    public static final Color SELECTION =
            new Color(44, 83, 140);
}
