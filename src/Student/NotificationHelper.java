package Student;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NotificationHelper {

    private static final String NOTIFICATION_FILE = "TextFiles/Notification.txt";

    public static void addNotification(String studentID, String type, String message) {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String entry = studentID + ";" + type + ";" + message + ";" + dateTime;
        try (FileWriter fw = new FileWriter(NOTIFICATION_FILE, true)) {
            fw.write(entry + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Error writing notification: " + e.getMessage());
        }
    }
}
