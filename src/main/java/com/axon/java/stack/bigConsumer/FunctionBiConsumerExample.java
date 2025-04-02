package com.axon.java.stack.bigConsumer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @Author: liupengfei
 * @Date: 2025/4/2 17:42
 */
public class FunctionBiConsumerExample {
    public static void main(String[] args) {
        Map<String, Integer> scores = new HashMap<>();

        // Function: 计算分数等级
        Function<Integer, String> gradeFunction = score -> {
            if (score >= 90) {
                return "A";
            } else if (score >= 80) {
                return "B";
            } else {
                return "C";
            }
        };

        // BiConsumer: 存储成绩等级
        BiConsumer<String, Integer> storeGrade = (name, score) ->
                scores.put(name, Integer.valueOf(gradeFunction.apply(score)));

        storeGrade.accept("Alice", 92);
        storeGrade.accept("Bob", 85);
        storeGrade.accept("Charlie", 78);

        System.out.println(scores);
    }
}
