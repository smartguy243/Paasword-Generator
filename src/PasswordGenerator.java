     /*
       @author: SmartGuy
       @project name: Password Generator
       @description: The user specifies the password length and character types to be included.
                     The program generates and displays a secure password.
                     There's an option to copy the password to the clipboard.
     */


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

public class PasswordGenerator {

    private static final List<String> generatedPasswords = new ArrayList<>(); // List to store generated passwords

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in); // Create a scanner object to read user input

        int choice; // Variable to store user's menu choice

        do {
            System.out.println("\n--------****----------- PASSWORD GENERATOR -----------****--------\n");

            // Display the menu options
            System.out.println("MENU:");
            System.out.println("1. Generate a password");
            System.out.println("2. View generated passwords");
            System.out.println("3. Exit the program\n");
            System.out.print("Please enter your choice (1-3): ");

            // Read user's choice
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    passwordGeneratorBoard(scanner); // Call method to generate a password
                    break;
                case 2:
                    viewGeneratedPasswords(); // Call method to view generated passwords
                    break;
                case 3:
                    System.out.println("\nExiting the program."); // Exit message
                    break;
                default:
                    System.out.println("Invalid input! Please enter 1, 2, or 3."); // Invalid input message
            }
        } while (choice != 3); // Repeat until user chooses to exit

        scanner.close(); // Close the scanner resource
    }

    // Method to take details from user for generating a password
    private static void passwordGeneratorBoard(Scanner scanner) {

        int length; // Variable to store password length

        // Loop until a valid length is provided
        while (true) {

            System.out.println("\n--------****----------- GENERATING PASSWORD -----------****--------\n");

            System.out.print("Enter the desired password length (minimum 8 characters): ");

            if (scanner.hasNextInt()) { // Check if input is an integer
                length = scanner.nextInt();

                if (length >= 8)
                    break; // Valid length, exit loop
                else System.out.println("Length must be at least 8 characters.");
            } else {
                System.out.println("\nInvalid input! Please enter a number."); // Invalid input message

                scanner.next(); // Clear the invalid input
            }
        }

        // Ask user for character types to be included in password
        boolean includeLowercase = getUserChoice(scanner, "Include lowercase letters? (y/n): ");
        boolean includeUppercase = getUserChoice(scanner, "Include uppercase letters? (y/n): ");
        boolean includeDigits = getUserChoice(scanner, "Include digits? (y/n): ");
        boolean includeSpecialChars = getUserChoice(scanner, "Include special characters? (y/n): ");

        String generatedPassword = generateRandomPassword(length, includeLowercase, includeUppercase, includeDigits, includeSpecialChars);
        generatedPasswords.add(generatedPassword); // Store the generated password

        System.out.println("\nGenerated Password: " + generatedPassword); // Display the generated password

        // Ask user if they want to copy the password to clipboard
        if (getUserChoice(scanner, "\nWould you like to copy the password to the clipboard? (y/n): ")) {
            copyToClipboard(generatedPassword); // Copy password to clipboard
            System.out.println("\nYour password has been successfully copied to the clipboard.");
        }
    }

    // Method to manage user choices
    private static boolean getUserChoice(Scanner scanner, String message) {
        String userInput;
        do {
            System.out.print(message); // Get user input
            userInput = scanner.next(); // Read user userInput
            if (userInput.equalsIgnoreCase("y")) return true; // Return true if user wants to include this character type
            if (userInput.equalsIgnoreCase("n")) return false; // Return false if user does not want to include this character type
            System.out.println("Invalid input! Please enter 'y' or 'n'."); // Invalid input message
        } while (true);
    }

    // Method to generate random password
    private static String generateRandomPassword(int length, boolean includeLowercase, boolean includeUppercase, boolean includeDigits, boolean includeSpecialChars) {
        StringBuilder password = new StringBuilder(); // Use StringBuilder to build the password
        String characters = ""; // Variable to hold available characters

        // Add character sets based on user choices
        if (includeLowercase) characters += "abcdefghijklmnopqrstuvwxyz"; // Add lowercase letters
        if (includeUppercase) characters += "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // Add uppercase letters
        if (includeDigits) characters += "0123456789"; // Add digits
        if (includeSpecialChars) characters += "!@#$%^&*()-_=+[]{}|;:,.<>?"; // Add special characters

        // Check if at least one character type was selected
        if (characters.isEmpty()) {
            System.out.println("You must select at least one type of character.");
            return ""; // Exit if no character types were selected
        }

        Random random = new Random(); // Create a Random object

        // Generate the password by selecting random characters from the defined set
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length()); // Get a random index
            password.append(characters.charAt(index)); // Append the character at that index to the password
        }

        return password.toString(); // Return the generated password as a string
    }

    // Method to view generated passwords
    private static void viewGeneratedPasswords() {

        System.out.println("\n--------****----------- CLIPBOARD -----------****--------\n");

        if (generatedPasswords.isEmpty()) { // Check if there are any generated passwords

            System.out.println("No passwords have been generated yet."); // Message when no passwords are available

            return; // Exit method if no passwords are available
        }

        System.out.println("Generated Passwords:\n"); // Display header for passwords list
        for (String password : generatedPasswords) {
            System.out.println(password); // Print each generated password
        }
    }

    // Method to copy generated passwords to clipboard
    private static void copyToClipboard(String text) {
        StringSelection stringSelection = new StringSelection(text); // Create a StringSelection object with the text
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null); // Set the text in the system clipboard
    }
}