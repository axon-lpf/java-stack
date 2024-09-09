package com.axon.java.stack.juc.threadpool;


import java.util.concurrent.*;

/**
 * 线程池的相关使用案例
 * <p>
 * ThreadPool 的线程池 底层都市用  TheadPollExecutor
 * <p>
 * ExecutorService executorService1 = Executors.newFixedThreadPool(10);  // 固定数量的线程池
 * 描述：创建一个固定大小的线程池，线程池中的线程数量始终为 nThreads。
 * •	特点：
 * •	固定数量的线程：线程池中始终保持固定数量的线程，任务提交后会被放入队列，等待空闲线程执行。
 * •	适用场景：适合处理稳定且长期运行的任务，确保线程数量的上限，防止系统资源耗尽。
 * •	资源管理：不会无限制地创建线程，有助于系统资源的管理和控制。
 * <p>
 * <p>
 * ExecutorService executorService2 = Executors.newSingleThreadExecutor();     //单个线程
 * <p>
 * 描述：创建一个单线程的线程池，确保所有任务在同一个线程中按顺序执行。
 * •	特点：
 * •	单一线程：线程池中始终只有一个线程，所有任务按顺序执行，保证任务的执行顺序。
 * •	适用场景：适合需要串行执行任务的场景，如日志记录、顺序任务处理等。
 * •	故障恢复：如果线程意外终止，会创建一个新的线程继续执行任务，保证线程池的稳定性。
 * <p>
 * ExecutorService executorService3 = Executors.newCachedThreadPool(); // 没有任何限制的
 * <p>
 * 描述：创建一个可根据需要创建新线程的线程池，但在以前构造的线程可用时将重用它们。
 * •	特点：
 * •	灵活扩展：线程池大小没有限制，可以根据需要动态创建新线程。
 * •	线程复用：空闲线程会被回收和复用，如果线程在60秒内没有任务执行，会被终止并从池中移除。
 * •	适用场景：适合大量短期任务或负载较轻的任务，能快速响应大量请求。
 * •	资源消耗：如果任务提交速率超过处理能力，可能导致线程大量创建，增加资源消耗。
 * <p>
 * <p>
 * 总结
 * <p>
 * •	newFixedThreadPool：固定数量的线程，适合长期且稳定的任务。
 * •	newSingleThreadExecutor：单线程，保证任务顺序执行，适合需要按顺序执行的任务。
 * •	newCachedThreadPool：根据需要动态创建线程，适合短期任务和负载不稳定的任务。
 * <p>
 * 选择合适的线程池类型取决于具体的应用场景和需求。在实际应用中，合理配置线程池的大小和类型，可以有效提高系统的性能和稳定性。
 * <p>
 * /**
 * 在实际的开发过程中不允许使用Executors 去创建线程池的， 必须要显示的手动去创建线程池
 * <p>
 * 参数说明，
 * 1.总共七个参数
 * <p>
 * <p>
 * corePoolSize – 当前正在工作的核心线程数， 这里的数量会占用maximumPoolSize的数量，
 * maximumPoolSize – 最大的工作核心线程数，相当于总的核心线程数
 * keepAliveTime –  线程的存活时间，  比如 总的核心线程数是5，当前工作的核心线程数是2, 突然请求量暴增， 队列打满之后，启动另外3个核销线程工作， 但是过一段时间后，请求量下去了，另外三个核心线程空闲， 这个时间就是指核心线程数空闲多久之后，给释放掉。
 * unit –  存活单位， 秒、毫秒
 * workQueue –  等待队列
 * handler –  拒绝策略 ，  当等待队列和工作的核心线程数满之后， 采用拒绝策略
 * <p>
 * 场景： 银行办办理业务
 * 1.总共5个窗口，今天周末， 只有两个窗口在有工作人员办理。
    银行有5个窗口，通常情况下只开两个窗口处理业务。
 •	如果排队人数超过10人，银行会开启另外3个窗口，这些窗口会先处理排队中的人，而不是直接处理新来的人。
 •	如果排队人数继续增加，超过银行的处理能力（例如超过20人），银行将拒绝服务新来的人，触发拒绝策略。
 * <p>
 * maximumPoolSize 如何去设置呢？
 * 分两种情况：
 * IO密集型：访问数据库、redis、文件、网络等是属于io密集型
 * maximumPoolSize=服务器核心数/(1-阻塞系数)  阻塞系数的值一般是0.8-到0.9之间
 * CPU密集型： 执行大量运算的则是cpu密集型
 * maximumPoolSize=服务器核心数+1
 *
 *
 * chatgpt 参数推荐的方案
 *  在配置线程池时，corePoolSize、maximumPoolSize、workQueue、keepAliveTime 的选择取决于任务的性质（CPU密集型或I/O密集型）、系统资源、以及任务的负载情况。以下是一些配置的指导原则，可以帮助你达到最佳性能：
 *
 * 1. corePoolSize（核心线程数）
 * 	•	CPU密集型任务：如果任务主要是CPU计算密集型（如复杂计算、大量循环等），一般建议将 corePoolSize 设置为服务器可用核心数（Runtime.getRuntime().availableProcessors()）的数量或稍微加1。例如，服务器有4个核心，可以设置 corePoolSize 为4或5。
 * 	•	I/O密集型任务：如果任务主要涉及I/O操作（如数据库查询、文件读写、网络通信等），corePoolSize 可以设置为核心数的2倍或更多，因为I/O操作会使线程阻塞，更多的线程可以充分利用CPU的空闲时间。
 *
 * 2. maximumPoolSize（最大线程数）
 *
 * 	•	CPU密集型任务：最大线程数一般设置为 corePoolSize + 1，因为额外的线程对CPU密集型任务的效率提升有限。
 * 	•	I/O密集型任务：最大线程数可以设置为 corePoolSize 的2倍或3倍，甚至更高，具体取决于I/O操作的阻塞程度。通常可以设置为 corePoolSize 的4到5倍。
 *
 * 3. workQueue（等待队列）
 *
 * 	•	任务数量和处理速度：如果任务数量多且处理速度较快，可以使用较大的队列（如 LinkedBlockingQueue）。这允许更多任务排队等待，而不是立即启动新的线程。
 * 	•	固定任务数量：如果任务数量是固定的，并且不希望消耗太多内存，可以使用 ArrayBlockingQueue 并设置合理的大小。
 * 	•	建议：如果 maximumPoolSize 很大，workQueue 可以设置为较小的值，以确保高并发下线程池能够处理任务。如果希望队列能够缓解压力，队列大小应设置为可以接受的任务积压数。
 *
 * 4. keepAliveTime（线程存活时间）
 *
 * 	•	短期突发任务：如果任务是短期的突发性负载，keepAliveTime 可以设置得较短（如30秒至1分钟），这样当负载消失时，线程池可以快速回收多余的线程。
 * 	•	长期负载任务：如果任务负载长期存在，可以将 keepAliveTime 设置得较长，以避免频繁的线程创建和销毁带来的开销。
 *
 * 5. 拒绝策略（handler）
 *
 * 	•	任务不能丢失：选择 CallerRunsPolicy，当线程池和队列都满时，由调用线程执行任务，减少任务丢失的可能性。
 * 	•	允许任务丢弃：如果允许丢弃部分任务，可以选择 DiscardPolicy 或 DiscardOldestPolicy。
 *
 *
 */
public class PollTest {

    public static void main(String[] args) throws InterruptedException {

  /*      ExecutorService executorService1 = Executors.newFixedThreadPool(10);  // 固定数量的线程池
        ExecutorService executorService2 = Executors.newSingleThreadExecutor();     //单个线程
        ExecutorService executorService3 = Executors.newCachedThreadPool(); // 没有任何限制的
*/

        int coreSize = Runtime.getRuntime().availableProcessors();
        System.out.println("当前系统的核心数是" + coreSize);
        ExecutorService customer = new ThreadPoolExecutor(2, 2 * 2, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(3), new ThreadPoolExecutor.AbortPolicy());
        // 这里总共只能最大接受的请求数位7， 那多出的则被拒绝掉
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            customer.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "线程正在处理"+ finalI);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        // 以上的案例，多出的线程则会被使用拒绝策略拒绝掉， 拒绝策略可以重定义
        Thread.sleep(20000);
        System.out.println("处理完成");
    }
}
