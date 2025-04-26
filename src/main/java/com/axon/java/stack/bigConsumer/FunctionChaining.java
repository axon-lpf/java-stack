package com.axon.java.stack.bigConsumer;

import java.util.function.Function;

/**
 * @Author: liupengfei
 * @Date: 2025/4/2 17:41
 */
public class FunctionChaining {
    public static void main(String[] args) {
        Function<Integer, Integer> multiplyBy2 = x -> x * 2;
        Function<Integer, Integer> add3 = x -> x + 3;

        Function<Integer, Integer> combined = multiplyBy2.andThen(add3);
        System.out.println("Result: " + combined.apply(5)); // (5 * 2) + 3 = 13
    }
}
