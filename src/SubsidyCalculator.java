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

public class SubsidyCalculator extends javax.swing.JFrame {

    JTextField farmerField;
    JLabel resultLabel;
    JLabel amountLabel;

    public SubsidyCalculator() {
        initComponents();
        setupUI();
    }

    private void setupUI() {
        setTitle("💰 Subsidy Eligibility Calculator");
        setSize(750, 650);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 250, 245));

        // ===== HEADER PANEL =====
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 750, 90);
        headerPanel.setBackground(new Color(34, 100, 34));
        headerPanel.setLayout(null);
        add(headerPanel);

        JLabel title = new JLabel("💰 Subsidy Eligibility Calculator");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBounds(20, 10, 400, 35);
        headerPanel.add(title);

        JLabel subtitle = new JLabel("Check farmer eligibility and calculate subsidy amount");
        subtitle.setFont(new Font("Arial", Font.ITALIC, 12));
        subtitle.setForeground(new Color(200, 255, 200));
        subtitle.setBounds(20, 50, 400, 25);
        headerPanel.add(subtitle);

        // ===== INPUT PANEL =====
        JPanel inputPanel = new JPanel();
        inputPanel.setBounds(30, 110, 690, 100);
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        inputPanel.setLayout(null);
        add(inputPanel);

        JLabel lbl = new JLabel("🔍 Enter Farmer ID:");
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        lbl.setForeground(new Color(34, 80, 34));
        lbl.setBounds(30, 15, 150, 20);
        inputPanel.add(lbl);

        farmerField = new JTextField();
        farmerField.setBounds(200, 13, 200, 30);
        farmerField.setFont(new Font("Arial", Font.PLAIN, 13));
        farmerField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 150, 150), 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        farmerField.setToolTipText("Enter farmer ID to check eligibility");
        inputPanel.add(farmerField);

        JButton btnCheck = new JButton("✓ Check Eligibility");
        btnCheck.setBounds(420, 13, 140, 30);
        btnCheck.setBackground(new Color(34, 139, 34));
        btnCheck.setForeground(Color.WHITE);
        btnCheck.setFont(new Font("Arial", Font.BOLD, 12));
        btnCheck.setFocusPainted(false);
        btnCheck.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCheck.addActionListener(e -> checkEligibility());
        inputPanel.add(btnCheck);

        JLabel infoLabel = new JLabel("💡 Subsidy based on: Trees planted, no fraud flags, and valid documentation");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        infoLabel.setForeground(new Color(100, 100, 100));
        infoLabel.setBounds(30, 55, 630, 20);
        inputPanel.add(infoLabel);

        // ===== RESULT PANEL =====
        JPanel resultPanel = new JPanel();
        resultPanel.setBounds(30, 220, 690, 350);
        resultPanel.setLayout(null);
        resultPanel.setBackground(Color.WHITE);
        resultPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        add(resultPanel);

        // Status indicator
        resultLabel = new JLabel("Enter Farmer ID and click 'Check Eligibility'");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        resultLabel.setForeground(new Color(100, 100, 100));
        resultLabel.setBounds(30, 20, 630, 25);
        resultPanel.add(resultLabel);

        // Separator
        JSeparator sep = new JSeparator();
        sep.setBounds(30, 50, 630, 2);
        resultPanel.add(sep);

        // Result details
        amountLabel = new JLabel("");
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        amountLabel.setForeground(new Color(34, 80, 34));
        amountLabel.setBounds(30, 65, 630, 250);
        amountLabel.setVerticalAlignment(JLabel.TOP);
        resultPanel.add(amountLabel);

        // ===== SUBSIDY TIERS PANEL =====
        JPanel tiersPanel = new JPanel();
        tiersPanel.setBounds(30, 580, 690, 40);
        tiersPanel.setBackground(new Color(240, 250, 240));
        tiersPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        tiersPanel.setLayout(null);
        add(tiersPanel);

        JLabel tierLabel = new JLabel("📊 Subsidy Tiers: 1-5 trees = ₹5,000 | 6-10 = ₹15,000 | 11+ = ₹30,000");
        tierLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        tierLabel.setForeground(new Color(100, 100, 100));
        tierLabel.setBounds(20, 10, 650, 20);
        tiersPanel.add(tierLabel);
    }

    private void checkEligibility() {
        String farmerIdText = farmerField.getText().trim();

        if (farmerIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Farmer ID!");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();

            // Get farmer name
            String farmerSql = "SELECT name FROM Farmer WHERE farmer_id = ?";
            PreparedStatement fps = con.prepareStatement(farmerSql);
            fps.setInt(1, Integer.parseInt(farmerIdText));
            ResultSet frs = fps.executeQuery();

            if (!frs.next()) {
                JOptionPane.showMessageDialog(this, "Farmer not found!");
                return;
            }
            String farmerName = frs.getString("name");

            // Count trees
            String treeSql = "SELECT COUNT(*) FROM Tree WHERE farmer_id = ?";
            PreparedStatement tps = con.prepareStatement(treeSql);
            tps.setInt(1, Integer.parseInt(farmerIdText));
            ResultSet trs = tps.executeQuery();
            trs.next();
            int treeCount = trs.getInt(1);

            // Calculate subsidy
            // Rules: minimum 2 trees = eligible
            // Rs. 500 per tree
            if (treeCount >= 2) {
                int subsidy = treeCount * 500;
                resultLabel.setText("✅ ELIGIBLE!");
                resultLabel.setForeground(new Color(34, 139, 34));
                amountLabel.setText("<html>Farmer: <b>" + farmerName + "</b><br>"
                        + "Trees Planted: <b>" + treeCount + "</b><br>"
                        + "Subsidy Amount: <b>Rs. " + subsidy + "/-</b></html>");
            } else {
                resultLabel.setText("❌ NOT ELIGIBLE");
                resultLabel.setForeground(Color.RED);
                amountLabel.setText("<html>Farmer: <b>" + farmerName + "</b><br>"
                        + "Trees Planted: <b>" + treeCount + "</b><br>"
                        + "Minimum 2 trees required!</html>");
            }

            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
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
        java.awt.EventQueue.invokeLater(() -> {
        new SubsidyCalculator().setVisible(true);
    });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
