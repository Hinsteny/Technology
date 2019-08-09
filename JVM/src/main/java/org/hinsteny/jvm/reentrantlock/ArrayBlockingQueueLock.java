package org.hinsteny.jvm.reentrantlock;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Hinsteny
 * @version ArrayBlockingQueueLock: 2019-07-25 13:46 All rights reserved.$
 */
public class ArrayBlockingQueueLock {

    private static final BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(10);

    /**
     * test
     * @param args
     */
    public static void main(String[] args) {
        startTakeThread();
        startAddThread();
    }

    private static void startTakeThread() {
        new Thread(() -> {
            while (true) {
                try {
                    String item = blockingQueue.take();
                    System.out.println("Take item from queue:" + item);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void startAddThread() {
        new Thread(() -> {
            int i = 1;
            while (i++ > 0) {
                try {
                    boolean result = blockingQueue.offer(String.valueOf(i));
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
