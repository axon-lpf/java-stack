package com.axon.java.stack.juc.Volatile;

public class VolatileTestDemo04 {


    private volatile int value;

    public int getValue() {
        return value;
    }

    public synchronized int setValue() {
        return value++;
    }


}
