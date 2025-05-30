package com.github.vkpro;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    @TempDir
    Path tempDir;

    @Test
    void testReadFromFile() throws IOException {
        // Create a test file with content
        Path testFile = tempDir.resolve("testRead.txt");
        String expectedContent = "Hello, World!\nThis is a test file.";
        Files.writeString(testFile, expectedContent);

        // Read the file using FileHandler
        String actualContent = FileHandler.readFromFile(testFile.toString());

        // Verify the content matches
        assertEquals(expectedContent, actualContent);
    }

    @Test
    void testWriteToFile() throws IOException {
        // Define test content and file path
        Path testFile = tempDir.resolve("testWrite.txt");
        String content = "This is content\nto be written to a file.";

        // Write content to file using FileHandler
        FileHandler.writeToFile(testFile.toString(), content);

        // Read the file directly and verify content
        String actualContent = Files.readString(testFile);
        assertEquals(content, actualContent);
    }

    @Test
    void testReadFromNonExistentFile() {
        // Try to read from a file that doesn't exist
        Path nonExistentFile = tempDir.resolve("nonexistent.txt");
        
        // Verify that an IOException is thrown
        assertThrows(IOException.class, () -> FileHandler.readFromFile(nonExistentFile.toString()));
    }

    @Test
    void testEmptyFileRead() throws IOException {
        // Create an empty file
        Path emptyFile = tempDir.resolve("empty.txt");
        Files.createFile(emptyFile);

        // Read the empty file
        String content = FileHandler.readFromFile(emptyFile.toString());

        // Verify that the content is empty
        assertEquals("", content);
    }
}