package com.axon.java.stack.juc.sync;


/**
 *  代码块的原理
 * 找到该类的.class文件执行以下命令进行反编译
 * javap -c SyncBlockDemoTest.class
 *
 * Compiled from "SyncBlockDemoTest.java"
 * public class com.axon.java.stack.juc.sync.SyncBlockDemoTest {
 *   public com.axon.java.stack.juc.sync.SyncBlockDemoTest();
 *     Code:
 *        0: aload_0
 *        1: invokespecial #1                  // Method java/lang/Object."<init>":()V
 *        4: aload_0
 *        5: new           #2                  // class java/lang/Object
 *        8: dup
 *        9: invokespecial #1                  // Method java/lang/Object."<init>":()V
 *       12: putfield      #3                  // Field lock:Ljava/lang/Object;
 *       15: return
 *
 *   public void testBlock();
 *     Code:
 *        0: aload_0
 *        1: getfield      #3                  // Field lock:Ljava/lang/Object;
 *        4: dup
 *        5: astore_1
 *        6: monitorenter
 *        7: getstatic     #4                  // Field java/lang/System.out:Ljava/io/PrintStream;
 *       10: ldc           #5                  // String 我是代码块锁
 *       12: invokevirtual #6                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
 *       15: aload_1
 *       16: monitorexit
 *       17: goto          25
 *       20: astore_2
 *       21: aload_1
 *       22: monitorexit
 *       23: aload_2
 *       24: athrow
 *       25: return
 *     Exception table:
 *        from    to  target type
 *            7    17    20   any
 *           20    23    20   any
 *
 *   public static void main(java.lang.String[]);
 *     Code:
 *        0: new           #7                  // class com/atguigu/sprijngcloud/juc/sync/SyncBlockDemoTest
 *        3: dup
 *        4: invokespecial #8                  // Method "<init>":()V
 *        7: astore_1
 *        8: aload_1
 *        9: invokevirtual #9                  // Method testBlock:()V
 *       12: getstatic     #4                  // Field java/lang/System.out:Ljava/io/PrintStream;
 *       15: ldc           #10                 // String 代码块测试
 *       17: invokevirtual #6                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
 *       20: return
 * }
 *
 * 分析：
 * 	1.	monitorenter: 这是同步块的开始。当线程进入同步块时，它会尝试获取该对象的监视器锁。如果监视器锁已经被其他线程持有，当前线程将被阻塞，直到锁被释放。
 * 	2.	monitorexit: 这是同步块的结束。当线程退出同步块时，它会释放该对象的监视器锁，以便其他等待的线程可以获取锁。字节码中通常会出现两次monitorexit，一次是在正常执行路径结束时，另一次是在异常路径中，以确保无论是否抛出异常，锁都会被释放。
 * 	3.	Exception Table: 这个部分定义了在同步块内出现异常时的处理方式。如果在同步块内抛出了异常，控制流会跳转到相应的异常处理代码，该处理代码也包括释放锁的操作。
 *
 * 总结： 被锁的代码块中会包含
 * monitorenter : 当线程进入同步块时获取锁。
 *
 * monitorexit ： 线程退出同步块时释放锁。通常有两个monitorexit指令，分别用于正常退出和异常退出，以确保锁的释放。
 * 	•	异常表：如果在同步块中抛出异常，控制流会跳转到异常处理代码，确保在异常情况下也能释放锁。 这种机制确保了在任何情况下，锁都会被正确释放，避免出现死锁的情况。
 *
 *
 */
public class SyncBlockDemoTest {

    private Object lock = new Object();


    public void testBlock() {

        synchronized (lock) {
            System.out.println("我是代码块锁");
        }
    }

    public static void main(String[] args) {
        SyncBlockDemoTest test = new SyncBlockDemoTest();
        test.testBlock();
        System.out.println("代码块测试");
    }
}
