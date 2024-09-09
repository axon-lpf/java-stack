package com.axon.java.stack.juc.lockDowngrade;


import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

class ChatGptLockDowngrade {
    private final ReentrantLock reentrantLock = new ReentrantLock();
    private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private final StampedLock stampedLock = new StampedLock();
    private final Map<String, String> map = new HashMap<>();

    // ReentrantLock写操作
    @SneakyThrows
    public void putWithReentrantLock(String key, String value) {
        reentrantLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 正在写入 (ReentrantLock)");
            map.put(key, value);
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName() + " 写入完成 (ReentrantLock)");
        } finally {
            reentrantLock.unlock();
        }
    }

    // ReentrantLock读操作
    public void getWithReentrantLock(String key) throws InterruptedException {
        reentrantLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 正在读取 (ReentrantLock)");
            Thread.sleep(200);
            String result = map.get(key);
            System.out.println(Thread.currentThread().getName() + " 读取结束，结果: " + result);
        } finally {
            reentrantLock.unlock();
        }
    }

    // ReentrantReadWriteLock写操作，演示锁降级
    @SneakyThrows
    public void putWithReadWriteLock(String key, String value) {
        reentrantReadWriteLock.writeLock().lock();
        long startTime = System.currentTimeMillis();
        try {
            System.out.println(Thread.currentThread().getName() + " 正在写入 (ReentrantReadWriteLock)");
            map.put(key, value);
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName() + " 写入完成，开始锁降级 (ReentrantReadWriteLock)");

            // 锁降级
            reentrantReadWriteLock.readLock().lock();
        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }

        try {
            // 锁降级后的读操作
            System.out.println(Thread.currentThread().getName() + " 读取完成 (锁降级), 耗时: " + (System.currentTimeMillis() - startTime) + " ms");
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }

    // ReentrantReadWriteLock读操作
    public void getWithReadWriteLock(String key) throws InterruptedException {
        reentrantReadWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 正在读取 (ReentrantReadWriteLock)");
            Thread.sleep(200);
            String result = map.get(key);
            System.out.println(Thread.currentThread().getName() + " 读取结束，结果: " + result);
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }

    // StampedLock写操作
    @SneakyThrows
    public void putWithStampedLock(String key, String value) {
        long stamp = stampedLock.writeLock();
        try {
            System.out.println(Thread.currentThread().getName() + " 正在写入 (StampedLock)");
            map.put(key, value);
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName() + " 写入完成 (StampedLock)");
        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }

    // StampedLock悲观读操作
    public void getWithStampedLock(String key) throws InterruptedException {
        long stamp = stampedLock.readLock();
        try {
            System.out.println(Thread.currentThread().getName() + " 正在读取 (StampedLock)");
            Thread.sleep(200);
            String result = map.get(key);
            System.out.println(Thread.currentThread().getName() + " 读取结束，结果: " + result);
        } finally {
            stampedLock.unlockRead(stamp);
        }
    }

    // StampedLock乐观读操作
    public void tryOptimisticReadWithStampedLock(String key) throws InterruptedException {
        long stamp = stampedLock.tryOptimisticRead();
        try {
            System.out.println(Thread.currentThread().getName() + " 尝试乐观读 (StampedLock)");
            Thread.sleep(200); // 模拟读取延迟

            // 验证在读取期间是否有写操作
            if (!stampedLock.validate(stamp)) {
                System.out.println("数据可能已被修改，升级为悲观读锁...");
                stamp = stampedLock.readLock();
                try {
                    String result = map.get(key);
                    System.out.println(Thread.currentThread().getName() + " 读取结束，结果: " + result + " (悲观读)");
                } finally {
                    stampedLock.unlockRead(stamp);
                }
            } else {
                String result = map.get(key);
                System.out.println(Thread.currentThread().getName() + " 读取结束，结果: " + result + " (乐观读)");
            }
        } finally {
            // 这里不需要显式解锁，因为乐观读没有锁定行为
        }
    }
}

public class ChatGptLockDowngradeDemoTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("开始 ReentrantLock 测试...");
        reentrantLockTest();

        System.out.println("开始 ReentrantReadWriteLock 测试...");
        reentrantReadWriteLockTest();

        System.out.println("开始 StampedLock 测试...");
        stampedLockTest();

        System.out.println("开始 StampedLock 乐观读测试...");
        stampedTryOptimisticReadTest();
    }

    // ReentrantLock 案例测试
    private static void reentrantLockTest() throws InterruptedException {
        ChatGptLockDowngrade lockDowngrade = new ChatGptLockDowngrade();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> lockDowngrade.putWithReentrantLock(String.valueOf(finalI), String.valueOf(finalI)), String.valueOf(i)).start();
        }

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    lockDowngrade.getWithReentrantLock(String.valueOf(finalI));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }

        Thread.sleep(10000);
    }

    // ReentrantReadWriteLock 案例测试
    private static void reentrantReadWriteLockTest() throws InterruptedException {
        ChatGptLockDowngrade lockDowngrade = new ChatGptLockDowngrade();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> lockDowngrade.putWithReadWriteLock(String.valueOf(finalI), String.valueOf(finalI)), String.valueOf(i)).start();
        }

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    lockDowngrade.getWithReadWriteLock(String.valueOf(finalI));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }

        Thread.sleep(10000);
    }

    // StampedLock 案例测试
    private static void stampedLockTest() throws InterruptedException {
        ChatGptLockDowngrade lockDowngrade = new ChatGptLockDowngrade();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> lockDowngrade.putWithStampedLock(String.valueOf(finalI), String.valueOf(finalI)), String.valueOf(i)).start();
        }

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    lockDowngrade.getWithStampedLock(String.valueOf(finalI));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }

        Thread.sleep(10000);
    }

    // StampedLock 乐观读案例测试
    private static void stampedTryOptimisticReadTest() throws InterruptedException {
        ChatGptLockDowngrade lockDowngrade = new ChatGptLockDowngrade();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    lockDowngrade.tryOptimisticReadWithStampedLock(String.valueOf(finalI));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }

        Thread.sleep(10000);
    }
}