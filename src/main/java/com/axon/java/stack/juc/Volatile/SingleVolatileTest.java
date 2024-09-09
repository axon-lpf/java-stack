package com.axon.java.stack.juc.Volatile;


/**
 * 双检锁案例中，使用 volatile修饰， 保证线程之间的可见性。  防止在没有初始化成功后，得到一个地址的引用。
 * <p>
 * 场景案例： 公司来一个新同事，  声明工位A 给这个同事用， 但是这个同事还没来，大家都已经知道都是同事A的工位了。 要保证这个同事来，坐在这个座位上之后，才能正在的明确。
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
                    return singleTest = new SingleVolatileTest();
                }
            }
        }
        return null;
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
