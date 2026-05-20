/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author saniy
 */
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
public class SearchFarmer extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SearchFarmer.class.getName());

    /**
     * Creates new form SearchFarmer
     */
    public SearchFarmer() {
        initComponents();
        setupUI();
    }
    JTextField searchField;
    JComboBox<String> filterCombo;
    JTable table;
    DefaultTableModel model;

    private void setupUI() {
        setTitle("🔍 Advanced Search");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 250, 245));

        // ===== HEADER PANEL =====
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 1000, 90);
        headerPanel.setBackground(new Color(34, 100, 34));
        headerPanel.setLayout(null);
        add(headerPanel);

        JLabel headerTitle = new JLabel("🔍 Advanced Search & Records");
        headerTitle.setFont(new Font("Arial", Font.BOLD, 26));
        headerTitle.setForeground(Color.WHITE);
        headerTitle.setBounds(20, 10, 500, 35);
        headerPanel.add(headerTitle);

        JLabel headerSubtitle = new JLabel("Search farmers, trees, and records with multiple filter options");
        headerSubtitle.setFont(new Font("Arial", Font.ITALIC, 12));
        headerSubtitle.setForeground(new Color(200, 255, 200));
        headerSubtitle.setBounds(20, 50, 500, 25);
        headerPanel.add(headerSubtitle);

        // ===== SEARCH PANEL =====
        JPanel searchPanel = new JPanel();
        searchPanel.setBounds(20, 100, 960, 80);
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        searchPanel.setLayout(null);
        add(searchPanel);

        JLabel filterLabel = new JLabel("📋 Search By:");
        filterLabel.setFont(new Font("Arial", Font.BOLD, 12));
        filterLabel.setForeground(new Color(34, 80, 34));
        filterLabel.setBounds(20, 15, 100, 20);
        searchPanel.add(filterLabel);

        filterCombo = new JComboBox<>(new String[]{"Farmer ID", "Farmer Name", "Village", "Tree Type"});
        filterCombo.setBounds(130, 13, 140, 28);
        filterCombo.setFont(new Font("Arial", Font.PLAIN, 12));
        filterCombo.setBackground(Color.WHITE);
        filterCombo.addActionListener(e -> updateSearchPlaceholder());
        searchPanel.add(filterCombo);

        JLabel searchLabel = new JLabel("Search Value:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 12));
        searchLabel.setForeground(new Color(34, 80, 34));
        searchLabel.setBounds(290, 15, 100, 20);
        searchPanel.add(searchLabel);

        searchField = new JTextField();
        searchField.setBounds(400, 13, 200, 28);
        searchField.setFont(new Font("Arial", Font.PLAIN, 12));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 150, 150), 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        searchField.setToolTipText("Enter search term");
        searchPanel.add(searchField);

        JButton btnSearch = new JButton("🔎 Search");
        btnSearch.setBounds(620, 13, 110, 28);
        btnSearch.setBackground(new Color(34, 139, 34));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(new Font("Arial", Font.BOLD, 12));
        btnSearch.setFocusPainted(false);
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.addActionListener(e -> searchFarmer());
        searchPanel.add(btnSearch);

        JButton btnClear = new JButton("🔄 Reset");
        btnClear.setBounds(745, 13, 110, 28);
        btnClear.setBackground(new Color(100, 100, 100));
        btnClear.setForeground(Color.WHITE);
        btnClear.setFont(new Font("Arial", Font.BOLD, 12));
        btnClear.setFocusPainted(false);
        btnClear.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClear.addActionListener(e -> {
            searchField.setText("");
            model.setRowCount(0);
            searchField.requestFocus();
        });
        searchPanel.add(btnClear);

        JLabel tipLabel = new JLabel("<html>💡 <b>Tips:</b> Use * for partial matches in text searches</html>");
        tipLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        tipLabel.setForeground(new Color(100, 100, 100));
        tipLabel.setBounds(20, 50, 920, 25);
        searchPanel.add(tipLabel);

        // ===== RESULTS PANEL =====
        JPanel resultPanel = new JPanel();
        resultPanel.setBounds(20, 190, 960, 350);
        resultPanel.setBackground(Color.WHITE);
        resultPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        resultPanel.setLayout(new BorderLayout());
        add(resultPanel);

        String[] columns = {"Tree ID", "Farmer ID", "Farmer Name", "Village", "Tree Type", "Plant Date", "Status"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        table = new JTable(model);
        table.setRowHeight(25);
        table.getTableHeader().setBackground(new Color(34, 139, 34));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setFont(new Font("Arial", Font.PLAIN, 11));
        table.setSelectionBackground(new Color(184, 207, 229));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scroll = new JScrollPane(table);
        resultPanel.add(scroll, BorderLayout.CENTER);

        // ===== STATUS PANEL =====
        JPanel statusPanel = new JPanel();
        statusPanel.setBounds(20, 550, 960, 50);
        statusPanel.setLayout(null);
        statusPanel.setBackground(new Color(200, 240, 200));
        statusPanel.setBorder(BorderFactory.createLineBorder(new Color(34, 139, 34), 2));
        add(statusPanel);

        JLabel statusLabel = new JLabel("📊 Total Records: 0 | Ready to search");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
        statusLabel.setBounds(10, 15, 200, 20);
        statusPanel.add(statusLabel);

        // Make Enter key work for search
        searchField.addActionListener(e -> searchFarmer());
    }

    private void searchFarmer() {
        String searchValue = searchField.getText().trim();
        String searchType = (String) filterCombo.getSelectedItem();

        if (searchValue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Please enter a search value!");
            return;
        }

        // Clear previous results
        model.setRowCount(0);

        try {
            Connection con = DBConnection.getConnection();
            if (con == null) {
                JOptionPane.showMessageDialog(this, "❌ Database connection failed!");
                return;
            }

            String sql;
            PreparedStatement ps;
            
            // Build appropriate SQL query based on search type
            switch (searchType) {
                case "Farmer ID":
                    sql = "SELECT t.tree_id, f.farmer_id, f.name, f.village, t.tree_type, t.plant_date, t.status " +
                          "FROM Tree t JOIN Farmer f ON t.farmer_id = f.farmer_id WHERE f.farmer_id = ? ORDER BY t.tree_id";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, Integer.parseInt(searchValue));
                    break;

                case "Farmer Name":
                    sql = "SELECT t.tree_id, f.farmer_id, f.name, f.village, t.tree_type, t.plant_date, t.status " +
                          "FROM Tree t JOIN Farmer f ON t.farmer_id = f.farmer_id WHERE f.name LIKE ? ORDER BY f.name";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, "%" + searchValue + "%");
                    break;

                case "Village":
                    sql = "SELECT t.tree_id, f.farmer_id, f.name, f.village, t.tree_type, t.plant_date, t.status " +
                          "FROM Tree t JOIN Farmer f ON t.farmer_id = f.farmer_id WHERE f.village LIKE ? ORDER BY f.name";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, "%" + searchValue + "%");
                    break;

                case "Tree Type":
                    sql = "SELECT t.tree_id, f.farmer_id, f.name, f.village, t.tree_type, t.plant_date, t.status " +
                          "FROM Tree t JOIN Farmer f ON t.farmer_id = f.farmer_id WHERE t.tree_type LIKE ? ORDER BY f.name";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, "%" + searchValue + "%");
                    break;

                default:
                    return;
            }

            ResultSet rs = ps.executeQuery();

            int count = 0;
            while (rs.next()) {
                count++;
                Object[] row = {
                    rs.getInt("tree_id"),
                    rs.getInt("farmer_id"),
                    rs.getString("name"),
                    rs.getString("village"),
                    rs.getString("tree_type"),
                    rs.getDate("plant_date"),
                    rs.getString("status")
                };
                model.addRow(row);
            }

            if (count == 0) {
                JOptionPane.showMessageDialog(this, "❌ No records found for: " + searchValue, "Search Result", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "✓ Found " + count + " record(s)!", "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }

            con.close();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "❌ Invalid Farmer ID! Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            logger.log(java.util.logging.Level.SEVERE, "Error during search", e);
        }
    }

    private void updateSearchPlaceholder() {
        String selected = (String) filterCombo.getSelectedItem();
        switch (selected) {
            case "Farmer ID":
                searchField.setToolTipText("Enter farmer ID (numeric)");
                break;
            case "Farmer Name":
                searchField.setToolTipText("Enter farmer name (partial match)");
                break;
            case "Village":
                searchField.setToolTipText("Enter village name");
                break;
            case "Tree Type":
                searchField.setToolTipText("Enter tree species");
                break;
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new SearchFarmer().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
