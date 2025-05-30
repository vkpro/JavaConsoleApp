package com.github.vkpro;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * A utility class for evaluating mathematical expressions provided as strings.
 * Supports basic arithmetic operations (+, -, *, /), parentheses, and negative numbers.
 */
public class ExpressionEvaluator {

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private ExpressionEvaluator() {
        // Utility class should not be instantiated
    }

    private static final String INVALID_FORMAT_MESSAGE = "Invalid expression format: ";

    /**
     * Evaluates a mathematical expression provided as a string.
     *
     * @param expression The mathematical expression to evaluate (e.g., "2+3*(4-1)")
     * @return The result of the evaluated expression
     * @throws IllegalArgumentException If the expression is null, empty, or has invalid format
     * @throws ArithmeticException If the expression contains division by zero
     */
    public static double evaluate(String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Expression cannot be empty");
        }
        
        // Remove all spaces
        expression = expression.replaceAll("\\s+", "");
        
        return evaluateExpression(expression);
    }
    
    private static double evaluateExpression(String expression) {
        Deque<Double> values = new ArrayDeque<>();
        Deque<Character> operators = new ArrayDeque<>();

        // Basic validation for incomplete expressions
        validateExpression(expression);

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            
            // If current character is a whitespace, skip it
            if (Character.isWhitespace(c)) {
                continue;
            }

            if (isDigitOrNegativeNumber(c, i, expression)) {
                i = processNumber(c, i, expression, values);
            }
            else if (c == '(') {
                operators.push(c);
            }
            else if (c == ')') {
                processClosingParenthesis(values, operators, expression);
            }
            else if (isOperator(c)) {
                processOperator(c, values, operators, expression);
            }
            else {
                throw new IllegalArgumentException("Invalid character in expression: " + c);
            }
        }

        // Process all remaining operators
        processRemainingOperators(values, operators, expression);

        // The final result should be the only value in the stack
        if (values.size() != 1 || !operators.isEmpty()) {
            throw new IllegalArgumentException(INVALID_FORMAT_MESSAGE + expression);
        }
        return values.pop();
    }

    private static void validateExpression(String expression) {
        if (expression.matches(".*[+\\-*/]\\s*$")) {
            throw new IllegalArgumentException("Incomplete expression: " + expression);
        }
    }

    private static boolean isDigitOrNegativeNumber(char c, int i, String expression) {
        return Character.isDigit(c) || (c == '-' && (i == 0 || expression.charAt(i-1) == '('));
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static int processNumber(char c, int i, String expression, Deque<Double> values) {
        StringBuilder numBuilder = new StringBuilder();

        // Handle negative numbers
        if (c == '-') {
            numBuilder.append(c);
            i++;
            if (i >= expression.length()) {
                throw new IllegalArgumentException(INVALID_FORMAT_MESSAGE + expression);
            }
        }

        // Get the complete number
        while (i < expression.length() &&
              (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
            numBuilder.append(expression.charAt(i++));
        }
        i--; // Move back one position as the for loop will increment

        values.push(Double.parseDouble(numBuilder.toString()));
        return i;
    }

    private static void processClosingParenthesis(Deque<Double> values, Deque<Character> operators, String expression) {
        while (!operators.isEmpty() && operators.peek() != '(') {
            if (values.size() < 2) {
                throw new IllegalArgumentException(INVALID_FORMAT_MESSAGE + expression);
            }
            values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
        }

        if (operators.isEmpty()) {
            throw new IllegalArgumentException("Mismatched parentheses in expression: " + expression);
        }
        operators.pop(); // Remove the '(' from stack
    }

    private static void processOperator(char c, Deque<Double> values, Deque<Character> operators, String expression) {
        while (!operators.isEmpty() && hasPrecedence(c, operators.peek())) {
            if (values.size() < 2) {
                throw new IllegalArgumentException(INVALID_FORMAT_MESSAGE + expression);
            }
            values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
        }
        operators.push(c);
    }

    private static void processRemainingOperators(Deque<Double> values, Deque<Character> operators, String expression) {
        while (!operators.isEmpty()) {
            if (values.size() < 2) {
                throw new IllegalArgumentException(INVALID_FORMAT_MESSAGE + expression);
            }

            char operator = operators.pop();
            if (operator == '(' || operator == ')') {
                throw new IllegalArgumentException("Mismatched parentheses in expression: " + expression);
            }

            values.push(applyOperation(operator, values.pop(), values.pop()));
        }
    }
    
    private static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        return (op1 != '*' && op1 != '/') || (op2 != '+' && op2 != '-');
    }
    
    private static double applyOperation(char operator, double b, double a) {
        return switch (operator) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> {
                if (b == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                yield a / b;
            }
            default -> 0;
        };
    }
}