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
public class FraudReport extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FraudReport.class.getName());

    /**
     * Creates new form FraudReport
     */
    public FraudReport() {
        initComponents();
        setupUI();
    }
    private void setupUI() {
        setTitle("🚨 Fraud Detection Report");
        setSize(1100, 750);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 250, 245));

        // ===== HEADER PANEL =====
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 1100, 90);
        headerPanel.setBackground(new Color(200, 0, 0));
        headerPanel.setLayout(null);
        add(headerPanel);

        JLabel title = new JLabel("🚨 Fraud Detection Report - Database Triggers");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        title.setBounds(20, 10, 600, 35);
        headerPanel.add(title);

        JLabel subtitle = new JLabel("Real-time fraud detection with automated database triggers");
        subtitle.setFont(new Font("Arial", Font.ITALIC, 12));
        subtitle.setForeground(new Color(255, 200, 200));
        subtitle.setBounds(20, 50, 500, 25);
        headerPanel.add(subtitle);

        // ===== STATISTICS PANEL =====
        JPanel statsPanel = new JPanel();
        statsPanel.setBounds(20, 100, 1060, 90);
        statsPanel.setLayout(new GridLayout(1, 4, 10, 0));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        add(statsPanel);

        // Load stats from DB
        int[] stats = loadStats();

        statsPanel.add(createStatBox("👨‍🌾 Total Farmers", String.valueOf(stats[0]), new Color(52, 152, 219)));
        statsPanel.add(createStatBox("🌳 Total Trees", String.valueOf(stats[1]), new Color(39, 174, 96)));
        statsPanel.add(createStatBox("⚠️ Fraud Records", String.valueOf(stats[2]), new Color(231, 76, 60)));
        statsPanel.add(createStatBox("📊 Flagged Items", String.valueOf(stats[3]), new Color(241, 196, 15)));

        // ===== FRAUD DETECTION TYPES PANEL =====
        JPanel fraudTypesPanel = new JPanel();
        fraudTypesPanel.setBounds(20, 200, 1060, 100);
        fraudTypesPanel.setBackground(new Color(255, 245, 245));
        fraudTypesPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 0, 0), 2));
        fraudTypesPanel.setLayout(null);
        add(fraudTypesPanel);

        JLabel fraudTypesLabel = new JLabel("🔍 Active Fraud Detection Triggers:");
        fraudTypesLabel.setFont(new Font("Arial", Font.BOLD, 13));
        fraudTypesLabel.setForeground(new Color(200, 0, 0));
        fraudTypesLabel.setBounds(10, 5, 250, 20);
        fraudTypesPanel.add(fraudTypesLabel);

        JLabel type1 = new JLabel("✓ Duplicate Trees - Farmer plants multiple trees on same date");
        type1.setFont(new Font("Arial", Font.PLAIN, 11));
        type1.setBounds(20, 25, 500, 18);
        fraudTypesPanel.add(type1);

        JLabel type2 = new JLabel("✓ Excessive Planting - Farmer plants 8+ trees within 7 days");
        type2.setFont(new Font("Arial", Font.PLAIN, 11));
        type2.setBounds(20, 45, 500, 18);
        fraudTypesPanel.add(type2);

        JLabel type3 = new JLabel("✓ Invalid Coordinates - Trees with GPS coordinates outside valid range");
        type3.setFont(new Font("Arial", Font.PLAIN, 11));
        type3.setBounds(20, 65, 550, 18);
        fraudTypesPanel.add(type3);

        JLabel refreshLabel = new JLabel("⏰ Last updated: " + new java.util.Date());
        refreshLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        refreshLabel.setForeground(new Color(100, 100, 100));
        refreshLabel.setBounds(700, 35, 350, 18);
        fraudTypesPanel.add(refreshLabel);

        // ===== FRAUD TABLE PANEL =====
        JPanel tablePanel = new JPanel();
        tablePanel.setBounds(20, 310, 1060, 360);
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        tablePanel.setLayout(new BorderLayout());
        add(tablePanel);

        // Fraud table
        String[] columns = {"Fraud ID", "Farmer ID", "Tree ID", "Type", "Description", "Severity", "Status", "Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Read-only
            }
        };

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT fraud_id, farmer_id, tree_id, fraud_type, description, severity, status, flagged_at " +
                        "FROM FraudDetection ORDER BY flagged_at DESC LIMIT 200";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            int fraudCount = 0;
            while (rs.next()) {
                fraudCount++;
                Object[] row = {
                    rs.getInt("fraud_id"),
                    rs.getInt("farmer_id"),
                    rs.getObject("tree_id") != null ? rs.getInt("tree_id") : "-",
                    rs.getString("fraud_type"),
                    rs.getString("description"),
                    rs.getString("severity"),
                    rs.getString("status"),
                    rs.getTimestamp("flagged_at")
                };
                model.addRow(row);
            }

            if (model.getRowCount() == 0) {
                Object[] row = {"-", "-", "-", "NO_FRAUD", "No fraud detected - all records are valid", "LOW", "CLEAN", new java.util.Date()};
                model.addRow(row);
            }

            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + e.getMessage());
            logger.log(java.util.logging.Level.SEVERE, "Error loading fraud data", e);
        }

        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.getTableHeader().setBackground(new Color(200, 0, 0));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        table.setFont(new Font("Arial", Font.PLAIN, 10));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scroll = new JScrollPane(table);
        tablePanel.add(scroll, BorderLayout.CENTER);

        // ===== ACTION PANEL =====
        JPanel actionPanel = new JPanel();
        actionPanel.setBounds(20, 680, 1060, 40);
        actionPanel.setBackground(new Color(240, 245, 240));
        actionPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        actionPanel.setLayout(null);
        add(actionPanel);

        JButton btnRefresh = new JButton("🔄 Refresh");
        btnRefresh.setBounds(20, 5, 100, 30);
        btnRefresh.setBackground(new Color(34, 139, 34));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFont(new Font("Arial", Font.BOLD, 11));
        btnRefresh.setFocusPainted(false);
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(e -> {
            dispose();
            new FraudReport().setVisible(true);
        });
        actionPanel.add(btnRefresh);

        JButton btnExport = new JButton("💾 Export CSV");
        btnExport.setBounds(130, 5, 100, 30);
        btnExport.setBackground(new Color(0, 100, 200));
        btnExport.setForeground(Color.WHITE);
        btnExport.setFont(new Font("Arial", Font.BOLD, 11));
        btnExport.setFocusPainted(false);
        btnExport.setCursor(new Cursor(Cursor.HAND_CURSOR));
        actionPanel.add(btnExport);

        JButton btnClose = new JButton("❌ Close");
        btnClose.setBounds(950, 5, 90, 30);
        btnClose.setBackground(new Color(100, 100, 100));
        btnClose.setForeground(Color.WHITE);
        btnClose.setFont(new Font("Arial", Font.BOLD, 11));
        btnClose.setFocusPainted(false);
        btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClose.addActionListener(e -> dispose());
        actionPanel.add(btnClose);

        JLabel statusLabel = new JLabel("Status: Ready | Showing latest 200 fraud detection records");
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        statusLabel.setForeground(new Color(100, 100, 100));
        statusLabel.setBounds(240, 10, 700, 20);
        actionPanel.add(statusLabel);
    }

    private int[] loadStats() {
        int[] stats = {0, 0, 0, 0};
        try {
            Connection con = DBConnection.getConnection();

            ResultSet r1 = con.createStatement().executeQuery("SELECT COUNT(*) FROM Farmer");
            r1.next(); stats[0] = r1.getInt(1);

            ResultSet r2 = con.createStatement().executeQuery("SELECT COUNT(*) FROM Tree");
            r2.next(); stats[1] = r2.getInt(1);

            ResultSet r3 = con.createStatement().executeQuery("SELECT COUNT(*) FROM FraudDetection WHERE status = 'FLAGGED'");
            r3.next(); stats[2] = r3.getInt(1);

            ResultSet r4 = con.createStatement().executeQuery("SELECT COUNT(*) FROM FraudDetection");
            r4.next(); stats[3] = r4.getInt(1);

            con.close();
        } catch (Exception e) {
            System.out.println("Stats error: " + e.getMessage());
        }
        return stats;
    }

    private JPanel createStatBox(String label, String value, Color color) {
        JPanel box = new JPanel();
        box.setLayout(null);
        box.setBackground(color);
        box.setBorder(BorderFactory.createEmptyBorder());

        JLabel valLabel = new JLabel(value);
        valLabel.setFont(new Font("Arial", Font.BOLD, 28));
        valLabel.setForeground(Color.WHITE);
        valLabel.setBounds(0, 15, 180, 35);
        valLabel.setHorizontalAlignment(JLabel.CENTER);
        box.add(valLabel);

        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        lblLabel.setForeground(Color.WHITE);
        lblLabel.setBounds(0, 50, 180, 25);
        lblLabel.setHorizontalAlignment(JLabel.CENTER);
        box.add(lblLabel);

        return box;
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
        java.awt.EventQueue.invokeLater(() -> {
            new FraudReport().setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
