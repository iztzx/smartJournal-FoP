import java.util.Map;
import java.util.Scanner;
public class AuthManager {
    // Stores all User objects loaded from UserData.txt, using Email as the key for O(1) lookup.
    private Map<String, User> users; 
    private final Scanner scanner;

    /**
     * Constructor: Initializes the AuthManager by loading existing user data
     * from the file and preparing the scanner for input.
     */
    public AuthManager() {
        // Loads all existing users into memory upon startup
        this.users = UserManager.loadUsers(); 
        this.scanner = new Scanner(System.in);
    }

    // --- 1. Sign Up / Registration Logic ---
    /**
     * Prompts the user for details, validates the email, hashes the password, 
     * and saves the new user account.
     */
    public void signUp() {
        System.out.println("\n=== Account Sign Up (Hashing Enabled) ===");
        
        String email, displayName, password;
        
        // Input validation loop to check for existing email
        do {
            System.out.print("Enter Email Address: ");
            email = scanner.nextLine().trim();
            if (users.containsKey(email)) {
                System.out.println("⚠️ Error: This email is already registered. Please log in or use a different email.");
            }
        } while (users.containsKey(email));

        System.out.print("Enter Display Name: ");
        displayName = scanner.nextLine().trim();
        
        System.out.print("Enter Password: ");
        password = scanner.nextLine();
        
        // ⭐ HASHING STEP: Hash the raw password before storage
        String hashedPassword = HashUtil.hashPassword(password);
        
        // Create the User object using the HASHED password
        User newUser = new User(email, displayName, hashedPassword); 
        
        // Update persistent storage (UserData.txt) and in-memory map
        UserManager.saveUser(newUser); // Saves the HASHED password
        users.put(email, newUser);
        // ⭐ CSV FILE I/O IMPLEMENTATION HERE
        LogManager.writeLogEntry(email, "SIGNUP", "New account created.");
        System.out.println("\n🎉 Sign up successful! You can now log in.");
    }

    // --- 2. Login Logic ---
    /**
     * Authenticates a user by email and password.* @return The authenticated User object on success, or null on failure.
     * @return
     */
    public User login() {
        System.out.println("\n=== Account Login (Hashing Enabled) ===");
        
        System.out.print("Enter Email Address: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("Enter Password: ");
        String inputPassword = scanner.nextLine();
        
        // ⭐ HASHING STEP: Hash the user's input password for comparison
        String inputHash = HashUtil.hashPassword(inputPassword);

        // Check if the user exists in the in-memory map
        if (users.containsKey(email)) {
            User user = users.get(email);
            
            // Compare the hash of the input password with the stored hash
            if (user.getPassword().equals(inputHash)) { 
                // ⭐ CSV FILE I/O IMPLEMENTATION HERE (SUCCESS)
            LogManager.writeLogEntry(email, "LOGIN_SUCCESS", "Authenticated.");
                System.out.println("\n✅ Login successful!");
                return user; // Return the authenticated User object
            } else {
                // ⭐ CSV FILE I/O IMPLEMENTATION HERE (FAIL)
            LogManager.writeLogEntry(email, "LOGIN_FAIL", "Invalid password attempt.");
                System.out.println("❌ Invalid password.");
            }
        } else {
            // ⭐ CSV FILE I/O IMPLEMENTATION HERE (FAIL)
        LogManager.writeLogEntry(email, "LOGIN_FAIL", "Account not found.");
            System.out.println("❌ Account not found. Please register.");
        }
        
        return null; // Login failed
    }
    
}
