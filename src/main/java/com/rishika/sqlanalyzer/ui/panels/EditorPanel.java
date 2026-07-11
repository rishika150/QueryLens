package com.rishika.sqlanalyzer.ui.panels;

import com.rishika.sqlanalyzer.ui.theme.AppTheme;
import net.miginfocom.swing.MigLayout;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;
import javax.swing.KeyStroke;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class EditorPanel extends JPanel {

    private final RSyntaxTextArea queryArea = new RSyntaxTextArea();

    public EditorPanel() {
        super(new MigLayout("insets 10, fill", "[grow]", "[grow]"));

        setBackground(AppTheme.BACKGROUND);
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(AppTheme.BORDER),
                "SQL Editor"
        ));

        queryArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SQL);
        queryArea.setCodeFoldingEnabled(true);
        queryArea.setBracketMatchingEnabled(true);
        queryArea.setAutoIndentEnabled(true);
        queryArea.setTabSize(4);
        queryArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));

        applyDarkEditorTheme();

        queryArea.setText("""
                SELECT id, name, department, salary
                FROM employees
                WHERE department = 'Engineering';
                """);

        RTextScrollPane scrollPane = new RTextScrollPane(queryArea);
        scrollPane.setLineNumbersEnabled(true);
        scrollPane.setFoldIndicatorEnabled(true);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        scrollPane.setBackground(AppTheme.BACKGROUND);
        scrollPane.getViewport().setBackground(AppTheme.BACKGROUND);

        scrollPane.getGutter().setBackground(AppTheme.CARD);
        scrollPane.getGutter().setLineNumberColor(AppTheme.SUBTEXT);
        scrollPane.getGutter().setBorderColor(AppTheme.BORDER);

        add(scrollPane, "grow");
    }

    private void applyDarkEditorTheme() {
        try (InputStream inputStream = getClass().getResourceAsStream(
                "/org/fife/ui/rsyntaxtextarea/themes/dark.xml"
        )) {
            if (inputStream != null) {
                Theme theme = Theme.load(inputStream);
                theme.apply(queryArea);
            }
        } catch (Exception ignored) {
            // Falls back to the manual colors below.
        }

        queryArea.setBackground(AppTheme.BACKGROUND);
        queryArea.setForeground(AppTheme.TEXT);
        queryArea.setCaretColor(AppTheme.TEXT);
        queryArea.setCurrentLineHighlightColor(AppTheme.SURFACE);
        queryArea.setSelectionColor(AppTheme.SELECTION);
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

    public RSyntaxTextArea getQueryArea() {
        return queryArea;
    }
    public void registerExecuteShortcut(Runnable action) {

        int mask = Toolkit.getDefaultToolkit()
                .getMenuShortcutKeyMaskEx();

        queryArea.getInputMap().put(
                KeyStroke.getKeyStroke(
                        KeyEvent.VK_ENTER,
                        mask
                ),
                "executeQuery"
        );

        queryArea.getActionMap().put(
                "executeQuery",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        action.run();
                    }
                }
        );
    }
}