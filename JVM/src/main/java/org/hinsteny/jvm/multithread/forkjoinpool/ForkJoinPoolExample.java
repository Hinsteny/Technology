package org.hinsteny.jvm.multithread.forkjoinpool;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author Hinsteny
 * @version $ID: ForkJoinPoolExample 2018-08-03 13:45 All rights reserved.$
 */
public class ForkJoinPoolExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        testForkJoinPool();
    }

    /**
     * 测试执行一个RecursiveTask
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void testForkJoinPool() throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<BigInteger> result = forkJoinPool.submit(new FactorialTask(0, 20));
        while (true) {
            if (result.isDone()) {
                System.out.println(String.format("计算任务结果为: %d", result.get()));
                break;
            }
        }
    }
}
