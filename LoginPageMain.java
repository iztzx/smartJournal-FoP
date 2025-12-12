import java.util.Scanner;
public class Main {
    private static final Scanner mainScanner = new Scanner(System.in);
    
    // --- Main Application Entry Point ---
    public static void main(String[] args) {
        
        AuthManager authManager = new AuthManager();
        User currentUser = null; 

        
        // Authentication Loop: Runs until a user successfully logs in or exits
        while (currentUser == null) {
            System.out.println("\n==================================");
            System.out.println("   SMART JOURNALING PLATFORM");
            System.out.println("==================================");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            
            if (mainScanner.hasNextLine()) {
                String choice = mainScanner.nextLine();
                
                if (choice.equals("1")) {
                    currentUser = authManager.login();
                } else if (choice.equals("2")) {
                    authManager.signUp();
                } else if (choice.equals("3")) {
                    System.out.println("Exiting application. Goodbye!");
                    return;
                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            } else {
                System.err.println("Input stream closed unexpectedly.");
                return;
            }
        }
        
        // --- POST-LOGIN LOGIC STARTS HERE ---
        System.out.println("\n--- Proceeding to Main Menu ---");
        // The welcome message and menu handling will be implemented here (or in a new class method)
        // For example: MainMenuHandler.startMenu(currentUser);
        // We will implement this next!
    }
    
}
