package com.axon.java.stack.juc.threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class ThreadLocalMyData {


    ThreadLocal<Integer> transactionLocal = ThreadLocal.withInitial(() -> 0);

    public void addTransactionLocal() {
        transactionLocal.set(transactionLocal.get() + 1);
    }

}


public class ThreadLocalDemo2 {

    public static void main(String[] args) throws InterruptedException {
        errorThreadLocalDemo();
        System.out.println("正常的线程开始运行");
        okThreadLocalDemo();
    }

    /**
     * •1. 问题: 线程池中的线程是复用的，而ThreadLocal是基于线程来存储数据的。因此，如果不手动清理，ThreadLocal中的数据可能会被复用，导致在后续任务中出现不正确的结果。
     * •解决方案: 在任务执行完成后，应该调用threadLocalMyData.transactionLocal.remove()来清除ThreadLocal中的数据。
     * 2. 线程池的配置
     * <p>
     * •	问题: Executors.newFixedThreadPool(3) 这个方法创建的线程池没有明确指定队列的大小，并且默认使用无界队列（LinkedBlockingQueue），这可能会导致资源耗尽的问题，尤其在任务非常多的情况下。
     * •	解决方案: 考虑使用ThreadPoolExecutor来指定线程池的参数，如核心线程数、最大线程数、队列大小、拒绝策略等。
     * <p>
     * 3. 资源释放
     * <p>
     * •	问题: 代码中虽然调用了executorService.shutdown()，但如果提交的任务执行时间过长或者出现异常，主线程并不会等待所有任务结束后再退出。
     * •	解决方案: 使用awaitTermination来确保线程池中所有任务都完成后再结束。
     *
     * @throws InterruptedException
     */
    private static void errorThreadLocalDemo() throws InterruptedException {
        ThreadLocalMyData threadLocalMyData = new ThreadLocalMyData();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        try {
            for (int i = 0; i < 10; i++) {
                executorService.submit(() -> {
                    try {
                        int beforeInt = threadLocalMyData.transactionLocal.get();
                        threadLocalMyData.addTransactionLocal();
                        int afterInt = threadLocalMyData.transactionLocal.get();

                        System.out.println(Thread.currentThread().getName() + "当前获取前的值是" + beforeInt + ",自增后的值是" + afterInt);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        Thread.sleep(3000);
        System.out.println("运行结束");
    }

    private static void okThreadLocalDemo() throws InterruptedException {
        ThreadLocalMyData threadLocalMyData = new ThreadLocalMyData();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        try {
            for (int i = 0; i < 10; i++) {
                executorService.submit(() -> {
                    try {
                        int beforeInt = threadLocalMyData.transactionLocal.get();
                        threadLocalMyData.addTransactionLocal();
                        int afterInt = threadLocalMyData.transactionLocal.get();

                        System.out.println(Thread.currentThread().getName() + " 当前获取前的值是 " + beforeInt + ", 自增后的值是 " + afterInt);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        // 重要：清理ThreadLocal，防止内存泄漏
                        threadLocalMyData.transactionLocal.remove();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
            // 等待线程池中的任务全部执行完毕
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        }
        System.out.println("运行结束");

    }
}
