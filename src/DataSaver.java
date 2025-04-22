import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class DataSaver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> records = new ArrayList<>();
        boolean moreData = true;

        while (moreData) {
            String firstName = SafeInput.getNonZeroLenString(scanner, "Enter first name");
            String lastName = SafeInput.getNonZeroLenString(scanner, "Enter last name");
            String id = String.format("%06d", SafeInput.getRangedInt(scanner, "Enter ID", 1, 999999));
            String email = SafeInput.getNonZeroLenString(scanner, "Enter email");
            int year = SafeInput.getRangedInt(scanner, "Enter birth year", 1000, 9999);

            String record = String.join(", ",
                    firstName, lastName, id, email, String.valueOf(year));
            records.add(record);

            moreData = SafeInput.getYNConfirm(scanner, "Add another record?");
        }

        String fileName = SafeInput.getNonZeroLenString(scanner, "Enter file name (without extension)") + ".csv";
        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "/src/" + fileName);

        try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out))) {

            for (String rec : records) {
                writer.write(rec, 0, rec.length());
                writer.newLine();
            }

            System.out.println("\nData saved to: " + file.toAbsolutePath());
            System.out.println("\nSaved Records:");
            records.forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}