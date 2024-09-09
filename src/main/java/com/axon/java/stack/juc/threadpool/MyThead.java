package com.axon.java.stack.juc.threadpool;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class MyThreadRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("我是没有返回值的线程");
    }
}

class MyThreadCallable implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {

        System.out.println("我是有返回值的线程");
        return 10;
    }
}

/**
 * 1.	Runnable vs Callable
 * 	•	Runnable 接口的 run 方法没有返回值，不能抛出检查型异常。
 * 	•	Callable 接口的 call 方法可以有返回值，并且可以抛出检查型异常。
 * 	2.	FutureTask
 * 	•	FutureTask 实现了 Runnable 和 Future 接口，可以包装 Callable 或 Runnable 对象。
 * 	•	可以通过 FutureTask 获取 Callable 的返回值或检查任务是否完成。
 * 	3.	线程共享 FutureTask
 * 	•	当多个线程共享同一个 FutureTask 实例时，只有一个线程会执行 call 方法，其他线程会被阻塞直到任务完成。这可以防止重复执行相同的任务。
 *
 *
 *
 * 	在这段代码中，new Thread(futureTask).start(); 被调用了两次，但只会打印一次 “我是有返回值的线程”。这是因为 FutureTask 的设计保证了同一个 Callable 任务只会被执行一次，无论它被多少个线程启动。
 *
 * 详细解释
 *
 * 	1.	FutureTask 的执行机制：
 * 	•	FutureTask 实现了 Runnable 和 Future 接口，既可以作为 Runnable 被线程执行，又可以作为 Future 获取任务的结果。
 * 	•	当 FutureTask 被多个线程执行时，它会确保 Callable 的 call 方法只执行一次。
 * 	•	内部通过 CAS（Compare-And-Swap） 操作和状态控制来实现这种行为，保证任务执行的原子性。
 * 	2.	多线程启动同一个 FutureTask：
 * 	•	当你启动两个线程去执行同一个 FutureTask 时，第一个调用 start() 的线程会成功执行 call 方法，而第二个线程会发现任务已经被执行或者正在执行，因此不会再执行 call 方法。
 * 	3.	示例代码：
 */

public class MyThead {

    public static void main(String[] args) {

        // 因为 FutureTask 继承实现了  Runnable ， 并构造方法中Callable，所以这里使用FutureTask 包装一下 MyThreadCallable ，并传入到 new Thread中去
        FutureTask futureTask = new FutureTask(new MyThreadCallable());
        FutureTask futureTask2 = new FutureTask(new MyThreadCallable());


        new Thread(futureTask).start();
        new Thread(futureTask).start();
        new Thread(futureTask2).start();

        try {
            Object o = futureTask.get();
            System.out.println(o);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}
