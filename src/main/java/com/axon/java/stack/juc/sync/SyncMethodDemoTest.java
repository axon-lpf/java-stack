package com.axon.java.stack.juc.sync;

/**
 * 同步方法的原理
 *  * 找到该类的.class文件执行以下命令进行反编译
 *  * javap -v SyncMethodDemoTest.class
 *
 *  反编译后输出以下结果：
 *
 *    public synchronized void testSyncMethod();
 *     descriptor: ()V
 *     flags: ACC_PUBLIC, ACC_SYNCHRONIZED
 *     Code:
 *       stack=2, locals=1, args_size=1
 *          0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
 *          3: ldc           #3                  // String 我是同步方法所
 *          5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
 *          8: return
 *       LineNumberTable:
 *         line 13: 0
 *         line 14: 8
 *       LocalVariableTable:
 *         Start  Length  Slot  Name   Signature
 *             0       9     0  this   Lcom/atguigu/sprijngcloud/juc/sync/SyncMethodDemoTest;
 *
 *   public static void main(java.lang.String[]);
 *     descriptor: ([Ljava/lang/String;)V
 *     flags: ACC_PUBLIC, ACC_STATIC
 *     Code:
 *       stack=2, locals=2, args_size=1
 *          0: new           #5                  // class com/atguigu/sprijngcloud/juc/sync/SyncMethodDemoTest
 *          3: dup
 *          4: invokespecial #6                  // Method "<init>":()V
 *          7: astore_1
 *          8: aload_1
 *          9: invokevirtual #7                  // Method testSyncMethod:()V
 *         12: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
 *         15: ldc           #8                  // String 同步方法所执行结束
 *         17: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
 *         20: return
 *       LineNumberTable:
 *         line 18: 0
 *         line 19: 8
 *         line 20: 12
 *         line 22: 20
 *       LocalVariableTable:
 *         Start  Length  Slot  Name   Signature
 *             0      21     0  args   [Ljava/lang/String;
 *             8      13     1  test   Lcom/atguigu/sprijngcloud/juc/sync/SyncMethodDemoTest;
 * }
 * SourceFile: "SyncMethodDemoTest.java"
 *
 * 分析：  如果是同步方法，在会在flags上添加ACC_SYNCHRONIZED  表示 一个同步方法。
 *
 * 	1.	synchronized 关键字：当一个实例方法被 synchronized 修饰时，JVM 会在执行这个方法之前获取当前实例对象的锁，并在方法执行完毕后释放锁。这意味着多个线程不能同时执行该方法，必须按照顺序进行。
 * 	2.	ACC_SYNCHRONIZED 标志：
 * 	•	在字节码文件中，当一个方法被 synchronized 修饰时，会在 flags 字段中标记 ACC_SYNCHRONIZED。
 * 	•	ACC_SYNCHRONIZED 标志通知 JVM 在调用此方法时需要获取和释放相应的锁。
 * 	3.	实例同步方法：
 * 	•	对于实例方法，锁定的是当前实例 (this 对象)。
 * 	•	这意味着只有一个线程可以在同一时间执行这个实例的同步方法，其他线程必须等待，直到前一个线程完成该方法的执行。
 * 	4.	字节码分析：
 * 	•	getstatic #2: 获取 System.out 对象，表示获取标准输出流。
 * 	•	ldc #3: 加载字符串常量 “我是同步方法所”。
 * 	•	invokevirtual #4: 调用 println 方法进行打印输出。
 *
 */
public class SyncMethodDemoTest {

    public synchronized void testSyncMethod() {
        System.out.println("我是同步方法所");
    }


    public static void main(String[] args) {
        SyncMethodDemoTest test = new SyncMethodDemoTest();
        test.testSyncMethod();
        System.out.println("同步方法所执行结束");

    }
}
