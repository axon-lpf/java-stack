package com.axon.java.stack.bigConsumer;

import java.util.function.Function;

/**
 * @Author: liupengfei
 * @Date: 2025/4/2 17:40
 */
public class SquareFunction {
    public static void main(String[] args) {
        // Function to find the square of a number
        Function<Integer, Integer> square = x -> x * x;

        // Implement the function using apply()
        System.out.println("Square of 4: " + square.apply(4));
    }
}
