package com.axon.java.stack.juc.threadlocal;

public class MyReferenceObject {

    /**
     * finalize 方法是 Java 中用于对象在被垃圾回收器回收之前执行清理操作的机制。finalize 是 Object 类中的一个方法，所有 Java 对象默认都继承了这个方法。其作用是允许开发者在对象被垃圾回收前进行一些资源释放或其他清理工作。
     * <p>
     * 作用
     * <p>
     * 1.	资源清理: finalize 方法通常用于释放非 Java 语言管理的资源，如文件句柄、数据库连接、网络连接等。虽然 Java 有垃圾回收机制（GC），但垃圾回收器只能回收堆内存中的对象，对于一些特殊资源，仍然需要手动释放。
     * 2.	调试和日志记录: 在调试过程中，finalize 方法可以用来记录哪些对象被垃圾回收，帮助开发者了解对象生命周期。
     * <p>
     * finalize 方法的工作机制
     * •	当垃圾回收器确定一个对象没有任何引用时，调用这个对象的 finalize 方法（如果存在）。
     * •	finalize 方法只会被调用一次，即使对象被复活（重新获得引用）也不会再被调用。
     * •	一旦 finalize 方法执行完毕，该对象将被真正地回收。
     * <p>
     * 注意事项
     * <p>
     * 1.	不可预测性: finalize 方法的执行时间是不确定的，甚至可能不会被调用。如果 JVM 在程序终止之前没有遇到内存不足的情况，可能永远不会触发垃圾回收，finalize 方法也不会被执行。
     * 2.	性能问题: 使用 finalize 会影响垃圾回收的性能。因为 JVM 需要额外的步骤来检查是否有 finalize 方法需要执行，这会增加开销。
     * 3.	对象复活问题: 在 finalize 方法中，如果重新将该对象的引用赋值给某个静态变量或对象的成员变量，该对象就不会被垃圾回收，从而“复活”。不过这种做法不推荐，因为它会使对象的生命周期更加难以管理。
     * 4.	替代方法: 从 Java 9 开始，finalize 被认为是不推荐使用的。建议使用 try-with-resources 或显式的资源管理方式，如 close() 方法来进行资源管理。也可以使用 java.lang.ref.Cleaner 类进行更灵活的资源清理。
     *
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("invoke  finalize  method!!");
    }
}
