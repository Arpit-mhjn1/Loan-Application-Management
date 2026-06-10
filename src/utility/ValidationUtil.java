package utility;

import java.io.File;
import java.time.LocalDate;
import java.time.Period;

public class ValidationUtil {

    public static boolean isValidAge(LocalDate dob) {
        if (dob == null) return false;
        return Period.between(dob, LocalDate.now()).getYears() >= 18;
    }

    public static boolean isValidLoanAmount(double amount, double monthlyIncome) {
        return amount <= (20 * monthlyIncome);
    }

    public static boolean isValidPanAadhar(String id) {
        return id != null && (id.length() == 10 || id.length() == 12);
    }

    public static boolean isValidFile(File file) {
        if (file == null || !file.exists()) return false;
        
        long fileSizeInBytes = file.length();
        long fileSizeInKB = fileSizeInBytes / 1024;
        long fileSizeInMB = fileSizeInKB / 1024;
        
        if (fileSizeInMB >= 2) return false;
        
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".pdf") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg");
    }
}
