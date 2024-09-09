package com.axon.java.stack.juc.lockDowngrade;

import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

class LockDowngrade {
    // 一般的可重入锁
    private ReentrantLock releaseLock = new ReentrantLock();

    //读写锁
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    //邮戳锁
    private StampedLock stampedLock = new StampedLock();

    private Map<String, String> map = new HashMap<>();

    @SneakyThrows
    public void putWriteLockTest(String key, String value) {

        releaseLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "正在写入");
            map.put(key, value);
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName() + "写如完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            releaseLock.unlock();
        }

    }

    public void readLockTest(String key) throws InterruptedException {
        releaseLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "正在读取");
            Thread.sleep(200);
            String result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "读取结束，读取结果" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            releaseLock.unlock();
        }

    }


    @SneakyThrows
    public void reentrantReadWriteLockPutWriteLockTest(String key, String value) {
        reentrantReadWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "正在写入");
            map.put(key, value);
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName() + "写如完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }

    }

    public void reentrantReadWriteLockReadLockTest(String key) throws InterruptedException {
        reentrantReadWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "正在读取");
            Thread.sleep(200);
            String result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "读取结束，读取结果" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }

    }


    @SneakyThrows
    public void stampedLockPutWriteLockTest(String key, String value) {
        long stamp = stampedLock.writeLock();
        try {
            System.out.println(Thread.currentThread().getName() + "正在写入");
            map.put(key, value);
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + "写如完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            stampedLock.unlockWrite(stamp);
        }

    }

    public void stampedLockReadLockTest(String key) throws InterruptedException {
        long stamp = stampedLock.readLock();
        try {
            System.out.println(Thread.currentThread().getName() + "正在读取");
            Thread.sleep(200);
            String result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "读取结束，读取结果" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            stampedLock.unlockRead(stamp);
        }

    }

    /**
     * 这是一种乐观读的场景
     *
     * @param key
     * @throws InterruptedException
     */
    public void stampedLockTryOptimisticReadTest(String key) throws InterruptedException {
        long stamp = stampedLock.tryOptimisticRead();
        try {
            if (!stampedLock.validate(stamp)) {  // 如果 validate 返回 false，说明在读取期间发生了写操作，stamp 已失效
                //需要重新获取锁并进行读取
                stamp = stampedLock.readLock();
                try {
                    System.out.println(Thread.currentThread().getName() + "正在读取");
                    Thread.sleep(200);
                    String result = map.get(key);
                    System.out.println(Thread.currentThread().getName() + "读取结束，读取结果" + result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    stampedLock.unlockRead(stamp);
                }
            }else {
                System.out.println("没有被修改");
            }
        } finally {
            //stampedLock.unlockRead(stamp);
        }

    }


}


/**
 * 锁降级
 */
public class LockDowngradeDemoTest {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("开始reentrantReadLock 锁进行测试。。。。。。。。。。。。。。");
        reentrantLockTest();

        System.out.println("开始reentrantReadWriteLock 锁进行测试。。。。。。。。。。。。");
        reentrantReadWriteLockTest();

        System.out.println("开始邮戳锁锁进行测试。。。。。。。。。。。。。");
        stampedLockTest();

        System.out.println("开始邮戳锁锁乐观读进行测试。。。。。。。。。。。。。");
        stampedTryOptimisticReadTest();


    }


    /**
     * ReentrantLock 案例说明， 写的时候，不能读， 读的时候也不能写。 读的时候只能单个读，直至上个读取完成之后，在读取别的。
     */
    private static void reentrantLockTest() throws InterruptedException {
        LockDowngrade lockDowngrade = new LockDowngrade();

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                lockDowngrade.putWriteLockTest(String.valueOf(finalI), String.valueOf(finalI));
            }, String.valueOf(i)).start();
        }

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    lockDowngrade.readLockTest(String.valueOf(finalI));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }

        Thread.sleep(10000);
    }

    /**
     * ReentrantReadWriteLock  写只能单个写，写的时候不能读， 读可以多个线程一起读，  但是容易造写锁饥饿的情况， 读锁一直占用
     * ReentrantReadWriteLock 底层原理， 没次写完之后，在释放写锁之前，会转换成读锁， 保证当前的写锁写完之后，不被其他线程抢多读取。
     * 由写锁降级为读锁， 叫做锁降级， 但是不能由读锁升级为写锁。  场景： 数据库一般有些的权限， 肯定有读的权限。 但是有读的权限，不一定有些的权限。
     * <p>
     * 为了解决锁饥饿的问题， 引入了邮戳锁
     *
     * @throws InterruptedException
     */
    private static void reentrantReadWriteLockTest() throws InterruptedException {
        LockDowngrade lockDowngrade = new LockDowngrade();

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                lockDowngrade.reentrantReadWriteLockPutWriteLockTest(String.valueOf(finalI), String.valueOf(finalI));
            }, String.valueOf(i)).start();
        }

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    lockDowngrade.reentrantReadWriteLockReadLockTest(String.valueOf(finalI));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }

        Thread.sleep(10000);
    }


    /**
     * 邮戳锁, 此案例是和 reentrantReadWriteLock 是一样的情况
     *
     * @throws InterruptedException
     */
    private static void stampedLockTest() throws InterruptedException {
        LockDowngrade lockDowngrade = new LockDowngrade();

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                lockDowngrade.stampedLockPutWriteLockTest(String.valueOf(finalI), String.valueOf(finalI));
            }, String.valueOf(i)).start();
        }

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    lockDowngrade.stampedLockReadLockTest(String.valueOf(finalI));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }

        Thread.sleep(10000);
    }



    /**
     * 邮戳锁, 此案例是尝试乐观读
     *
     * @throws InterruptedException
     */
    private static void stampedTryOptimisticReadTest() throws InterruptedException {
        LockDowngrade lockDowngrade = new LockDowngrade();

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                lockDowngrade.stampedLockPutWriteLockTest(String.valueOf(finalI), String.valueOf(finalI));
            }, String.valueOf(i)).start();
        }

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    lockDowngrade.stampedLockTryOptimisticReadTest(String.valueOf(finalI));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }

        Thread.sleep(10000);
    }


}
