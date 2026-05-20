import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * DatabaseConnection Manager
 * Handles all database connectivity for GreenLedger application
 */
public class DBConnection {
    
    private static final Logger logger = Logger.getLogger(DBConnection.class.getName());
    
    // Database Configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/greenledger";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "admin123";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    // Connection timeout
    private static final int TIMEOUT = 5;
    
    static {
        try {
            Class.forName(DB_DRIVER);
            logger.log(Level.INFO, "MySQL JDBC Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Failed to load MySQL JDBC Driver", e);
        }
    }
    
    /**
     * Get a database connection
     * @return Connection object or null if connection fails
     */
    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            logger.log(Level.INFO, "Connected to MySQL successfully!");
            return con;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database connection failed: " + e.getMessage(), e);
            showErrorMessage("Database Connection Error", 
                "Failed to connect to database.\nPlease check:\n" +
                "1. MySQL Server is running\n" +
                "2. Database 'greenledger' exists\n" +
                "3. Credentials are correct");
            return null;
        }
    }
    
    /**
     * Test database connection
     * @return true if connection is successful
     */
    public static boolean testConnection() {
        try {
            Connection con = getConnection();
            if (con != null) {
                con.close();
                return true;
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Connection test failed", e);
        }
        return false;
    }
    
    /**
     * Close a database connection safely
     * @param con Connection to close
     */
    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
                logger.log(Level.INFO, "Connection closed successfully");
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Error closing connection", e);
            }
        }
    }
    
    /**
     * Display error message to user
     */
    private static void showErrorMessage(String title, String message) {
        javax.swing.JOptionPane.showMessageDialog(null, message, title, 
            javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}