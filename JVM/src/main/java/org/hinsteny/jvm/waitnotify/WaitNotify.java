package org.hinsteny.jvm.waitnotify;

/**
 * 测试wait和notify进出锁的顺序
 *
 * @author Hinsteny
 * @version $ID: WaitNotify 2018-04-25 16:40 All rights reserved.$
 */
public class WaitNotify extends Parent {

    public static void main(String[] args) {
        final Integer lock = new Integer(1);
        // wait thread
        Thread wait = buildWaitThread(lock, buildSleepConsumer(3000));
        wait.start();
        // notify thread
        Thread notify = buildNotifyThread(lock, buildSleepConsumer(3000));
        notify.start();
    }

}
