package com.axon.java.stack.juc.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArrayDemo {

    static AtomicIntegerArray intArray = new AtomicIntegerArray(new int[]{1, 2, 3, 4, 5});

    static AtomicIntegerArray intArray2 = new AtomicIntegerArray(new int[10]);

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            System.out.println(intArray.get(i));
        }

        System.out.println("开始设置");

        for (int i = 0; i < 10; i++) {
            intArray2.compareAndSet(i, 0, i);
        }

        System.out.println("开始打印");
        for (int i = 0; i < 10; i++) {
            System.out.println(intArray2.get(i));
        }

    }
}
