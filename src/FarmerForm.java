/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 * Professional Farmer Registration Form
 * @author saniy
 */
import java.sql.*;
import javax.swing.*;
import java.awt.*;

public class FarmerForm extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FarmerForm.class.getName());
    private JTextField txtFarmerID, txtFarmerName, txtPhone, txtAadhaar, txtVillageID, txtEmail, txtLandArea;
    private JLabel errorLabel;

    public FarmerForm() {
        initComponents();
        setupProfessionalUI();
    }

    private void setupProfessionalUI() {
        setTitle("👨‍🌾 Farmer Registration Form");
        setSize(700, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 250, 245));

        // ===== HEADER PANEL =====
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 700, 80);
        headerPanel.setBackground(new Color(34, 139, 34));
        headerPanel.setLayout(null);
        add(headerPanel);

        JLabel headerTitle = new JLabel("👨‍🌾 New Farmer Registration");
        headerTitle.setFont(new Font("Arial", Font.BOLD, 24));
        headerTitle.setForeground(Color.WHITE);
        headerTitle.setBounds(20, 10, 400, 30);
        headerPanel.add(headerTitle);

        JLabel headerSubtitle = new JLabel("Please fill all required fields marked with *");
        headerSubtitle.setFont(new Font("Arial", Font.ITALIC, 12));
        headerSubtitle.setForeground(new Color(200, 255, 200));
        headerSubtitle.setBounds(20, 45, 400, 25);
        headerPanel.add(headerSubtitle);

        // ===== MAIN FORM PANEL =====
        JPanel formPanel = new JPanel();
        formPanel.setBounds(30, 100, 640, 560);
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        formPanel.setLayout(null);
        add(formPanel);

        int yPos = 20;
        int fieldHeight = 35;
        int fieldSpacing = 50;

        // ===== FARMER ID =====
        JLabel lbl1 = createLabel("Farmer ID *", 20, yPos, "Unique identifier (numeric)");
        formPanel.add(lbl1);
        txtFarmerID = createTextField(280, yPos, 320);
        txtFarmerID.setToolTipText("Enter unique 3-5 digit farmer ID");
        formPanel.add(txtFarmerID);
        yPos += fieldSpacing;

        // ===== FARMER NAME =====
        JLabel lbl2 = createLabel("Full Name *", 20, yPos, "First and last name");
        formPanel.add(lbl2);
        txtFarmerName = createTextField(280, yPos, 320);
        txtFarmerName.setToolTipText("Enter farmer's full name (letters and spaces only)");
        formPanel.add(txtFarmerName);
        yPos += fieldSpacing;

        // ===== VILLAGE =====
        JLabel lbl3 = createLabel("Village/Location *", 20, yPos, "Where the farm is located");
        formPanel.add(lbl3);
        txtVillageID = createTextField(280, yPos, 320);
        txtVillageID.setToolTipText("Enter village or location name");
        formPanel.add(txtVillageID);
        yPos += fieldSpacing;

        // ===== AADHAAR =====
        JLabel lbl4 = createLabel("Aadhaar Number *", 20, yPos, "12-digit unique ID");
        formPanel.add(lbl4);
        txtAadhaar = createTextField(280, yPos, 320);
        txtAadhaar.setToolTipText("Enter 12-digit Aadhaar number");
        formPanel.add(txtAadhaar);
        yPos += fieldSpacing;

        // ===== PHONE =====
        JLabel lbl5 = createLabel("Phone Number *", 20, yPos, "10-digit mobile number");
        formPanel.add(lbl5);
        txtPhone = createTextField(280, yPos, 320);
        txtPhone.setToolTipText("Enter 10-digit phone number");
        formPanel.add(txtPhone);
        yPos += fieldSpacing;

        // ===== EMAIL =====
        JLabel lbl6 = createLabel("Email Address", 20, yPos, "Contact email (optional)");
        formPanel.add(lbl6);
        txtEmail = createTextField(280, yPos, 320);
        txtEmail.setToolTipText("Enter email address (optional)");
        formPanel.add(txtEmail);
        yPos += fieldSpacing;

        // ===== LAND AREA =====
        JLabel lbl7 = createLabel("Land Area (hectares)", 20, yPos, "Farm size in hectares");
        formPanel.add(lbl7);
        txtLandArea = createTextField(280, yPos, 320);
        txtLandArea.setToolTipText("Enter land area in hectares (e.g., 2.5)");
        formPanel.add(txtLandArea);
        yPos += fieldSpacing;

        // ===== ERROR LABEL =====
        errorLabel = new JLabel("");
        errorLabel.setFont(new Font("Arial", Font.BOLD, 12));
        errorLabel.setForeground(new Color(200, 0, 0));
        errorLabel.setBounds(20, yPos, 600, 25);
        formPanel.add(errorLabel);
        yPos += 40;

        // ===== BUTTONS PANEL =====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(30, 670, 640, 50);
        buttonPanel.setBackground(new Color(240, 245, 240));
        buttonPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        buttonPanel.setLayout(null);
        add(buttonPanel);

        JButton btnSave = createButton("✓ Save Farmer", 150, 10, 150, 35);
        btnSave.setBackground(new Color(34, 139, 34));
        btnSave.setForeground(Color.WHITE);
        btnSave.addActionListener(e -> saveFarmer());
        buttonPanel.add(btnSave);

        JButton btnClear = createButton("🗑️ Clear Form", 320, 10, 150, 35);
        btnClear.setBackground(new Color(100, 100, 100));
        btnClear.setForeground(Color.WHITE);
        btnClear.addActionListener(e -> clearForm());
        buttonPanel.add(btnClear);
    }

    private JLabel createLabel(String text, int x, int y, String tooltip) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(new Color(34, 80, 34));
        label.setBounds(x, y, 250, 15);
        label.setToolTipText(tooltip);
        return label;
    }

    private JTextField createTextField(int x, int y, int width) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, width, 30);
        tf.setFont(new Font("Arial", Font.PLAIN, 13));
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 150, 150), 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return tf;
    }

    private JButton createButton(String text, int x, int y, int width, int height) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, width, height);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createRaisedBevelBorder());
        return btn;
    }

    private void saveFarmer() {
        String farmerID = txtFarmerID.getText().trim();
        String name = txtFarmerName.getText().trim();
        String village = txtVillageID.getText().trim();
        String aadhaar = txtAadhaar.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();
        String landArea = txtLandArea.getText().trim();

        // Validate using InputValidator
        if (!InputValidator.isValidFarmerId(farmerID)) {
            errorLabel.setText("❌ " + InputValidator.getErrorMessage("farmerId", farmerID));
            return;
        }

        if (!InputValidator.isValidName(name)) {
            errorLabel.setText("❌ " + InputValidator.getErrorMessage("name", name));
            return;
        }

        if (InputValidator.isEmpty(village)) {
            errorLabel.setText("❌ Village/Location is required!");
            return;
        }

        if (!InputValidator.isValidAadhaar(aadhaar)) {
            errorLabel.setText("❌ " + InputValidator.getErrorMessage("aadhaar", aadhaar));
            return;
        }

        if (!InputValidator.isValidPhone(phone)) {
            errorLabel.setText("❌ " + InputValidator.getErrorMessage("phone", phone));
            return;
        }

        if (!InputValidator.isValidEmail(email)) {
            errorLabel.setText("❌ " + InputValidator.getErrorMessage("email", email));
            return;
        }

        if (!InputValidator.isValidLandArea(landArea)) {
            errorLabel.setText("❌ " + InputValidator.getErrorMessage("landArea", landArea));
            return;
        }

        // All validations passed, save to database
        try {
            Connection con = DBConnection.getConnection();
            if (con == null) {
                errorLabel.setText("❌ Database connection failed!");
                return;
            }

            String sql = "INSERT INTO Farmer (farmer_id, name, village, aadhaar_no, phone, email, land_area_hectares) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(farmerID));
            ps.setString(2, name);
            ps.setString(3, village);
            ps.setString(4, aadhaar);
            ps.setString(5, phone);
            ps.setString(6, InputValidator.isEmpty(email) ? null : email);
            ps.setDouble(7, InputValidator.isEmpty(landArea) ? 0 : Double.parseDouble(landArea));
            ps.executeUpdate();
            
            errorLabel.setForeground(new Color(34, 139, 34));
            errorLabel.setText("✓ Farmer saved successfully! Record ID: " + farmerID);
            
            javax.swing.Timer timer = new javax.swing.Timer(2000, e -> clearForm());
            timer.setRepeats(false);
            timer.start();
            
            con.close();
        } catch (SQLIntegrityConstraintViolationException e) {
            errorLabel.setText("❌ Farmer ID or Aadhaar already exists!");
        } catch (Exception e) {
            errorLabel.setText("❌ Error: " + e.getMessage());
            logger.log(java.util.logging.Level.SEVERE, "Error saving farmer", e);
        }
    }

    private void clearForm() {
        txtFarmerID.setText("");
        txtFarmerName.setText("");
        txtVillageID.setText("");
        txtAadhaar.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtLandArea.setText("");
        errorLabel.setText("");
        txtFarmerID.requestFocus();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new FarmerForm().setVisible(true));
    }
}
