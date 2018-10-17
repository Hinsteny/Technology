package org.hinsteny.jvm.multithread.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Hinsteny
 * @version $ID: ThreadPoolExample 2018-08-02 20:21 All rights reserved.$
 */
public class ThreadPoolExample {

    /**
     * test method
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        newNormalThreadPoolExecutor();

        newThreadPoolWithLinkedBlockingDeque();
    }

    /**
     * 自定创建一个普通的线程池对象, 进行任务的提交执行
     */
    private static void newNormalThreadPoolExecutor() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), Executors.defaultThreadFactory(), new AbortPolicy());
        threadPoolExecutor.execute(() -> System.out.println("execute one task!"));
        Future<Integer> future = threadPoolExecutor.submit(() -> {
            System.out.println("submit and execute one task!");
            Thread.sleep(3000);
            return 1;
        });
        // Future<Integer> future.get() 此方法会阻塞, 直到任务执行完成产生返回结果
        System.out.println(String.format("submit task result is: %s!", future.get()));
        threadPoolExecutor.shutdown();
    }

    /**
     * 创建一个
     * 核心线程为1
     * 最大线程数2
     * 阻塞队列, 长度为2
     * 线程存活线程时间3s
     * 拒绝策略为抛异常
     * 单个任务执行耗时1s
     * 线程池的执行逻辑: 向线程池中提交任务, 当其中的工作线程达到最大线程池个数时, 任务就会被放到阻塞队列中, 当队列也满了时, 再提交任务, 就会执行我们指定的拒绝策略;
     *
     */
    private static void newThreadPoolWithLinkedBlockingDeque() throws InterruptedException {
        // 构建参数
        int corePoolSize = 1;
        int maxPollSize = 2;
        long keepAliveTime = 3;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue(2);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler abortPolicy = new AbortPolicy();
        // 构建线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPollSize, keepAliveTime, unit, workQueue, threadFactory, abortPolicy);
        // 任务执行结果统计
        final AtomicInteger total = new AtomicInteger(0);
        final AtomicInteger success = new AtomicInteger(0);
        final AtomicInteger failed = new AtomicInteger(0);
        // 提交任务
        for (int i = 1; i <= 12; i++) {
            final String taskId = String.valueOf(i);
            System.out.println(String.format("submit one task: %s ", taskId));
            try {
                total.getAndIncrement();
                threadPoolExecutor.execute(() -> {
                    System.out.println(String.format("==================================== execute task start: %s !", taskId));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    success.getAndIncrement();
                    System.out.println(String.format("+++++++++++++++++++++++++++++++++++ execute task finished: %s , success: %d!", taskId, success.get()));
                });
            } catch (Throwable e) {
                failed.getAndIncrement();
                new Throwable("Task: " + taskId + " executed exception", e).printStackTrace();
            }
            if (i % 5 == 0) {
                Thread.sleep(1000);
            }
        }
        threadPoolExecutor.shutdown();
        // 线程池关闭了, 但是其中的任务并没有都执行完成
//        while (!threadPoolExecutor.isShutdown()) {
        // 线程池关闭了, 并且其中的任务都全部执行完成啦
        while (!threadPoolExecutor.awaitTermination(1, TimeUnit.MINUTES)) {
            Thread.sleep(1000);
        }
        System.out.println(String.format("Submit tasks total: %d, executed success is: %d and exception is: %d !", total.get(), success.get(), failed.get()));
    }

}
