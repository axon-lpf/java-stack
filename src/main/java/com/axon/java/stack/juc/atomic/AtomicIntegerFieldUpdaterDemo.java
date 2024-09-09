package com.axon.java.stack.juc.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

class BankAccount {

    public volatile int money;

    /**
     * 这里是保证原子性，多线程去处理，会造成值丢失
     */
    public void add() {
        money++;
    }


    AtomicIntegerFieldUpdater updater = AtomicIntegerFieldUpdater.newUpdater(BankAccount.class, "money");

    /**
     * 这里保证了原子性操作
     *
     * @param bankAccount
     */
    public void trantsMoeny(BankAccount bankAccount) {
        updater.getAndIncrement(bankAccount);
    }

}


public class AtomicIntegerFieldUpdaterDemo {
    public static void main(String[] args) throws InterruptedException {

        BankAccount bankAccount = new BankAccount();
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {

            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    bankAccount.add();
                    //bankAccount.trantsMoeny(bankAccount);
                }
                latch.countDown();

            }).start();
        }
        latch.await();
        System.out.println(" 当前money的值是" + bankAccount.money);

    }
}
