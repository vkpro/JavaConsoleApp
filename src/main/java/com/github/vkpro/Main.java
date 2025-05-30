package com.github.vkpro;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Set console encoding
        System.setProperty("file.encoding", "UTF-8");
        System.setOut(new java.io.PrintStream(System.out, true, StandardCharsets.UTF_8));

        // Using try-with-resources to ensure scanner is closed
        try (var scanner = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            boolean running = true;

            while (running) {
                displayMainMenu();
                int choice = getIntInput(scanner, "Enter your choice: ");
                if (choice == -1) continue;

                // Using switch expressions
                running = switch (choice) {
                    case 1 -> handleEncryptionMenu(scanner);
                    case 2 -> handleDecryptionMenu(scanner);
                    case 3 -> { evaluateExpression(scanner); yield true; }
                    case 0 -> { System.out.println("Exiting..."); yield false; }
                    default -> { System.out.println("Invalid choice!"); yield true; }
                };

                if (running) {
                    System.out.print("\nContinue? (y/n): ");
                    String continueChoice = scanner.nextLine().trim().toLowerCase();
                    if (!continueChoice.equals("y")) {
                        running = false;
                    }

                    // Clear the console for better readability
                    System.out.println("\n-----------------------------------\n");
                }
            }
        } // Scanner automatically closed with try-with-resources
    }

    private static void displayMainMenu() {
        System.out.println("""
            Please choose an option:
            1. Caesar Cipher Encryption
            2. Caesar Cipher Decryption
            3. Arithmetic Expression Evaluation
            0. Exit
            """);
    }

    private static int getIntInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        try {
            int input = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            return input;
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Clear the invalid input
            return -1;
        }
    }

    private static boolean handleEncryptionMenu(Scanner scanner) {
        return handleSubMenu(scanner,
            """

            Encryption Options:
            1. Encrypt from console
            2. Encrypt from file
            0. Back
            """,
            option -> {
                switch (option) {
                    case 1 -> encryptFromConsole(scanner);
                    case 2 -> encryptFromFile(scanner);
                    case 0 -> System.out.println("Returning to main menu...");
                    default -> System.out.println("Invalid choice!");
                }
            });
    }

    private static boolean handleDecryptionMenu(Scanner scanner) {
        return handleSubMenu(scanner,
            """

            Decryption Options:
            1. Decrypt from console
            2. Decrypt from file
            0. Back
            """,
            option -> {
                switch (option) {
                    case 1 -> decryptFromConsole(scanner);
                    case 2 -> decryptFromFile(scanner);
                    case 0 -> System.out.println("Returning to main menu...");
                    default -> System.out.println("Invalid choice!");
                }
            });
    }

    private static boolean handleSubMenu(Scanner scanner, String menuText, java.util.function.Consumer<Integer> optionHandler) {
        System.out.println(menuText);

        int choice = getIntInput(scanner, "Enter your choice: ");
        if (choice == -1) return true;

        optionHandler.accept(choice);
        return choice != 0; // Return false when user selects "Back" (0)
    }

    private static void evaluateExpression(Scanner scanner) {
        System.out.print("Enter arithmetic expression: ");
        String expression = scanner.nextLine();

        try {
            double result = ExpressionEvaluator.evaluate(expression);
            // Check if result is an integer
            if (result == (int) result) {
                System.out.println("Result: " + (int) result);
            } else {
                System.out.println("Result: " + result);
            }
        } catch (Exception e) {
            System.out.println("Error evaluating expression: " + e.getMessage());
        }
    }

    private static void encryptFromConsole(Scanner scanner) {
        System.out.print("Enter text to encrypt: ");
        String text = scanner.nextLine();
        
        System.out.print("Enter shift value: ");
        try {
            int shift = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            String encrypted = CaesarCipher.encrypt(text, shift);
            System.out.println("Encrypted text: " + encrypted);
        } catch (java.util.InputMismatchException e) {
            System.out.println("Error: Please enter a valid number for shift value.");
            scanner.nextLine(); // Clear the invalid input
        } catch (Exception e) {
            System.out.println("Error encrypting text: " + e.getMessage());
            scanner.nextLine(); // Clear the invalid input
        }
    }
    
    private static void decryptFromConsole(Scanner scanner) {
        System.out.print("Enter text to decrypt: ");
        String text = scanner.nextLine();
        
        System.out.print("Enter shift value: ");
        try {
            int shift = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            String decrypted = CaesarCipher.decrypt(text, shift);
            System.out.println("Decrypted text: " + decrypted);
        } catch (java.util.InputMismatchException e) {
            System.out.println("Error: Please enter a valid number for shift value.");
            scanner.nextLine(); // Clear the invalid input
        } catch (Exception e) {
            System.out.println("Error decrypting text: " + e.getMessage());
            scanner.nextLine(); // Clear the invalid input
        }
    }

    private static void encryptFromFile(Scanner scanner) {
        try {
            System.out.print("Enter input file path: ");
            String inputPath = scanner.nextLine();
            
            System.out.print("Enter output file path: ");
            String outputPath = scanner.nextLine();
            
            System.out.print("Enter shift value: ");
            int shift = scanner.nextInt();
            
            String text = FileHandler.readFromFile(inputPath);
            String encrypted = CaesarCipher.encrypt(text, shift);
            FileHandler.writeToFile(outputPath, encrypted);
            
            System.out.println("Text encrypted and saved to " + outputPath);
        } catch (java.util.InputMismatchException e) {
            System.out.println("Error: Please enter a valid number for shift value.");
            scanner.nextLine(); // Clear the invalid input
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void decryptFromFile(Scanner scanner) {
        try {
            System.out.print("Enter input file path: ");
            String inputPath = scanner.nextLine();
            
            System.out.print("Enter output file path: ");
            String outputPath = scanner.nextLine();
            
            System.out.print("Enter shift value: ");
            int shift = scanner.nextInt();
            
            String text = FileHandler.readFromFile(inputPath);
            String decrypted = CaesarCipher.decrypt(text, shift);
            FileHandler.writeToFile(outputPath, decrypted);
            
            System.out.println("Text decrypted and saved to " + outputPath);
        } catch (java.util.InputMismatchException e) {
            System.out.println("Error: Please enter a valid number for shift value.");
            scanner.nextLine(); // Clear the invalid input
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}