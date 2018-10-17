package org.hinsteny.jvm.multithread.executors;

import java.util.concurrent.*;

/**
 *
 * Executors examples
 * @author Hinsteny
 * @version $ID: ExecutorsExample 2018-09-17 23:30 All rights reserved.$
 */
public class ExecutorsExample {

    /**
     * 测试方法
     *
     * @param args
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        testSingleThreadExecutor(10);
        testnewSingleThreadScheduledExecutor(10);
        testFixedThreadPool(12);
        testCachedThreadPool(30);
        testWorkStealingPool(100000);
    }

    /**
     * 创建只有一个执行任务线程的线程池, 所有丢进线程池的任务会在一个无限缓存队列中排序, 然后依次按序执行;
     *
     * @param count
     */
    private static void testSingleThreadExecutor(int count) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int x = 0; x < count; x++) {
            final int taskId = x + 1;
            executorService.submit(() ->
                System.out.println(String.format("Task [%s] execute by thread [%s]", taskId, Thread.currentThread().getId()))
            );
        }
        executorService.shutdown();
    }

    /**
     * 创建只有一个执行任务线程的线程池, 所有丢进线程池的任务会在一个无限缓存队列中排序, 然后依次按序执行;
     *
     * @param count
     */
    private static void testnewSingleThreadScheduledExecutor(int count) throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(r -> new Thread(r));
        for (int x = 0; x < count; x++) {
            final int taskId = x + 1;
            ScheduledFuture<Long> future = scheduledExecutorService.schedule(() -> {
                    long start = System.currentTimeMillis();
                    System.out.println(String.format("Task [%s] execute by thread [%s]", taskId, Thread.currentThread().getId()));
                    return System.currentTimeMillis() - start;
                }
                , 3, TimeUnit.SECONDS);
            System.out.println(String.format("Task [%s] execute result [%s]", taskId, future.get()));
        }
        scheduledExecutorService.shutdown();
    }

    /**
     * 创建一个固定大小的线程池, 来新任务时, 有空闲线程对象则立即执行, 无空闲线程时则默认先放在队列中
     * @param count
     */
    private static void testFixedThreadPool(int count) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int x = 0; x < count; x++) {
            final int taskId = x + 1;
            executorService.submit(() ->
                System.out.println(String.format("Task [%s] execute by thread [%s]", taskId, Thread.currentThread().getId()))
            );
        }
        executorService.shutdown();
    }

    /**
     * 创建无大小限制的线程池, 来一个线程任务就创建一个新线程, 但是池中有可用的已回收线程对象则优先使用不创建新线程
     * @param count
     * @throws InterruptedException
     */
    private static void testCachedThreadPool(int count) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int x = 0; x < count; x++) {
            final int taskId = x + 1;
            executorService.submit(() ->
                System.out.println(String.format("Task [%s] execute by thread [%s]", taskId, Thread.currentThread().getId()))
            );
            if (x % 10 == 9) {
                Thread.sleep(1000);
            }
        }
        executorService.shutdown();
    }

    /**
     * 创建一个工作窃取的线程池
     * 针对一个提交到线程池的任务, 在它的执行过程中, 会分化裂变出多个可并行执行的子任务, 这时可以充分利用多处理器的功能, 并行计算, 最后汇总结果返回;
     */
    private static void testWorkStealingPool(int max) throws ExecutionException, InterruptedException {
        ForkJoinPool executorService = (ForkJoinPool) Executors.newWorkStealingPool(5);
        ForkJoinTask<Long> result = executorService.submit(new NumberSumTask(0, max, max / 100));
        while (true) {
            if (result.isDone()) {
                System.out.println(String.format("NumberSumTask start with: %d, end at: %d, result is: %d", 0, max, result.get()));
                break;
            }
        }
        executorService.shutdown();
    }

    /**
     * 二分法递归任务拆解的方式对一个有序自增1的整数序列求和的计算任务
     */
    private static class NumberSumTask extends RecursiveTask<Long>{

        private int start;

        private int end;

        private int threadHold;

        public NumberSumTask(int start, int end, int threadHold) {
            this.start = start;
            this.end = end;
            this.threadHold = threadHold;
        }

        @Override
        protected Long compute() {
            // 判断数值区间长度是否小于给定的单个线程计算量, 小于则直接计算, 否则继续拆解
            if (end - start < threadHold) {
                System.out.println(String.format("SubTask sum start with: %d, end at: %d, by thread: %d", start, end, Thread.currentThread().getId()));
                long sum = 0;
                for (; start < end + 1; start ++) {
                    sum += start;
                }
                return sum;
            } else {
                // 分割任务
                int middle = (start + end) / 2;
                NumberSumTask leftTask = new NumberSumTask(start, middle, threadHold);
                NumberSumTask rightTask = new NumberSumTask(middle, end, threadHold);
                // 异步执行 leftTask
                leftTask.fork();
                // 异步执行 rightTask
                rightTask.fork();
                // 阻塞，直到 leftTask 执行完毕返回结果
                Long leftResult = leftTask.join();
                // 阻塞，直到 rightTask 执行完毕返回结果
                Long rightResult = rightTask.join();
                // 合并结果
                return leftResult + rightResult;
            }
        }
    }

}
