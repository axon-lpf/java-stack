package com.axon.java.stack.juc.oom;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * gc 回收频繁，超出限制
 * <p>
 * *  设置VM options
 * * -Xms10m  -Xmx10m  -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
 * <p>
 * gc 频繁回收超过了限制
 * Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded
 */

public class GCLimitOverflowTest {
    public static void main(String[] args) {
        try {
            boolean flag = true;
            List<String> result = new ArrayList<>();
            while (flag) {
                result.add(UUID.randomUUID().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        System.out.println("执行结束");
    }
}
