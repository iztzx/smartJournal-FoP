import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class LogManager {
    
    private static final String LOG_FILE = "activity_log.csv";
    private static final String CSV_HEADER = "Timestamp,Email,Action,Detail";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Appends a log entry to the activity_log.csv file.
     * @param email The user's email or "SYSTEM".
     * @param action The action (e.g., LOGIN_SUCCESS, SIGNUP, LOGIN_FAIL).
     * @param detail A descriptive message.
     */
    public static void writeLogEntry(String email, String action, String detail) {
        
        String timestamp = LocalDateTime.now().format(FORMATTER);
        
        // Ensure detail does not break CSV structure
        String safeDetail = detail.replace(",", ";");
        
        // Construct the new CSV line
        String logLine = String.format("%s,%s,%s,%s", timestamp, email, action, safeDetail);

        try (PrintWriter pw = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            
            // Write header only if the file is new or empty
            if (new java.io.File(LOG_FILE).length() == 0) {
                 pw.println(CSV_HEADER);
            }
            
            pw.println(logLine);
            
        } catch (IOException e) {
            System.err.println("❌ Error writing to activity log: " + e.getMessage());
        }
    }
}
    

