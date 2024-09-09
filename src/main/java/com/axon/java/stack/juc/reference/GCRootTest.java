package com.axon.java.stack.juc.reference;

/**
 * GC Root（Garbage Collection Root） 是在 Java 垃圾回收机制中用来作为对象存活判定的起点。GC 会从这些根节点开始遍历对象图，所有从这些根节点可达的对象都是存活的，而不可达的对象则会被判定为垃圾，最终由垃圾回收器回收。
 *
 * 常见的 GC Root 对象：
 *
 * 	1.	栈帧中的局部变量：当前线程栈帧中的局部变量和输入参数。
 * 	2.	静态变量：所有类的静态变量（存在于方法区中）。
 * 	3.	常量：所有常量池中的常量对象。
 * 	4.	JNI引用：由 JNI 保持的本地代码中引用的对象。
 *
 * 案例场景：
 *
 * 假设有一个场景，开发中我们在处理网络连接时，定义了一个全局的静态对象来管理连接。由于这个对象是静态的，它的引用就会成为 GC Root。
 */
public class GCRootTest {

   /* // 静态对象：作为 GC Root 存在
    private static NetworkConnectionManager connectionManager;

    public static void main(String[] args) {
        connectionManager = new NetworkConnectionManager();

        // 局部变量：也可以作为 GC Root 存在
        Object localObject = new Object();

        // 连接管理操作
        connectionManager.openConnection();

        // 此时 connectionManager 和 localObject 都是 GC Root
        // 连接对象和 localObject 均不会被回收
        System.gc(); // 手动触发 GC
    }*/
}
