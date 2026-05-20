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
public class LoginScreen extends javax.swing.JFrame {
    JTextField usernameField;
    JPasswordField passwordField;
    JLabel errorLabel;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LoginScreen.class.getName());

    /**
     * Creates new form LoginScreen
     */
    public LoginScreen() {
        initComponents();
        setupUI();
    }
    private void setupUI() {
        setTitle("Green Ledger - Login");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(34, 139, 34));

        // Logo/Title
        JLabel logo = new JLabel("🌱");
        logo.setFont(new Font("Arial", Font.PLAIN, 50));
        logo.setBounds(190, 20, 80, 60);
        add(logo);

        JLabel title = new JLabel("GREEN LEDGER");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBounds(120, 75, 250, 35);
        add(title);

        JLabel subtitle = new JLabel("Subsidy Management System");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitle.setForeground(Color.WHITE);
        subtitle.setBounds(130, 105, 220, 20);
        add(subtitle);

        // White login panel
        JPanel loginPanel = new JPanel();
        loginPanel.setBounds(50, 140, 340, 170);
        loginPanel.setLayout(null);
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        add(loginPanel);

        // Username
        JLabel userLbl = new JLabel("Username:");
        userLbl.setFont(new Font("Arial", Font.BOLD, 13));
        userLbl.setBounds(20, 15, 100, 25);
        loginPanel.add(userLbl);

        usernameField = new JTextField();
        usernameField.setBounds(120, 15, 180, 28);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 13));
        loginPanel.add(usernameField);

        // Password
        JLabel passLbl = new JLabel("Password:");
        passLbl.setFont(new Font("Arial", Font.BOLD, 13));
        passLbl.setBounds(20, 55, 100, 25);
        loginPanel.add(passLbl);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 55, 180, 28);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 13));
        loginPanel.add(passwordField);

        // Error label
        errorLabel = new JLabel("");
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(20, 88, 300, 20);
        loginPanel.add(errorLabel);

        // Login button
        JButton btnLogin = new JButton("LOGIN");
        btnLogin.setBounds(90, 115, 160, 38);
        btnLogin.setBackground(new Color(34, 139, 34));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(e -> login());
        loginPanel.add(btnLogin);

        // Enter key on password field
        passwordField.addActionListener(e -> login());
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Validation
        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("❌ Please enter username and password!");
            return;
        }

        // Show loading message
        errorLabel.setText("Authenticating...");
        errorLabel.setForeground(new Color(0, 100, 200));
        
        // Authenticate using AuthenticationManager
        if (AuthenticationManager.authenticate(username, password)) {
            errorLabel.setForeground(new Color(34, 139, 34));
            errorLabel.setText("✓ Login successful!");
            
            // Delay for user feedback
            javax.swing.Timer timer = new javax.swing.Timer(500, e -> {
                dispose(); // close login screen
                new MainMenu().setVisible(true); // open main menu
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            errorLabel.setForeground(Color.RED);
            errorLabel.setText("❌ Invalid username or password!");
            passwordField.setText("");
            usernameField.requestFocus();
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
        java.awt.EventQueue.invokeLater(() -> new LoginScreen().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
