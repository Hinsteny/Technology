package org.hinsteny.jvm.waitnotify;

/**
 * 调用notify, 线程被唤醒的顺序呢
 *
 * @author Hinsteny
 * @version $ID: NotifyOrder 2018-04-26 19:33 All rights reserved.$
 */
public class NotifyOrder extends Parent {

    public static void main(String[] args) {
        // case one
        notifyCase();
        // case two
        notifyAllCase();
    }

    /**
     * 测试notify: FIFO, 先进入wait的线程, 第一个被唤醒
     */
    private static void notifyCase() {
        final Integer lock = new Integer(1);
        // one wait thread
        Thread one = buildWaitThread(lock, null);
        one.start();
        // two wait thread
        Thread two = buildWaitThread(lock, null);
        two.start();
        // notify thread
        Thread notify = buildNotifyThread(lock, buildSleepConsumer(3000));
        notify.start();
    }

    /**
     * 测试notifyAll: LIFO, 最后进入wait的, 最先被唤醒
     */
    private static void notifyAllCase() {
        final Integer lock = new Integer(1);
        // one wait thread
        Thread one = buildWaitThread(lock, null);
        one.start();
        // two wait thread
        Thread two = buildWaitThread(lock, null);
        two.start();
        // three wait thread
        Thread three = buildWaitThread(lock, null);
        three.start();
        Thread notify = buildNotifyAllThread(lock, buildSleepConsumer(10000));
        notify.start();
    }
}
