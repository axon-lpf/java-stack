package com.axon.java.stack.juc.sync;

/**
 * 同步方法的原理
 * * 找到该类的.class文件执行以下命令进行反编译
 * * javap -v SyncStaticDemoTest.class
 * <p>
 * 反编译后输出以下结果：
 * <p>
 *      public static synchronized void testSyncMethod();
 *     descriptor: ()V
 *     flags: ACC_PUBLIC, ACC_STATIC, ACC_SYNCHRONIZED
 *     Code:
 *       stack=2, locals=0, args_size=0
 *          0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
 *          3: ldc           #3                  // String 我是同步方法所
 *          5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
 *          8: return
 *       LineNumberTable:
 *         line 58: 0
 *         line 59: 8
 *
 *   public static void main(java.lang.String[]);
 *     descriptor: ([Ljava/lang/String;)V
 *     flags: ACC_PUBLIC, ACC_STATIC
 *     Code:
 *       stack=2, locals=1, args_size=1
 *          0: invokestatic  #5                  // Method testSyncMethod:()V
 *          3: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
 *          6: ldc           #6                  // String 同步方法所执行结束
 *          8: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
 *         11: return
 *       LineNumberTable:
 *         line 63: 0
 *         line 64: 3
 *         line 66: 11
 *       LocalVariableTable:
 *         Start  Length  Slot  Name   Signature
 *             0      12     0  args   [Ljava/lang/String;
 * }
 * SourceFile: "SyncStaticDemoTest.java"
 * <p>
 * 分析：
 *      使用 synchronized 修饰的静态方法，会在 flags 字段中标记 ACC_SYNCHRONIZED 和 ACC_STATIC。
 * 	•	ACC_SYNCHRONIZED 表示该方法是同步方法，JVM 会在调用此方法时自动处理锁的获取和释放。
 * 	•	对于静态同步方法，锁定的是类对象 (Class 对象)，而不是实例对象。
 *
 * 分析与总结：
 *
 * 	1.	synchronized 修饰符：当一个方法被 synchronized 修饰时，JVM 会在方法执行前自动获取对象的监视器锁（如果是实例方法）或者类的监视器锁（如果是静态方法），并在方法执行完毕后释放锁。
 * 	2.	ACC_SYNCHRONIZED 标志：在字节码文件中，synchronized 方法会通过 ACC_SYNCHRONIZED 标志来指示该方法是同步的。这意味着 JVM 在执行这个方法时，会隐式地获取和释放相应的锁。
 * 	3.	ACC_STATIC 标志：对于静态方法，ACC_STATIC 标志指示方法属于类而不是实例。因此，在静态同步方法中，锁定的对象是类的 Class 对象。
 * 	4.	JVM 的处理：当 JVM 发现一个方法有 ACC_SYNCHRONIZED 标志时，它会在调用方法前自动获取锁，并在方法结束时自动释放锁。这种锁的机制是隐式的，开发者无需显式地使用 monitorenter 和 monitorexit 指令。
 *
 */
public class SyncStaticDemoTest {

    public static synchronized void testSyncMethod() {
        System.out.println("我是静态同步方法锁");
    }


    public static void main(String[] args) {
        testSyncMethod();
        System.out.println("静态同步方法所执行结束");

    }
}
