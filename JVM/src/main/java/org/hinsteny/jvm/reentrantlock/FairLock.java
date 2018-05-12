package org.hinsteny.jvm.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * fair lock example
 * @author Hinsteny
 * @version $ID: FairLock 2018-05-01 15:04 All rights reserved.$
 */
public class FairLock extends Parent {

    /**
     * test
     * @param args
     */
    public static void main(String[] args) {
        Lock lock = new ReentrantLock(true);
        Thread work_one = buildWorkThread(lock, buildWork());
        Thread work_two = buildWorkThread(lock, buildWork());
        Thread work_three = buildWorkThread(lock, buildWork());
        work_one.start();
        work_two.start();
        work_three.start();
    }

}
