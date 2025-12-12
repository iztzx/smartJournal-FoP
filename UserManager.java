import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    
    // ===============================================
    // 1. ORACLE CONNECTION CONSTANTS
    // ===============================================
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521/XE"; 
    private static final String USER = "SYSTEM";       
    private static final String PASS = "12345678"; // <-- CRUCIAL: REPLACE WITH YOUR ACTUAL SYSTEM PASSWORD

    // ===============================================
    // 2. CONNECTION HELPER METHOD
    // ===============================================
    private static Connection getConnection() throws SQLException {
        try {
            // Load the Oracle JDBC driver class
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Oracle JDBC Driver not found! Ensure ojdbcX.jar is in NetBeans Libraries.");
            throw new SQLException("Driver initialization failed.", e);
        }
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
    
    // ===============================================
    // 3. LOAD USERS (JDBC IMPLEMENTATION)
    // ===============================================
    public static Map<String, User> loadUsers() {
        Map<String, User> userMap = new HashMap<>();
        // SQL query to select all users from the 'users' table
        final String SQL_SELECT = "SELECT email, display_name, password_hash FROM users";

        // Use try-with-resources for automatic closing of connection, statement, and result set
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                String email = rs.getString("email");
                String displayName = rs.getString("display_name");
                String passwordHash = rs.getString("password_hash"); 
                
                // Create User object using data retrieved from the database
                User user = new User(email, displayName, passwordHash);
                userMap.put(email, user);
            }
            System.out.println("✅ Loaded " + userMap.size() + " users from Oracle Database.");

        } catch (SQLException e) {
            System.err.println("❌ Database loading error: " + e.getMessage());
            System.err.println("Ensure Oracle Server is running and the 'users' table exists.");
        }
        return userMap;
    }
    
    // ===============================================
    // 4. SAVE USER (JDBC IMPLEMENTATION)
    // ===============================================
    public static void saveUser(User newUser) {
        // SQL for inserting a new user record. We use prepared statements (?) for security.
        final String SQL_INSERT = "INSERT INTO users (email, display_name, password_hash) VALUES (?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT)) {
            
            // Set parameters (1-indexed) using the User object's getters
            // NOTE: You must ensure newUser.getEmail() exists and is correct.
            pstmt.setString(1, newUser.getEmail()); 
            pstmt.setString(2, newUser.getDisplayName());
            pstmt.setString(3, newUser.getPassword()); // Inserts the HASHED password
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ New user saved to Oracle Database.");
            }

        } catch (SQLException e) {
            // Catches errors like unique constraint violation (duplicate email)
            System.err.println("❌ Database saving error: " + e.getMessage());
        }
    }
    
    // Reminder: You need to fix the getEmailAddress() bug in User.java
    // and ensure you have one other file I/O instance for the basic feature mark.
}

