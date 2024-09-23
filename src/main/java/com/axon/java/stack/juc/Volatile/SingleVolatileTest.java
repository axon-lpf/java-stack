package com.axon.java.stack.juc.Volatile;


/**
 * 双检锁案例中，使用 volatile修饰， 保证线程之间的可见性。  防止在没有初始化成功后，得到一个地址的引用。
 * <p>
 * 场景案例： 公司来一个新同事，  声明工位A 给这个同事用， 但是这个同事还没来，大家都已经知道都是同事A的工位了。 要保证这个同事来，坐在这个座位上之后，才能正在的明确。
 *
 *
 * 代码说明：
 *
 * 	1.	volatile 关键字：用于防止指令重排，确保对象初始化的线程可见性。即保证在多线程环境下，instance 的修改对其他线程可见，避免读到部分构造的对象。
 * 	2.	第一次检查 (if (instance == null))：目的是为了避免不必要的同步开销。只有当 instance 为 null 时，才会进入同步代码块。
 * 	3.	同步块 (synchronized)：保证线程安全，只有一个线程能够初始化单例对象。
 * 	4.	第二次检查 (if (instance == null))：在同步块内再检查一次，防止多个线程在第一次检查时都通过，导致创建多个实例。
 *
 * 优点：
 *
 * 	•	线程安全：确保了在多线程环境中，只有一个线程能初始化单例对象。
 * 	•	高效性：双重检查锁减少了不必要的同步开销，在对象创建之后不再需要同步，提升了性能。
 *
 * 适用场景：
 *
 * 	•	当单例对象创建开销大，而又希望在多线程环境下延迟初始化单例对象时，使用双重检查锁可以有效提高效率。
 */
public class SingleVolatileTest {

    private static volatile SingleVolatileTest singleTest = null;

    private SingleVolatileTest() {
        System.out.println("我是构造函数啊。。。。。。。。");
    }

    /**
     * 官方版本
     *
     * @return
     */
    public static SingleVolatileTest getSingleTest() {
        if (singleTest == null) {
            synchronized (SingleVolatileTest.class) {
                if (singleTest == null) {
                     singleTest = new SingleVolatileTest();
                }
            }
        }
        return singleTest;
    }

    /**
     * 错误版本
     *
     * @return
     */
    public static SingleVolatileTest getSingleTes2() {
        if (singleTest == null) {
            return singleTest = new SingleVolatileTest();
        }
        return null;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                SingleVolatileTest.getSingleTest();

                //SingleTest.getSingleTes2();
            }, String.valueOf(i)).start();
        }
    }

}
