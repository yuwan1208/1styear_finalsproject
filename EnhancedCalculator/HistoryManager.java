import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryManager {
    private static final String HISTORY_FILE = "history.txt";

    public void log(String expression, double result) {
        try (FileWriter writer = new FileWriter(HISTORY_FILE, true)) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            writer.write(timestamp + " | " + expression + " = " + result + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to history file: " + e.getMessage());
        }
    }

    public void showHistory() {
        System.out.println("\n--- Calculation History ---");
        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No history yet.");
        } catch (IOException e) {
            System.out.println("Error reading history file: " + e.getMessage());
        }
    }

    public void clearHistory() {
        try (PrintWriter writer = new PrintWriter(HISTORY_FILE)) {
            writer.print(""); // Clears the file content
            System.out.println("History cleared successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("Error clearing history: " + e.getMessage());
        }
    }
}
