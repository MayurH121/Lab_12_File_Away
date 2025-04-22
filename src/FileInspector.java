import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileInspector {
    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec;
        ArrayList<String> lines = new ArrayList<>();
        int lineCount = 0, wordCount = 0, charCount = 0;

        try {
            File workingDirectory = new File(System.getProperty("user.dir") + "/src");
            chooser.setCurrentDirectory(workingDirectory);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();

                System.out.println("\nFile Content:");
                try (InputStream in = new BufferedInputStream(Files.newInputStream(file));
                     BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

                    while (reader.ready()) {
                        rec = reader.readLine();
                        lines.add(rec);
                        lineCount++;
                        wordCount += rec.split("\\s+").length;
                        charCount += rec.length();
                        System.out.println(rec);
                    }
                }

                System.out.println("\nFile Summary Report:");
                System.out.println("File Name: " + selectedFile.getName());
                System.out.println("Number of Lines: " + lineCount);
                System.out.println("Number of Words: " + wordCount);
                System.out.println("Number of Characters: " + charCount);
            } else {
                System.out.println("No file selected.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}