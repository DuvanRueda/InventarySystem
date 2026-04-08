package co.edu.uptc.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DateFormatter {
    public static String getDateNow() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm"));
    }
}
