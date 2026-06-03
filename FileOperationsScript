import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * ============================================================================
 * DELIVERABLE: FILE OPERATIONS SCRIPT
 * * This program demonstrates how to handle text files in Java step-by-step.
 * We use a "try-catch" block for all steps because whenever Java talks to the 
 * computer's hard drive, there is a chance it might fail (e.g., the folder is 
 * locked, or the hard drive is full). The try-catch prevents the program 
 * from crashing.
 * ============================================================================
 */
public class FileOperationsScript {

    public static void main(String[] args) {
        
        // STEP 0: SETUP
        // We create a variable to hold our file name. 
        // This way, if we want to change the file name later, we only have to change it here.
        Path myFile = Paths.get("beginner_diary.txt");

        System.out.println("=== STARTING FILE OPERATIONS ===\n");

        try {
            
            // ==========================================
            // STEP 1: CREATE AND WRITE TO THE FILE
            // ==========================================
            // We create a string variable with our starting text. The '\n' means "start a new line".
            String startingText = "Day 1: I am learning how to code.\nIt is a little hard, but it is neat.\n";
            
            // This command creates the file and puts our text inside it.
            // If the file already exists, it will delete the old one and start fresh.
            Files.writeString(myFile, startingText);
            
            System.out.println("✅ STEP 1: Successfully created the file and wrote the first lines.");


            // ==========================================
            // STEP 2: READ THE FILE
            // ==========================================
            // We grab all the text from the file and store it in a single String variable.
            String textWeJustRead = Files.readString(myFile);
            
            System.out.println("\n📖 STEP 2: Here is what the file says right now:");
            System.out.println("-----------------------------------");
            System.out.print(textWeJustRead);
            System.out.println("-----------------------------------");


            // ==========================================
            // STEP 3: APPEND (ADD) TEXT TO THE FILE
            // ==========================================
            String extraLine = "Day 2: I figured out how to add text without deleting the old stuff!\n";
            
            // We use the exact same command as Step 1, but we add 'StandardOpenOption.APPEND'.
            // This tells Java: "Please don't delete what is already there, just put this at the bottom."
            Files.writeString(myFile, extraLine, StandardOpenOption.APPEND);
            
            System.out.println("\n✅ STEP 3: Successfully added a new line to the bottom of the file.");


            // ==========================================
            // STEP 4: MODIFY EXISTING TEXT (FIND AND REPLACE)
            // ==========================================
            // To change text inside a file, a human beginner generally does three things:
            // 1. Read the whole file into a string.
            // 2. Change the string.
            // 3. Write the new string back to the file, replacing the old content.
            
            String currentFileText = Files.readString(myFile);
            
            // Let's replace the word "neat" with "AWESOME"
            String modifiedText = currentFileText.replace("neat", "AWESOME");
            
            // Write the modified text back (this overwrites the file with our new version)
            Files.writeString(myFile, modifiedText);
            
            System.out.println("✅ STEP 4: Successfully replaced the word 'neat' with 'AWESOME'.");


            // ==========================================
            // STEP 5: READ THE FINAL RESULT
            // ==========================================
            String finalFileText = Files.readString(myFile);
            
            System.out.println("\n📖 STEP 5: Here is the final modified file:");
            System.out.println("-----------------------------------");
            System.out.print(finalFileText);
            System.out.println("-----------------------------------");

        } catch (IOException e) {
            // If anything goes wrong, Java skips down to here and prints our custom error message
            // instead of a confusing system error.
            System.out.println("❌ ERROR: Something went wrong with the file!");
            System.out.println("Exact error message: " + e.getMessage());
        }
        
        System.out.println("\n=== FILE OPERATIONS COMPLETE ===");
    }
}
