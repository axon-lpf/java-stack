package com.axon.java.stack.juc.Volatile;


/**
 *
 * volatile为啥能够禁止指令重排序， 因为底层使用内存屏障，
 * 粗略的分读屏障 、写屏障。 细分读写屏障、写读屏障、 读读屏障、写写屏障
 *  * LoadStore、 StoreLoad、 LoadLoad、StoreStore 屏障
 *
 * StoreStore： 在每一个Volatile写操作之前插入一个StoreStore屏障
 *
 * StoreLoad： 在每一个Volatile写操作之后插入一个StoreLoad屏障
 *
 * LoadLoad:   在每一个Volatile读操作之后插入一个LoadLoad屏障
 *
 * LoadStore: 在每一个Volatile读操作之后插入一个LoadStore屏障
 *
 *
 * 总结： volatile写之前的操作，都禁止重排序到  volatile之后
 *       volatile读之后的操作，都禁止重排序到  volatile之前
 *       volatile写之后的 volatile读，禁止重排序。
 *
 *
 *
 * 2.被volatile修饰的变量，并编译成字节码后， 会自动打上ACC_VOLATILE, 那JVM调用时，遇到这种标识，会自动加入对应的内存屏障
 *
 *
 * Volatile 使用场景
 *  1.单一赋值是可以， 但是不能用到运算符赋值等， 如i++
 *  2.状态标志，判断业务是否结束  参见：VolatileTestDemo02
 *  3.开销较低读、写策略  参见： VolatileTestDemo04
 *  4.DCL 双端见检锁   参见：SingleVolatileTest
 *
 *  什么是内存屏障？
 *      内存屏障是一种屏障指令，他使得CPU或编译器对屏障指令的前和后所发出的内存操作执行一个排序的约束， 也交内存栅栏、或栅栏指令。
 *
 *
 * chatGPT总结：
 *
 * 优化后的总结：
 *
 * 	1.	内存屏障的作用：
 * 	•	volatile 变量通过内存屏障来禁止指令重排序，确保可见性和有序性。
 * 	•	StoreStore 屏障：在每一个 volatile 写操作之前插入，确保前面的普通写操作不会被重排序到 volatile 写之后。
 * 	•	StoreLoad 屏障：在每一个 volatile 写操作之后插入，防止 volatile 写后的读操作与其他 volatile 变量的读写操作重排序。
 * 	•	LoadLoad 屏障：在每一个 volatile 读操作之后插入，确保后续的读操作不会被重排序到 volatile 读之前。
 * 	•	LoadStore 屏障：在每一个 volatile 读操作之后插入，防止后续的写操作被重排序到 volatile 读之前。
 * 	2.	volatile写操作和读操作的约束：
 * 	•	volatile 写之前的操作（普通写），不能重排序到 volatile 写之后。
 * 	•	volatile 读之后的操作（普通读），不能重排序到 volatile 读之前。
 * 	•	volatile 写之后的 volatile 读，不能重排序。
 * 	3.	字节码标识：
 * 	•	被 volatile 修饰的变量在编译后会打上 ACC_VOLATILE 标识，JVM 在遇到此标识时，会自动在相应位置加入内存屏障，以确保指令的有序执行。
 * 	4.	volatile的使用场景：
 * 	•	单一赋值操作是安全的，但在涉及到运算符的赋值（如 i++）时，volatile 并不能保证线程安全。
 * 	•	适用于状态标志，例如判断某个任务是否完成。
 * 	•	适用于低开销的读写策略。
 * 	•	用于实现双重检查锁（DCL），确保线程安全的单例模式。
 *
 * 额外优化建议：
 *
 * 	•	对于 volatile 的使用，应注意其局限性，例如它不能保证复合操作（如递增）的原子性。如果需要保证操作的原子性，应该使用 synchronized 或 Atomic 类。
 *
 *
 *
 *
 *
 */
class MyVolatileTest03 {

    int i = 0;

    volatile boolean flag = false;

    public void wirter() {
        i = 2;
        //被volatile修饰后 加入 StoreStore屏障  禁止处理器把上面的普通写 与volatile 写重排序
        flag = true;
        //加入StoreLoad屏障  禁止处理器把volatile写与下面的可能普通读写重排序。
    }

    public void read() {
        if (flag) {
            //加入 LoadLoad屏障  禁止处理器把上面的 volatile 读与下面的 普通读重排序
            //加入 LoadStore屏障  禁止处理器把上面的 volatile读与下面普通读写重排序
            System.out.println("-----i" + i);
        }
    }
}


public class VolatileTestDemo03 {


    public static void main(String[] args) {

    }

}
