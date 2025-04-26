package com.axon.java.stack.bigConsumer;

import java.util.function.Function;

/**
 * @Author: liupengfei
 * @Date: 2025/4/2 17:38
 */
public class FunctionExample {
    public static void main(String[] args) {
        Function<String, Integer> stringLength = str -> str.length();

        System.out.println("Length of 'Hello': " + stringLength.apply("Hello"));
    }
}
