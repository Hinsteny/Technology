package org.hinsteny.jvm.waitnotify;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试wait和notify进出锁的顺序
 *
 * @author Hinsteny
 * @version $ID: WaitNotify 2018-04-25 16:40 All rights reserved.$
 */
public class WaitNotify extends Parent {

    public static void main(String[] args) {
//        final Integer lock = 1;
//        // wait thread
//        Thread wait = buildWaitThread(lock, buildSleepConsumer(3000));
//        wait.start();
//        // notify thread
//        Thread notify = buildNotifyThread(lock, buildSleepConsumer(3000));
//        notify.start();

        //
        muiltWork();
    }

    private static void muiltWork() {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                Object oject = new Object();
                synchronized(oject) {
                    int count = 0;
                    while (count++ < 5) {
                        try {
                            creaseCount();
                            oject.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    oject.notify();
                }
            }).start();
        }
    }

    private static AtomicInteger integer = new AtomicInteger();
    private static void creaseCount() {
        int andIncrement = integer.getAndIncrement();
        System.out.println(Thread.currentThread().getId() + ":" + andIncrement);
    }
}
