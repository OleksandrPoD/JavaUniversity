import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class MovieDatabaseApp extends JFrame {
    private Connection conn;

    
    private final String[] TABLES = {
        "cinema", "hall", "movie", "genre", "moviegenreslink", 
        "session", "client", "ticket", "order", "employee", 
        "buffet", "product", "buffetproductslink", "productcategory", "productcategorieslink"
    };

    public MovieDatabaseApp() {
        try {
            
            conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cinemadatabase", "root", "");
        } catch (SQLException e) {
            try {
                
                conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cinemadatabase", "root", "root");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Cannot connect to Database cinemadatabase");
                System.exit(1);
            }
        }

        setTitle("Movie Database Master Control App (Miromax)");
        setSize(1100, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        JTabbedPane tabs = new JTabbedPane();
        for (String t : TABLES) {
            tabs.addTab(t, new GenericTablePanel(conn, t));
        }
        add(tabs);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MovieDatabaseApp::new);
    }
}
class GenericTablePanel extends JPanel {
    private Connection conn;
    private String tableName;
    private DefaultTableModel model;
    private JTable table;
    private java.util.List<String> pkNames = new ArrayList<>();
    private java.util.List<String> colNames = new ArrayList<>();
    private TableRowSorter<DefaultTableModel> sorter;

    public GenericTablePanel(Connection c, String t) {
        this.conn = c;
        this.tableName = t;
        setLayout(new BorderLayout());

        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        initMetadata();
        loadData();

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(15);
        JButton searchBtn = new JButton("Search (Пошук)");
        JButton clearBtn = new JButton("Clear (Очистити)");

        searchBtn.addActionListener(e -> {
            String text = searchField.getText();
            if (text.trim().isEmpty()) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
        });

        clearBtn.addActionListener(e -> {
            searchField.setText("");
            sorter.setRowFilter(null);
        });

        searchPanel.add(new JLabel("Фільтр текст:"));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(clearBtn);
        topPanel.add(searchPanel);

        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> sortCombo = new JComboBox<>(colNames.toArray(new String[0]));
        JComboBox<String> orderCombo = new JComboBox<>(new String[]{"Зростання (Asc)", "Спадання (Desc)"});
        JButton sortBtn = new JButton("Сортувати");

        sortBtn.addActionListener(e -> {
            String selectedCol = (String) sortCombo.getSelectedItem();
            if (selectedCol != null) {
                int colIndex = colNames.indexOf(selectedCol);
                java.util.List<RowSorter.SortKey> sortKeys = new ArrayList<>();
                SortOrder order = orderCombo.getSelectedItem().toString().contains("Asc") ? SortOrder.ASCENDING : SortOrder.DESCENDING;
                sortKeys.add(new RowSorter.SortKey(colIndex, order));
                sorter.setSortKeys(sortKeys);
            }
        });

        sortPanel.add(new JLabel("Сортувати поле:"));
        sortPanel.add(sortCombo);
        sortPanel.add(orderCombo);
        sortPanel.add(sortBtn);
        topPanel.add(sortPanel);

        add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addBtn = new JButton("Add Record");
        JButton editBtn = new JButton("Edit Record");
        JButton deleteBtn = new JButton("Delete Record");
        JButton refreshBtn = new JButton("Refresh");

        refreshBtn.addActionListener(e -> loadData());

        deleteBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Виберіть рядок для видалення!");
                return;
            }
            
            int modelRow = table.convertRowIndexToModel(selectedRow);
            Object idValue = model.getValueAt(modelRow, 0); 
            String idColName = pkNames.isEmpty() ? colNames.get(0) : pkNames.get(0);

