package org.hinsteny.jvm.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

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
        multiThreadAcquireMutexLock(5, lock);
    }

    /**
     *
     * @param jobsNumber
     * @param lock
     */
    private static void multiThreadAcquireMutexLock(int jobsNumber, Lock lock) {
        while (jobsNumber-- > 0) {
            Function<Lock, Integer> function = buildWorkTaskWithLock(jobsNumber);
            new Thread(new JobThread(function, lock)).start();
        }
    }

}
