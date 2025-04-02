package com.axon.java.stack.bigConsumer;

import java.util.function.BiConsumer;

/**
 * @Author: liupengfei
 * @Date: 2025/4/2 17:38
 */
public class MathOperations {
    public static void main(String[] args) {
        // BiConsumer to add 2 numbers
        // and print the result
        BiConsumer<Integer, Integer> add = (a, b) -> System.out.println("Sum: " + (a + b));

        // Implement add using accept()
        add.accept(2, 3);

        // BiConsumer to multiply 2 numbers
        // and print the result
        BiConsumer<Integer, Integer> multiply = (a, b) -> System.out.println("Product: " + (a * b));

        // Using addThen() method
        add.andThen(multiply).accept(2, 3);
    }
}
