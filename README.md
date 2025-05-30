# Java Console Application

A simple Java console application that demonstrates basic Java programming concepts and console
input/output operations.

## Project Description

This Java Console Application is designed to showcase fundamental Java programming principles
through a command-line interface. The application allows users to interact with various features
through text-based commands, demonstrating core concepts such as:

## Features


## Requirements

- Java Development Kit (JDK) 21 or higher
- Any Java IDE (Eclipse, IntelliJ IDEA, etc.) or a text editor

## Getting Started

1. Clone the repository:
   ```
   git clone https://github.com/vkpro/JavaConsoleApp.git
   ```

2. Open the project in your preferred IDE or navigate to the project directory in your terminal.

3. Compile the Java files:
   ```
   ./gradlew build
   ```

   **Without Gradle:**
   ```
   mkdir -p build/classes
   javac -d build/classes src/main/java/com/github/vkpro/*.java
   ```

4. Run the application:
   ```
   ./gradlew run
   ```

   **Without Gradle:**
   ```
   java -cp build/classes com.github.vkpro.Main
   ```

   **Create executable JAR (optional):**
   ```
   ./gradlew jar
   java -jar build/libs/JavaConsoleApp-*.jar
   ```

   **Without Gradle (create JAR):**
   ```
   mkdir -p build/libs
   jar cvfe build/libs/JavaConsoleApp.jar com.github.vkpro.Main -C build/classes .
   java -jar build/libs/JavaConsoleApp.jar
   ```

## Usage Examples

### Main Menu

```
Please choose an option:
1. Caesar Cipher Encryption
2. Caesar Cipher Decryption
3. Arithmetic Expression Evaluation
0. Exit
```

### Caesar Cipher Encryption

```
Please choose an option:
1. Encrypt text from console
2. Encrypt text from file
0. Back to main menu
```

### Arithmetic Expression Evaluation

```
Enter arithmetic expression: 2 + 3 * 4
Result: 14

Enter arithmetic expression: (10 + 5) / 3
Result: 5

Enter arithmetic expression: 2 * (3 + 4) - 1
Result: 13

Enter arithmetic expression: -5 + 3
Result: -2
```

## Project Structure

- `src/main/java/com/github/vkpro/` - Source code directory
   - `Main.java` - Main application entry point
   - `CaesarCipher.java` - Caesar cipher implementation
   - `ExpressionEvaluator.java` - Arithmetic expression parser and evaluator
   - `FileHandler.java` - File operations handler
- `docs/` - Documentation files

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request


## Contact

Project Link: [https://github.com/vkpro/JavaConsoleApp](https://github.com/vkpro/JavaConsoleApp)