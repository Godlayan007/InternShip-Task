import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileHandler {

    public static void main(String[] args) {
        // Define the name and path of our text file
        Path filePath = Paths.get("human_code_example.txt");

        try {
            // ==========================================
            // 1. WRITE TO THE FILE
            // ==========================================
            String initialContent = "Hello! This is the original text.\nLearning to code is fun.\n";

            // This creates the file if it doesn't exist, and overwrites it if it does.
            Files.writeString(filePath, initialContent);
            System.out.println("✅ Step 1: File created and written successfully.");


            // ==========================================
            // 2. READ THE FILE
            // ==========================================
            System.out.println("\n📖 Step 2: Reading current file content:");
            String readContent = Files.readString(filePath);
            System.out.println("-----------------------------------");
            System.out.print(readContent);
            System.out.println("-----------------------------------");


            // ==========================================
            // 3. MODIFY THE FILE (Two ways)
            // ==========================================

            // Way A: Append (add) new text to the end of the file
            String newSentence = "This sentence was appended later.\n";
            Files.writeString(filePath, newSentence, StandardOpenOption.APPEND);
            System.out.println("\n✅ Step 3a: New text appended to the file.");

            // Way B: Find and Replace specific text inside the file
            // First, read the whole file into a string
            String currentText = Files.readString(filePath);

            // Second, replace a specific word
            String modifiedText = currentText.replace("fun", "AWESOME");

            // Third, overwrite the file with the new modified string
            Files.writeString(filePath, modifiedText);
            System.out.println("✅ Step 3b: Replaced the word 'fun' with 'AWESOME'.");


            // ==========================================
            // 4. READ THE FINAL RESULT
            // ==========================================
            System.out.println("\n📖 Step 4: Reading the final modified content:");
            String finalContent = Files.readString(filePath);
            System.out.println("-----------------------------------");
            System.out.print(finalContent);
            System.out.println("-----------------------------------");

        } catch (IOException e) {
            // If something goes wrong (e.g., no permission to write to the folder), catch the error here.
            System.err.println("❌ An error occurred while handling the file: " + e.getMessage());
        }
    }
}