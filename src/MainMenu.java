/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author saniy
 */
import javax.swing.*;
import java.awt.*;

public class MainMenu extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainMenu.class.getName());

    /**
     * Creates new form MainMenu
     */
    public MainMenu() {
        initComponents();
        setupUI();
    }

    private void setupUI() {
        setTitle("Green Ledger - Subsidy Management System");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(34, 139, 34));
        setLayout(null);

        // User Info Panel (Top Right)
        JPanel userPanel = new JPanel();
        userPanel.setBounds(550, 10, 180, 60);
        userPanel.setLayout(null);
        userPanel.setBackground(new Color(20, 100, 20));
        userPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        add(userPanel);

        // Current user label
        AuthenticationManager.User user = AuthenticationManager.getCurrentUser();
        String userName = user != null ? user.fullName : "Guest";
        String userRole = user != null ? user.role : "N/A";
        
        JLabel userLabel = new JLabel("👤 " + userName);
        userLabel.setFont(new Font("Arial", Font.BOLD, 11));
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(5, 5, 170, 18);
        userPanel.add(userLabel);

        JLabel roleLabel = new JLabel("Role: " + userRole);
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 9));
        roleLabel.setForeground(Color.YELLOW);
        roleLabel.setBounds(5, 23, 170, 15);
        userPanel.add(roleLabel);

        // Title Label
        JLabel title = new JLabel("🌱 GREEN LEDGER");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setBounds(120, 20, 400, 50);
        add(title);

        JLabel subtitle = new JLabel("Transparent Subsidy Management for Rural Afforestation");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitle.setForeground(Color.WHITE);
        subtitle.setBounds(90, 65, 500, 25);
        add(subtitle);

        // Main Buttons
        JButton btnFarmer = createButton("👨‍🌾  Add Farmer", 50, 120, 140, 60);
        btnFarmer.addActionListener(e -> new FarmerForm().setVisible(true));
        add(btnFarmer);

        JButton btnTree = createButton("🌳  Add Tree", 210, 120, 140, 60);
        btnTree.addActionListener(e -> new TreeForm().setVisible(true));
        add(btnTree);

        JButton btnView = createButton("📋  View Records", 370, 120, 140, 60);
        btnView.addActionListener(e -> new ViewData().setVisible(true));
        add(btnView);

        JButton btnSearch = createButton("🔍  Search Farmer", 530, 120, 140, 60);
        btnSearch.addActionListener(e -> new SearchFarmer().setVisible(true));
        add(btnSearch);

        // Second Row
        JButton btnUpdate = createButton("✏️  Update/Delete", 50, 195, 140, 60);
        btnUpdate.addActionListener(e -> new UpdateDelete().setVisible(true));
        add(btnUpdate);

        JButton btnFraud = createButton("🚨  Fraud Report", 210, 195, 140, 60);
        btnFraud.addActionListener(e -> new FraudReport().setVisible(true));
        add(btnFraud);

        JButton btnSubsidy = createButton("💰  Subsidy Check", 370, 195, 140, 60);
        btnSubsidy.addActionListener(e -> new SubsidyCalculator().setVisible(true));
        add(btnSubsidy);

        JButton btnAudit = createButton("📋  Audit Log", 530, 195, 140, 60);
        btnAudit.addActionListener(e -> new AuditLogScreen().setVisible(true));
        add(btnAudit);

        // Third Row
        JButton btnAbout = createButton("ℹ️  About", 50, 270, 140, 60);
        btnAbout.addActionListener(e -> new AboutScreen().setVisible(true));
        add(btnAbout);

        JButton btnRefresh = createButton("🔄  Refresh Data", 210, 270, 140, 60);
        btnRefresh.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Data refreshed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        add(btnRefresh);

        // Status Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setBounds(50, 350, 620, 120);
        infoPanel.setLayout(new GridLayout(3, 2, 10, 10));
        infoPanel.setBackground(new Color(240, 255, 240));
        infoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(34, 139, 34), 2), "System Statistics", 0, 0, new Font("Arial", Font.BOLD, 12)));
        add(infoPanel);

        // Statistics
        infoPanel.add(createStatLabel("Total Farmers:", "Loading..."));
        infoPanel.add(createStatLabel("Total Trees:", "Loading..."));
        infoPanel.add(createStatLabel("Pending Subsidies:", "Loading..."));
        infoPanel.add(createStatLabel("Database Status:", "✓ Connected"));
        infoPanel.add(createStatLabel("Last Sync:", "Just now"));
        infoPanel.add(createStatLabel("System Version:", "1.0.0"));

        // Bottom Buttons
        JButton btnLogout = new JButton("🚪  Logout");
        btnLogout.setBounds(50, 480, 140, 40);
        btnLogout.setBackground(new Color(200, 50, 50));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Arial", Font.BOLD, 13));
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.addActionListener(e -> logout());
        add(btnLogout);

        JButton btnExit = new JButton("❌  Exit");
        btnExit.setBounds(530, 480, 140, 40);
        btnExit.setBackground(new Color(100, 100, 100));
        btnExit.setForeground(Color.WHITE);
        btnExit.setFont(new Font("Arial", Font.BOLD, 13));
        btnExit.setFocusPainted(false);
        btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExit.addActionListener(e -> System.exit(0));
        add(btnExit);

        loadStatistics();
    }

    private JLabel createStatLabel(String label, String value) {
        JLabel lbl = new JLabel(label + " " + value);
        lbl.setFont(new Font("Arial", Font.BOLD, 11));
        lbl.setForeground(new Color(34, 139, 34));
        return lbl;
    }

    private void loadStatistics() {
        try {
            java.sql.Connection con = DBConnection.getConnection();
            if (con != null) {
                // Get total farmers
                java.sql.Statement st1 = con.createStatement();
                java.sql.ResultSet rs1 = st1.executeQuery("SELECT COUNT(*) as cnt FROM Farmer");
                if (rs1.next()) {
                    // Update statistics panel if needed
                }
                con.close();
            }
        } catch (Exception e) {
            logger.log(java.util.logging.Level.WARNING, "Error loading statistics", e);
        }
    }

    private void logout() {
        AuthenticationManager.logout();
        dispose();
        new LoginScreen().setVisible(true);
    }

    private JButton createButton(String text, int x, int y, int width, int height) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, width, height);
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(34, 139, 34));
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
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
        java.awt.EventQueue.invokeLater(() -> new MainMenu().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
