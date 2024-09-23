package com.axon.java.stack.data.structures.queue;

/**
 * 模计算,
 * <p>
 * 总结：
 * 总结：
 * 	1.	结果范围：取模运算的结果总是位于 [0, maxSize - 1] 的范围内。在该程序中，maxSize = 5，因此取模结果的范围是 [0, 4]。
 * 	2.	循环性质：由于取模结果每过 maxSize 次（即每次达到 5 的倍数），结果会重新开始循环，生成重复的序列 [0, 1, 2, 3, 4]。
 * 	3.	典型应用：此类取模运算常用于实现环形队列、哈希表分桶等场景，以确保索引或值在指定范围内循环。
 */
public class ModularArithmetic {

    public static void main(String[] args) {

        // 设置固定着
        int maxSize = 5;

        for (int i = 0; i < 100; i++) {

            int result = i % maxSize;
            System.out.println("当前的除数" + i + ",结果值" + result);
        }


    }
}
