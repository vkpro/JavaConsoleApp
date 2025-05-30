package com.github.vkpro;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class ExpressionEvaluatorTest {

    private static final double DELTA = 0.0001;

    @Test
    @DisplayName("Test basic arithmetic operations")
    public void testBasicOperations() {
        assertEquals(5.0, ExpressionEvaluator.evaluate("2 + 3"), DELTA);
        assertEquals(-1.0, ExpressionEvaluator.evaluate("2 - 3"), DELTA);
        assertEquals(6.0, ExpressionEvaluator.evaluate("2 * 3"), DELTA);
        assertEquals(2.0, ExpressionEvaluator.evaluate("6 / 3"), DELTA);
    }

    @Test
    @DisplayName("Test expressions with parentheses")
    public void testParentheses() {
        assertEquals(14.0, ExpressionEvaluator.evaluate("2 * (3 + 4)"), DELTA);
        assertEquals(10.0, ExpressionEvaluator.evaluate("(2 + 3) * 2"), DELTA);
        assertEquals(1.0, ExpressionEvaluator.evaluate("(2 + 3) - 4"), DELTA);
        assertEquals(7.0, ExpressionEvaluator.evaluate("(2 + 3 * 2) - 1"), DELTA);
    }

    @Test
    @DisplayName("Test complex expressions")
    public void testComplexExpressions() {
        assertEquals(10.0, ExpressionEvaluator.evaluate("2 * 3 + 4"), DELTA);
        assertEquals(14.0, ExpressionEvaluator.evaluate("2 + 3 * 4"), DELTA);
        assertEquals(13.0, ExpressionEvaluator.evaluate("5 + 2 * 4"), DELTA);
        assertEquals(11.0, ExpressionEvaluator.evaluate("5 * 2 + 1"), DELTA);
    }

    @Test
    @DisplayName("Test negative numbers")
    public void testNegativeNumbers() {
        assertEquals(-5.0, ExpressionEvaluator.evaluate("-5"), DELTA);
        assertEquals(-1.0, ExpressionEvaluator.evaluate("-5 + 4"), DELTA);
        assertEquals(-20.0, ExpressionEvaluator.evaluate("-5 * 4"), DELTA);
        assertEquals(15.0, ExpressionEvaluator.evaluate("3 * (-5 + 10)"), DELTA);
    }

    @Test
    @DisplayName("Test decimal numbers")
    public void testDecimalNumbers() {
        assertEquals(5.5, ExpressionEvaluator.evaluate("2.5 + 3"), DELTA);
        assertEquals(7.5, ExpressionEvaluator.evaluate("2.5 * 3"), DELTA);
        assertEquals(3.5, ExpressionEvaluator.evaluate("7 / 2"), DELTA);
        assertEquals(3.33, ExpressionEvaluator.evaluate("10 / 3"), 0.01);
    }

    @Test
    @DisplayName("Test empty and null expressions")
    public void testEmptyAndNullExpressions() {
        assertThrows(IllegalArgumentException.class, () -> ExpressionEvaluator.evaluate(""));
        assertThrows(IllegalArgumentException.class, () -> ExpressionEvaluator.evaluate(null));
        assertThrows(IllegalArgumentException.class, () -> ExpressionEvaluator.evaluate("   "));
    }

    @Test
    @DisplayName("Test division by zero")
    public void testDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> ExpressionEvaluator.evaluate("5 / 0"));
        assertThrows(ArithmeticException.class, () -> ExpressionEvaluator.evaluate("10 / (5 - 5)"));
    }

    @Test
    @DisplayName("Test invalid expressions")
    public void testInvalidExpressions() {
        assertThrows(IllegalArgumentException.class, () -> ExpressionEvaluator.evaluate("2 + a"));
        assertThrows(IllegalArgumentException.class, () -> ExpressionEvaluator.evaluate("2 + @"));
        assertThrows(IllegalArgumentException.class, () -> ExpressionEvaluator.evaluate("2 +"));
    }
}