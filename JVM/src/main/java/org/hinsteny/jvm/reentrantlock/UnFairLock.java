package org.hinsteny.jvm.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * unfair lock example
 * @author Hinsteny
 * @version $ID: UnFairLock 2018-05-01 15:05 All rights reserved.$
 */
public class UnFairLock extends Parent{

    /**
     * test
     * @param args
     */
    public static void main(String[] args) {
        Lock lock = new ReentrantLock(false);
        Thread work_one = buildWorkThread(lock, buildWork());
        Thread work_two = buildWorkThread(lock, buildWork());
        Thread work_three = buildWorkThread(lock, buildWork());
        work_one.start();
        work_two.start();
        work_three.start();
    }

}
