package com.axon.java.stack.juc.lockDowngrade;


import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyDowngradeTest {

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * 锁降级案例
     *
     * 独占写锁：当一个线程持有写锁时，其他线程既不能获取读锁也不能获取写锁。此时，该线程可以安全地获取读锁（降级），因为其他线程无法干扰。
     * 读锁共享：在写锁释放之前获取读锁意味着当前线程可以继续持有对数据的控制权，不会因为写锁的释放而立即导致其他线程修改数据。
     *
     * 由于写锁在持有期间，其他线程无法获取任何锁，这就保证了在降级过程中不会出现死锁的情况。
     */
    public void test1() {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "开始正在写入了。。。。。。。");

            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + "写入完成。。。。。。。");
            //在写锁释放前降级为读锁
            //
            readWriteLock.readLock().lock();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }

        try {
            System.out.println(Thread.currentThread().getName() + "开始正在读取了");
            Thread.sleep(100);
            System.out.println(Thread.currentThread().getName() + "读取完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    /**
     * 反面案例，读锁不能升级为写锁案例
     */
    public void test2() {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "正在开始读取了。。。。。。。");

            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + "读取完成。。。。。。。");
            //在写锁释放前降级为读锁
            readWriteLock.writeLock().lock();  //锁升级失败，程序卡住，因为获取写锁的操作会一直等待，直到其他线程释放了读锁。由于当前线程已经持有了读锁且不会释放，因此形成了死锁。
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "读锁释放了。。。。");
            readWriteLock.readLock().unlock();
        }

        // 这里就卡住了。
        try {
            System.out.println(Thread.currentThread().getName() + "开始正在写入了");
            Thread.sleep(100);
            System.out.println(Thread.currentThread().getName() + "写入完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }


}

/**
 * 锁降级
 */
public class DowngradeTest {
    public static void main(String[] args) {

        MyDowngradeTest myDowngrade = new MyDowngradeTest();
/*        for (int i = 0; i < 10; i++) {

            new Thread(() -> {
                myDowngrade.test1();
            }).start();
        }*/

        for (int i = 0; i < 10; i++) {

            new Thread(() -> {
                myDowngrade.test2();
            }).start();
        }

    }
}
