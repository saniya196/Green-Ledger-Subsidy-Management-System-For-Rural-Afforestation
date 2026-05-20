import java.sql.*;

/**
 * AuthenticationManager - Handles user authentication and session management
 */
public class AuthenticationManager {
    
    private static AuthenticationManager instance;
    private static User currentUser;
    
    public static class User {
        public int userId;
        public String username;
        public String fullName;
        public String role;
        
        public User(int userId, String username, String fullName, String role) {
            this.userId = userId;
            this.username = username;
            this.fullName = fullName;
            this.role = role;
        }
        
        @Override
        public String toString() {
            return fullName + " (" + role + ")";
        }
    }
    
    /**
     * Get singleton instance
     */
    public static AuthenticationManager getInstance() {
        if (instance == null) {
            instance = new AuthenticationManager();
        }
        return instance;
    }
    
    /**
     * Authenticate user with credentials
     */
    public static boolean authenticate(String username, String password) {
        if (InputValidator.isEmpty(username) || InputValidator.isEmpty(password)) {
            return false;
        }
        
        try {
            Connection con = DBConnection.getConnection();
            if (con == null) {
                return false;
            }
            
            String sql = "SELECT user_id, username, full_name, role FROM Users WHERE username = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username.trim());
            ps.setString(2, password); // In production, use hashed passwords!
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                currentUser = new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("full_name"),
                    rs.getString("role")
                );
                con.close();
                return true;
            }
            
            con.close();
            return false;
            
        } catch (SQLException e) {
            System.out.println("Authentication error: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get current logged-in user
     */
    public static User getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Check if user is authenticated
     */
    public static boolean isAuthenticated() {
        return currentUser != null;
    }
    
    /**
     * Logout current user
     */
    public static void logout() {
        currentUser = null;
    }
    
    /**
     * Check if current user has specific role
     */
    public static boolean hasRole(String role) {
        return currentUser != null && currentUser.role.equals(role);
    }
    
    /**
     * Check if current user is admin
     */
    public static boolean isAdmin() {
        return hasRole("ADMIN");
    }
    
    /**
     * Check if current user is officer
     */
    public static boolean isOfficer() {
        return hasRole("OFFICER") || isAdmin();
    }
}
