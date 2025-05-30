package com.github.vkpro;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CaesarCipherTest {

    @Test
    public void testEncryptEnglish() {
        assertEquals("Khoor Zruog", CaesarCipher.encrypt("Hello World", 3));
        assertEquals("Ifmmp Xpsme", CaesarCipher.encrypt("Hello World", 1));
        assertEquals("Gdkkn Vnqkc", CaesarCipher.encrypt("Hello World", -1));
    }

    @Test
    public void testEncryptRussian() {
        assertEquals("Фхнжйч Снх", CaesarCipher.encrypt("Привет Мир", 5));
        assertEquals("Рсйгёу Нйс", CaesarCipher.encrypt("Привет Мир", 1));
        assertEquals("Опзбдс Лзп", CaesarCipher.encrypt("Привет Мир", -1));
    }

    @Test
    public void testDecryptEnglish() {
        assertEquals("Hello World", CaesarCipher.decrypt("Khoor Zruog", 3));
        assertEquals("Hello World", CaesarCipher.decrypt("Ifmmp Xpsme", 1));
        assertEquals("Hello World", CaesarCipher.decrypt("Gdkkn Vnqkc", -1));
    }

    @Test
    public void testDecryptRussian() {
        assertEquals("Привет Мир", CaesarCipher.decrypt("Фхнжйч Снх", 5));
        assertEquals("Привет Мир", CaesarCipher.decrypt("Рсйгёу Нйс", 1));
        assertEquals("Привет Мир", CaesarCipher.decrypt("Опзбдс Лзп", -1));
    }

    @Test
    public void testPreserveCase() {
        assertEquals("Khoor Zruog", CaesarCipher.encrypt("Hello World", 3));
        assertEquals("khoor zruog", CaesarCipher.encrypt("hello world", 3));
        assertEquals("KHOOR ZRUOG", CaesarCipher.encrypt("HELLO WORLD", 3));
    }

    @Test
    public void testPreserveNonAlphabetic() {
        assertEquals("Khoor, Zruog! 123", CaesarCipher.encrypt("Hello, World! 123", 3));
        assertEquals("Фхнжйч, Снх! 123", CaesarCipher.encrypt("Привет, Мир! 123", 5));
    }

    @Test
    public void testWrapAround() {
        // English alphabet wrap-around
        assertEquals("xyz", CaesarCipher.encrypt("abc", -3));
        assertEquals("abc", CaesarCipher.encrypt("xyz", 3));

        // Russian alphabet wrap-around
        assertEquals("абв", CaesarCipher.encrypt("эюя", 3));
        assertEquals("эюя", CaesarCipher.encrypt("абв", -3));

        // Mixed case wrap-around
        assertEquals("Abc", CaesarCipher.encrypt("Xyz", 3));
        assertEquals("аБв", CaesarCipher.encrypt("эЮя", 3));
    }
}