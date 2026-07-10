package com.rishika.sqlanalyzer.util;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVExporter {

    public void exportTable(JTable table, File file) throws IOException {
        TableModel model = table.getModel();

        try (FileWriter writer = new FileWriter(file)) {

            for (int col = 0; col < model.getColumnCount(); col++) {
                writer.write(escape(model.getColumnName(col)));

                if (col < model.getColumnCount() - 1) {
                    writer.write(",");
                }
            }

            writer.write("\n");

            for (int row = 0; row < model.getRowCount(); row++) {
                for (int col = 0; col < model.getColumnCount(); col++) {
                    Object value = model.getValueAt(row, col);
                    writer.write(escape(value == null ? "" : value.toString()));

                    if (col < model.getColumnCount() - 1) {
                        writer.write(",");
                    }
                }

                writer.write("\n");
            }
        }
    }

    private String escape(String value) {
        String escaped = value.replace("\"", "\"\"");

        if (escaped.contains(",") || escaped.contains("\"") || escaped.contains("\n")) {
            return "\"" + escaped + "\"";
        }

        return escaped;
    }
}
