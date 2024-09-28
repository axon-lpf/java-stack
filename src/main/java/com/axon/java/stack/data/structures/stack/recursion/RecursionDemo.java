package com.axon.java.stack.data.structures.stack.recursion;


/**
 * 递归的演示 demo
 * <p>
 * 递归的场景这里的演示，主要是去解决回溯算法的
 * 递归的也是栈的一种体现
 */
public class RecursionDemo {

    public static void main(String[] args) {

        test(10000);
        System.out.println("主方法运行结束");

    }

    public static void test(int n) {
        if (n > 0) {
            test(n - 1);
        }
    }
}
