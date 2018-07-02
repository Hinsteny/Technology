package org.hinsteny.jvm.commons;

import java.time.LocalDateTime;

/**
 * 一个运行在jvm中的应用程序, 当所有用户线程退出后, 守护线程也就退出了, 然后应用程序便会关闭结束
 * ：：只要程序中有一个用户线程, 应用程序就不会被jvm关闭结束;
 * ：：只要程序中只剩有一个守护线程, 应用程序就会被jvm关闭结束;
 *
 * @author Hinsteny
 * @version $ID: MyAPP 2018-06-30 13:17 All rights reserved.$
 */
public class MyAPP {

    public static void main(String[] args) {
        System.out.println("starting app main tread, am i daemon " + Thread.currentThread().isDaemon());
//        startDaemonThread();
        startUserThread();
        System.out.println("stop app main thread ");
//        System.exit(0);
    }

    private static void startDaemonThread() {
        Thread app = new Thread(() -> {
            System.out.println("I am daemon thread, i started");
            try {
                while (true) {
                    Thread.sleep(100000);
                    System.out.println(LocalDateTime.now());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("I am daemon thread, i am dead!!!");
        });
        app.setDaemon(true);
        app.start();
    }

    private static void startUserThread() {
        new Thread(() -> {
            System.out.println("I am user thread, i started");
            try {
                while (true) {
                    System.out.println(LocalDateTime.now());
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("I am user thread, i am dead!!!");
        }).start();
    }

}
