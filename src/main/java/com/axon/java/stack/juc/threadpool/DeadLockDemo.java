package com.axon.java.stack.juc.threadpool;


class MyDeadLock implements Runnable {

    private String lockA;
    private String lockB;

    public MyDeadLock(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName() + "线程开始运行了");
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "已经获得" + lockA);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "已经获得" + lockB);
            }
        }
        System.out.println("运行结束");
    }


}

/**
 *  这里是一个死锁案例， 你中有我， 我中有你
 *
 *  A 获取锁表B , B获取锁B
 *
 *
 *  这个代码是一个经典的死锁（Deadlock）案例，它展示了两个线程如何因为互相等待对方持有的资源而陷入无法继续执行的状态。以下是对这个死锁案例的详细分析：
 *
 * 代码解析
 * 	1.	MyDeadLock 类：
 * 	•	这是一个实现了Runnable接口的类，用于创建线程任务。该类的构造方法接受两个字符串参数lockA和lockB，代表两个不同的锁对象。
 * 	•	在run()方法中，线程首先获取lockA的锁（通过synchronized (lockA)），然后在持有lockA锁的同时尝试获取lockB的锁（通过synchronized (lockB)）。
 * 	•	在线程持有lockA的锁时，由于使用了Thread.sleep(1000)，线程会暂时休眠1秒钟，然后继续执行。
 * 	2.	DeadLockDemo 类：
 * 	•	这个类的main方法创建了两个线程，分别以"AAA"和"BBB"为名字。
 * 	•	线程"AAA"尝试先获取lockA，再获取lockB。
 * 	•	线程"BBB"尝试先获取lockB，再获取lockA。
 *
 * 死锁发生的原因
 *
 * 	1.	线程AAA的执行流程：
 * 	•	线程"AAA"首先获取了lockA的锁，然后休眠1秒钟。由于它还没有释放lockA，所以任何其他线程都无法获得lockA。
 * 	•	在休眠结束后，"AAA"线程尝试获取lockB的锁。
 * 	2.	线程BBB的执行流程：
 * 	•	与此同时，线程"BBB"首先获取了lockB的锁，然后同样休眠1秒钟。
 * 	•	在休眠结束后，"BBB"线程尝试获取lockA的锁。
 * 	3.	死锁的形成：
 * 	•	当线程"AAA"在尝试获取lockB时，lockB已经被线程"BBB"持有，而线程"BBB"正在等待线程"AAA"释放lockA。
 * 	•	由于lockA和lockB都无法被同时持有，两个线程陷入了无限等待的状态——这就是死锁。
 *
 * 死锁的特征
 *
 * 	•	互斥：线程对资源的访问是互斥的，即同一时间只有一个线程可以访问某个资源。
 * 	•	持有并等待：一个线程已经持有一个资源并且在等待另外一个资源，而该资源被其他线程持有。
 * 	•	不剥夺：已经分配给线程的资源不能被其他线程强制剥夺，线程只能主动释放它占有的资源。
 * 	•	循环等待：存在一个循环等待链，链中的每个线程都等待下一个线程所持有的资源。
 *
 * 解决方法
 * 	1.	避免循环等待：
 * 	•	通过给锁对象设置一个固定的获取顺序，确保线程按相同的顺序获取锁。比如，两个线程都先尝试获取lockA，再获取lockB。
 * 	2.	使用tryLock：
 * 	•	使用ReentrantLock替代synchronized，并使用tryLock尝试获取锁。如果无法获取锁，可以选择在一段时间后重试，或者放弃获取锁，避免死锁。
 * 	3.	资源分配的预先请求：
 * 	•	要求线程在开始执行之前一次性申请所有需要的资源，如果无法全部申请，则放弃并释放已经申请的资源。
 *
 * 	在生产环境中，如何去排查死锁
 *  1. 首先 jsp -l 查询进程号
 *  2. jstack  进程号
 *
 *  Found one Java-level deadlock:
 * =============================
 * "BBB":
 *   waiting to lock monitor 0x00007fe4888240c8 (object 0x000000076aee1c20, a java.lang.String),
 *   which is held by "AAA"
 * "AAA":
 *   waiting to lock monitor 0x00007fe4888268a8 (object 0x000000076aee1c58, a java.lang.String),
 *   which is held by "BBB"
 *
 * Java stack information for the threads listed above:
 * ===================================================
 * "BBB":
 *         at com.axon.java.stack.juc.threadpool.MyDeadLock.run(DeadLockDemo.java:27)
 *         - waiting to lock <0x000000076aee1c20> (a java.lang.String)
 *         - locked <0x000000076aee1c58> (a java.lang.String)
 *         at java.lang.Thread.run(Thread.java:748)
 * "AAA":
 *         at com.axon.java.stack.juc.threadpool.MyDeadLock.run(DeadLockDemo.java:27)
 *         - waiting to lock <0x000000076aee1c58> (a java.lang.String)
 *         - locked <0x000000076aee1c20> (a java.lang.String)
 *         at java.lang.Thread.run(Thread.java:748)
 *
 * Found 1 deadlock.
 *
 *
 */
public class DeadLockDemo {

    public static void main(String[] args) {

        new Thread(new MyDeadLock("lockA", "LOCKB"),"AAA").start();

        new Thread(new MyDeadLock("LOCKB", "lockA"),"BBB").start();

        System.out.println("运行结束");

    }
}
