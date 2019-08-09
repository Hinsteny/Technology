package org.hinsteny.jvm.multithread.cases;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Hinsteny
 * @version MuiltThreadConsumeNumber: 2019-08-09 16:06 All rights reserved.$
 */
public class MuiltThreadConsumeNumber {

    public static void main(String[] args) {
        consumeNumber(3, 100);
    }

    private static void consumeNumber(int threadNumber, int end) {
        AtomicInteger number = new AtomicInteger(0);
        ExecutorService workGroup = new ThreadPoolExecutor(threadNumber, threadNumber, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        for (int i = 0; i < threadNumber; i++) {
            workGroup.submit(new NumberConsumeWork(threadNumber, i, end, number));
        }
    }

    static class NumberConsumeWork implements Runnable {

        private int threadCount;

        private int threadId;

        private int maxNumber;

        private AtomicInteger number;

        public NumberConsumeWork(int threadCount, int threadId, int maxNumber, AtomicInteger number) {
            this.threadCount = threadCount;
            this.threadId = threadId;
            this.maxNumber = maxNumber;
            this.number = number;
        }

        @Override
        public void run() {
            while (maxNumber >=  number.get()) {
                int current = number.get();
                if (current % threadCount == threadId) {
                    System.out.println(String.format("thread%d: %d", threadId, current));
                    number.getAndIncrement();
                }
            }
        }
    }

}
