/**
 * Professional Tree Registration Form
 * @author saniy
 */
import java.sql.*;
import javax.swing.*;
import java.awt.*;

public class TreeFormProfessional extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TreeFormProfessional.class.getName());
    private JTextField txtTreeID, txtFarmerID, txtTreeType, txtPlantDate, txtLatitude, txtLongitude, txtStatus;
    private JLabel errorLabel;
    private JComboBox<String> cbTreeType;

    public TreeFormProfessional() {
        initComponents();
        setupProfessionalUI();
    }

    private void setupProfessionalUI() {
        setTitle("🌳 Tree Registration Form");
        setSize(750, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 250, 245));

        // ===== HEADER PANEL =====
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 750, 90);
        headerPanel.setBackground(new Color(34, 100, 34));
        headerPanel.setLayout(null);
        add(headerPanel);

        JLabel headerTitle = new JLabel("🌳 Register New Tree");
        headerTitle.setFont(new Font("Arial", Font.BOLD, 26));
        headerTitle.setForeground(Color.WHITE);
        headerTitle.setBounds(20, 10, 400, 35);
        headerPanel.add(headerTitle);

        JLabel headerSubtitle = new JLabel("Record tree planting with GPS coordinates for subsidy tracking");
        headerSubtitle.setFont(new Font("Arial", Font.ITALIC, 12));
        headerSubtitle.setForeground(new Color(200, 255, 200));
        headerSubtitle.setBounds(20, 50, 500, 25);
        headerPanel.add(headerSubtitle);

        // ===== MAIN FORM PANEL =====
        JPanel formPanel = new JPanel();
        formPanel.setBounds(30, 110, 690, 630);
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        formPanel.setLayout(null);
        add(formPanel);

        int yPos = 20;
        int fieldHeight = 35;
        int fieldSpacing = 55;

        // ===== TREE ID =====
        JLabel lbl1 = createLabel("Tree ID *", 20, yPos, "Unique tree identifier");
        formPanel.add(lbl1);
        txtTreeID = createTextField(280, yPos, 350);
        txtTreeID.setToolTipText("Enter unique tree ID (numeric)");
        formPanel.add(txtTreeID);
        yPos += fieldSpacing;

        // ===== FARMER ID =====
        JLabel lbl2 = createLabel("Farmer ID *", 20, yPos, "Which farmer planted this");
        formPanel.add(lbl2);
        txtFarmerID = createTextField(280, yPos, 350);
        txtFarmerID.setToolTipText("Enter farmer ID (must exist in database)");
        formPanel.add(txtFarmerID);
        yPos += fieldSpacing;

        // ===== TREE TYPE =====
        JLabel lbl3 = createLabel("Tree Type *", 20, yPos, "Species of tree");
        formPanel.add(lbl3);
        cbTreeType = createComboBox(280, yPos, 350);
        cbTreeType.setToolTipText("Select tree species");
        formPanel.add(cbTreeType);
        yPos += fieldSpacing;

        // ===== PLANT DATE =====
        JLabel lbl4 = createLabel("Plant Date *", 20, yPos, "When tree was planted (YYYY-MM-DD)");
        formPanel.add(lbl4);
        txtPlantDate = createTextField(280, yPos, 350);
        txtPlantDate.setToolTipText("Enter date in YYYY-MM-DD format");
        formPanel.add(txtPlantDate);
        yPos += fieldSpacing;

        // ===== LATITUDE =====
        JLabel lbl5 = createLabel("Latitude *", 20, yPos, "GPS latitude (-90 to 90)");
        formPanel.add(lbl5);
        txtLatitude = createTextField(280, yPos, 350);
        txtLatitude.setToolTipText("Enter latitude between -90 and 90");
        formPanel.add(txtLatitude);
        yPos += fieldSpacing;

        // ===== LONGITUDE =====
        JLabel lbl6 = createLabel("Longitude *", 20, yPos, "GPS longitude (-180 to 180)");
        formPanel.add(lbl6);
        txtLongitude = createTextField(280, yPos, 350);
        txtLongitude.setToolTipText("Enter longitude between -180 and 180");
        formPanel.add(txtLongitude);
        yPos += fieldSpacing;

        // ===== STATUS =====
        JLabel lbl7 = createLabel("Status", 20, yPos, "Current health status");
        formPanel.add(lbl7);
        JComboBox<String> cbStatus = new JComboBox<>(new String[]{"HEALTHY", "DISEASED", "DEAD"});
        cbStatus.setBounds(280, yPos, 350, 30);
        cbStatus.setFont(new Font("Arial", Font.PLAIN, 13));
        cbStatus.setBackground(Color.WHITE);
        formPanel.add(cbStatus);
        yPos += fieldSpacing;

        // ===== ERROR LABEL =====
        errorLabel = new JLabel("");
        errorLabel.setFont(new Font("Arial", Font.BOLD, 12));
        errorLabel.setForeground(new Color(200, 0, 0));
        errorLabel.setBounds(20, yPos, 650, 30);
        formPanel.add(errorLabel);
        yPos += 40;

        // ===== HELP TEXT =====
        JLabel helpText = new JLabel("<html><b>📍 GPS Tips:</b> Use decimal format. India range: Lat 8-37, Long 68-97</html>");
        helpText.setFont(new Font("Arial", Font.ITALIC, 10));
        helpText.setForeground(new Color(100, 100, 100));
        helpText.setBounds(20, yPos, 650, 40);
        formPanel.add(helpText);

        // ===== BUTTONS PANEL =====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(30, 750, 690, 50);
        buttonPanel.setBackground(new Color(240, 245, 240));
        buttonPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        buttonPanel.setLayout(null);
        add(buttonPanel);

        JButton btnSave = createButton("✓ Plant Tree", 160, 10, 150, 35);
        btnSave.setBackground(new Color(34, 139, 34));
        btnSave.setForeground(Color.WHITE);
        btnSave.addActionListener(e -> saveTree(cbStatus));
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

    private JComboBox<String> createComboBox(int x, int y, int width) {
        JComboBox<String> cb = new JComboBox<>(new String[]{
            "Neem", "Mango", "Coconut", "Sugarcane", "Rice", "Wheat", 
            "Maize", "Cotton", "Jute", "Tea", "Coffee", "Cardamom"
        });
        cb.setBounds(x, y, width, 30);
        cb.setFont(new Font("Arial", Font.PLAIN, 13));
        cb.setBackground(Color.WHITE);
        return cb;
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

    private void saveTree(JComboBox<String> cbStatus) {
        String treeID = txtTreeID.getText().trim();
        String farmerID = txtFarmerID.getText().trim();
        String treeType = (String) cbTreeType.getSelectedItem();
        String plantDate = txtPlantDate.getText().trim();
        String latitude = txtLatitude.getText().trim();
        String longitude = txtLongitude.getText().trim();
        String status = (String) cbStatus.getSelectedItem();

        // Validation
        if (!InputValidator.isValidTreeType(treeID)) {
            errorLabel.setText("❌ Tree ID must be numeric");
            return;
        }

        if (!InputValidator.isValidFarmerId(farmerID)) {
            errorLabel.setText("❌ Farmer ID must be numeric");
            return;
        }

        if (!InputValidator.isValidLatitude(latitude)) {
            errorLabel.setText("❌ Latitude must be between -90 and 90");
            return;
        }

        if (!InputValidator.isValidLongitude(longitude)) {
            errorLabel.setText("❌ Longitude must be between -180 and 180");
            return;
        }

        // Save to database
        try {
            Connection con = DBConnection.getConnection();
            if (con == null) {
                errorLabel.setText("❌ Database connection failed!");
                return;
            }

            String sql = "INSERT INTO Tree (tree_id, farmer_id, tree_type, plant_date, latitude, longitude, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(treeID));
            ps.setInt(2, Integer.parseInt(farmerID));
            ps.setString(3, treeType);
            ps.setString(4, plantDate);
            ps.setDouble(5, Double.parseDouble(latitude));
            ps.setDouble(6, Double.parseDouble(longitude));
            ps.setString(7, status);
            ps.executeUpdate();

            errorLabel.setForeground(new Color(34, 139, 34));
            errorLabel.setText("✓ Tree registered successfully! ID: " + treeID);

            javax.swing.Timer timer = new javax.swing.Timer(2000, e -> clearForm());
            timer.setRepeats(false);
            timer.start();

            con.close();
        } catch (Exception e) {
            errorLabel.setText("❌ Error: " + e.getMessage());
            logger.log(java.util.logging.Level.SEVERE, "Error saving tree", e);
        }
    }

    private void clearForm() {
        txtTreeID.setText("");
        txtFarmerID.setText("");
        cbTreeType.setSelectedIndex(0);
        txtPlantDate.setText("");
        txtLatitude.setText("");
        txtLongitude.setText("");
        errorLabel.setText("");
        txtTreeID.requestFocus();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new TreeFormProfessional().setVisible(true));
    }
}