            int confirm = JOptionPane.showConfirmDialog(this, "Видалити запис з ID " + idValue + "?", "Підтвердження", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    String sql = "DELETE FROM `" + tableName + "` WHERE `" + idColName + "` = ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setObject(1, idValue);
                        pstmt.executeUpdate();
                        loadData(); 
                        JOptionPane.showMessageDialog(this, "Запис успішно видалено!");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Помилка видалення: " + ex.getMessage());
                }
            }
        });
        addBtn.addActionListener(e -> {
            JDialog addDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add to " + tableName, true);
            addDialog.setLayout(new BorderLayout());
            
            JPanel fieldsPanel = new JPanel(new GridLayout(colNames.size(), 2, 5, 10));
            fieldsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            
            java.util.List<JTextField> textFields = new ArrayList<>();

            for (int i = 0; i < colNames.size(); i++) {
                String colName = colNames.get(i);
                fieldsPanel.add(new JLabel(colName + ":"));
                
                JTextField textField = new JTextField(15);
                fieldsPanel.add(textField);
                textFields.add(textField);
            }

            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
            JButton okBtn = new JButton("OK");
            JButton cancelBtn = new JButton("Cancel");
            
            btnPanel.add(okBtn);
            btnPanel.add(cancelBtn);

            addDialog.add(fieldsPanel, BorderLayout.CENTER);
            addDialog.add(btnPanel, BorderLayout.SOUTH);

            cancelBtn.addActionListener(ex -> addDialog.dispose());

            okBtn.addActionListener(ex -> {
                StringBuilder sql = new StringBuilder("INSERT INTO `" + tableName + "` (");
                StringBuilder values = new StringBuilder("VALUES (");
                
                for (int i = 0; i < colNames.size(); i++) {
                    sql.append("`").append(colNames.get(i)).append("`");
                    values.append("?");
                    if (i < colNames.size() - 1) {
                        sql.append(", ");
                        values.append(", ");
                    }
                }
                sql.append(") ").append(values).append(")");

                try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
                    for (int i = 0; i < colNames.size(); i++) {
                        String text = textFields.get(i).getText().trim();
                        if (text.isEmpty()) {
                            pstmt.setNull(i + 1, java.sql.Types.VARCHAR);
                        } else {
                            pstmt.setString(i + 1, text);
                        }
                    }
                    
                    pstmt.executeUpdate();
                    loadData(); 
                    addDialog.dispose();
                    JOptionPane.showMessageDialog(this, "Запис успішно додано!");
                } catch (SQLException sQLEx) {
                    JOptionPane.showMessageDialog(addDialog, "Помилка додавання: " + sQLEx.getMessage());
                }
            });

            addDialog.pack();
            addDialog.setSize(Math.max(addDialog.getWidth(), 350), addDialog.getHeight());
            addDialog.setLocationRelativeTo(this);
            addDialog.setVisible(true);
        });
        editBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Виберіть рядок для редагування!");
                return;
            }

            int modelRow = table.convertRowIndexToModel(selectedRow);
            
            JDialog editDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit in " + tableName, true);
            editDialog.setLayout(new BorderLayout());
            
            JPanel fieldsPanel = new JPanel(new GridLayout(colNames.size(), 2, 5, 10));
            fieldsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            
            java.util.List<JTextField> textFields = new ArrayList<>();

            for (int i = 0; i < colNames.size(); i++) {
                String colName = colNames.get(i);
                fieldsPanel.add(new JLabel(colName + ":"));
                
                Object value = model.getValueAt(modelRow, i);
                JTextField textField = new JTextField(value != null ? value.toString() : "");
                
                if (i == 0 || pkNames.contains(colName)) {
                    textField.setEditable(false);
                    textField.setBackground(new Color(240, 240, 240));
                }
                
                fieldsPanel.add(textField);
                textFields.add(textField);
            }

            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
            JButton okBtn = new JButton("OK");
            JButton cancelBtn = new JButton("Cancel");
            
            btnPanel.add(okBtn);
            btnPanel.add(cancelBtn);

            editDialog.add(fieldsPanel, BorderLayout.CENTER);
            editDialog.add(btnPanel, BorderLayout.SOUTH);

            cancelBtn.addActionListener(ex -> editDialog.dispose());

            okBtn.addActionListener(ex -> {
                String idColName = pkNames.isEmpty() ? colNames.get(0) : pkNames.get(0);
                Object idValue = model.getValueAt(modelRow, 0);

                StringBuilder sql = new StringBuilder("UPDATE `" + tableName + "` SET ");
                for (int i = 1; i < colNames.size(); i++) {
                    sql.append("`").append(colNames.get(i)).append("` = ?");
                    if (i < colNames.size() - 1) {
                        sql.append(", ");
                    }
                }
                sql.append(" WHERE `").append(idColName).append("` = ?");

                try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
                    for (int i = 1; i < colNames.size(); i++) {
                        pstmt.setString(i, textFields.get(i).getText());
                    }
                    pstmt.setObject(colNames.size(), idValue);
                    
                    pstmt.executeUpdate();
                    loadData(); 
                    editDialog.dispose();
                    JOptionPane.showMessageDialog(this, "Запис успішно оновлено!");
                } catch (SQLException sQLEx) {
                    JOptionPane.showMessageDialog(editDialog, "Помилка оновлення: " + sQLEx.getMessage());
                }
            });

            editDialog.pack();
            editDialog.setSize(Math.max(editDialog.getWidth(), 350), editDialog.getHeight());
            editDialog.setLocationRelativeTo(this);
            editDialog.setVisible(true);
        });

        bottomPanel.add(addBtn);
        bottomPanel.add(editBtn);
        bottomPanel.add(deleteBtn);
        bottomPanel.add(refreshBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void initMetadata() {
        try {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rsPk = meta.getPrimaryKeys(conn.getCatalog(), null, tableName);
            while (rsPk.next()) {
                pkNames.add(rsPk.getString("COLUMN_NAME"));
            }

            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery("SELECT * FROM `" + tableName + "` LIMIT 1")) {
                
                ResultSetMetaData rsmd = rs.getMetaData();
                int cols = rsmd.getColumnCount();
                String[] columns = new String[cols];
                
                for (int i = 1; i <= cols; i++) {
                    String cName = rsmd.getColumnName(i);
                    columns[i - 1] = cName;
                    colNames.add(cName);
                }
                
                model.setColumnIdentifiers(columns);
            }

            if (pkNames.isEmpty()) {
                pkNames.addAll(colNames);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        model.setRowCount(0);
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM `" + tableName + "`")) {
            
            int cols = colNames.size();
            while (rs.next()) {
                Object[] row = new Object[cols];
                for (int i = 0; i < cols; i++) {
                    row[i] = rs.getObject(colNames.get(i));
                }
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}