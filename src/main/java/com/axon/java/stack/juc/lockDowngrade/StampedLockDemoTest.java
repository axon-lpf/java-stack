package com.axon.java.stack.juc.lockDowngrade;

import java.util.concurrent.locks.StampedLock;

class MyStampedLock {

    private StampedLock stampedLock = new StampedLock();

    private volatile int number = 30;

    /**
     * 写锁设置
     */
    public void writer() {
        long stamp = stampedLock.writeLock();
        try {
            System.out.println(Thread.currentThread().getName() + "正在写入");
            number = number + 20;
            System.out.println(Thread.currentThread().getName() + "写入结束");
            //System.out.println("写锁没有修改成功，读锁无法介入");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }

    /**
     * 读锁设置
     *
     * @throws InterruptedException
     */
    public void read() throws InterruptedException {

        long stamp = stampedLock.readLock();
        try {
            // 故意暂停4s,很乐观的认为在读取的过程的中 没有线程修改她的值
            System.out.println(Thread.currentThread().getName() + "开始准备读");
            for (int i = 0; i < 4; i++) {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + "正在读取中");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            int result = number;
            System.out.println(Thread.currentThread().getName() + "获得当前结果" + result);
            System.out.println("写锁没有修改成功，读锁无法介入");
        } finally {
            stampedLock.unlockRead(stamp);
        }

    }


    public void tryOptimisticRead() throws InterruptedException {

        long stamp = stampedLock.tryOptimisticRead();
        int result = number;
        try {
            System.out.println(Thread.currentThread().getName() + "开始准备读");
            for (int i = 0; i < 4; i++) {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + "正在读取中");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!stampedLock.validate(stamp)) {
            long l = stampedLock.readLock();

            try {
                System.out.println("有人修改过写操作");

                System.out.println("从乐观读升级为悲观读");
                result = number;
                System.out.println(Thread.currentThread().getName() + "获得当前结果" + result);
            } finally {
                stampedLock.unlockRead(l);
            }
        }
        System.out.println("结果执行完毕" + result);


    }


}


public class StampedLockDemoTest {

    public static void main(String[] args) throws InterruptedException {

        //extracted();

        System.out.println("以下是乐观读测试");

        MyStampedLock myStampedLock = new MyStampedLock();


        new Thread(() -> {
            try {
                myStampedLock.tryOptimisticRead();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        Thread.sleep(2000);

        new Thread(() -> {
            myStampedLock.writer();
        }).start();



        Thread.sleep(1000L);

    }

    private static void extracted() throws InterruptedException {
        MyStampedLock myStampedLock = new MyStampedLock();

        new Thread(() -> {
            myStampedLock.writer();
        }).start();

        new Thread(() -> {
            try {
                myStampedLock.read();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        Thread.sleep(1000L);
    }
}
