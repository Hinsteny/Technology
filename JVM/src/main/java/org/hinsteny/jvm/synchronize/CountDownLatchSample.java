package org.hinsteny.jvm.synchronize;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author Hinsteny
 * @version CountDownLatchSample: CountDownLatchSample 2019-06-28 11:05 All rights reserved.$
 */
public class CountDownLatchSample {

    public static void main(String[] args) throws InterruptedException {
//        synchronizeSubTask();
        synchronizeSubTaskMuiltAwait();
    }

    private static void synchronizeSubTask() throws InterruptedException {
        final CountDownLatch count = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
              System.out.println("subTask start run");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count.countDown();
                System.out.println("subTask finished run");
            }).start();
        }
        System.out.println("Main task do wait...");
        count.await();
    }

    private static void synchronizeSubTaskMuiltAwait() throws InterruptedException {
        final CountDownLatch count = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                System.out.println("subTask start run");
                try {
                    Thread.sleep(new Random().nextInt( 10000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count.countDown();
                System.out.println("subTask finished run");
            }).start();
        }
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                System.out.println("Await subTask start run");
                try {
                    count.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count.countDown();
                System.out.println("Await subTask finished run");
            }).start();
        }
//        System.out.println("Main task do wait...");
//        count.await();
        for (;;) {
            System.out.println("Main task do wait...");
            Thread.sleep(3000);
        }
    }


}
