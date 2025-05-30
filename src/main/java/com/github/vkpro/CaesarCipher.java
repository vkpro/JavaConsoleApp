package com.github.vkpro;

/**
 * Implementation of the Caesar cipher encryption and decryption algorithm.
 * This class supports both English and Russian alphabets, handling both uppercase
 * and lowercase letters while preserving non-alphabetic characters.
 */
public class CaesarCipher {
    private static final String ENGLISH_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String ENGLISH_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String RUSSIAN_LOWERCASE = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static final String RUSSIAN_UPPERCASE = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";

    /**
     * Encrypts the given text using the Caesar cipher algorithm.
     *
     * @param text  The text to encrypt
     * @param shift The number of positions to shift each character (positive for right shift)
     * @return The encrypted text
     */
    public static String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();

        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                String alphabet;
                int alphabetLength;
                int charPosition;

                // Determine which alphabet to use
                if (ENGLISH_LOWERCASE.indexOf(character) != -1) {
                    alphabet = ENGLISH_LOWERCASE;
                    alphabetLength = ENGLISH_LOWERCASE.length();
                    charPosition = ENGLISH_LOWERCASE.indexOf(character);
                } else if (ENGLISH_UPPERCASE.indexOf(character) != -1) {
                    alphabet = ENGLISH_UPPERCASE;
                    alphabetLength = ENGLISH_UPPERCASE.length();
                    charPosition = ENGLISH_UPPERCASE.indexOf(character);
                } else if (RUSSIAN_LOWERCASE.indexOf(character) != -1) {
                    alphabet = RUSSIAN_LOWERCASE;
                    alphabetLength = RUSSIAN_LOWERCASE.length();
                    charPosition = RUSSIAN_LOWERCASE.indexOf(character);
                } else if (RUSSIAN_UPPERCASE.indexOf(character) != -1) {
                    alphabet = RUSSIAN_UPPERCASE;
                    alphabetLength = RUSSIAN_UPPERCASE.length();
                    charPosition = RUSSIAN_UPPERCASE.indexOf(character);
                } else {
                    // This should not happen as we already checked isLetter
                    result.append(character);
                    continue;
                }

                // Calculate new position with wrap-around
                int newPosition = (charPosition + shift) % alphabetLength;
                if (newPosition < 0) {
                    newPosition += alphabetLength;
                }

                // Append the encrypted character
                result.append(alphabet.charAt(newPosition));
            } else {
                // Non-alphabetic characters remain unchanged
                result.append(character);
            }
        }

        return result.toString();
    }

    /**
     * Decrypts the given text that was encrypted using the Caesar cipher algorithm.
     *
     * @param text  The text to decrypt
     * @param shift The original shift value used for encryption
     * @return The decrypted text
     */
    public static String decrypt(String text, int shift) {
        // Decryption is just encryption with the negative shift
        return encrypt(text, -shift);
    }
}