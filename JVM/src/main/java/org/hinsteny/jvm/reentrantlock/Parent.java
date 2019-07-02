package org.hinsteny.jvm.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.function.Function;

/**
 * @author Hinsteny
 * @version $ID: Parent 2018-05-01 15:06 All rights reserved.$
 */
public class Parent {

    private static Integer score = 0;

    /**
     * build one work thread
     *
     * @param number job-number
     */
    protected static Function<Lock, Integer> buildWorkTaskWithLock(final int number) {
        return (lock) -> {
            long id = number;
            System.out.println(String.format("%s thread need get lock", id));
            lock.lock();
            try {
                System.out.println(String.format("%s thread obtained the lock", id));
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            System.out.println(String.format("%s thread exit", id));
            return number;
        };
    }

    /**
     * build one work thread
     *
     * @param action job
     * @return work task
     */
    protected static Function<Lock, Integer> buildWorkTaskWithTryLock(final Function<Long, Boolean> action) {
        return (lock) -> {
            long id = Thread.currentThread().getId();
            int count = 0;
            boolean escape = false;
            while (!escape) {
                System.out.println(String.format("%s thread need get lock", id));
                try {
                    lock.tryLock(3, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    System.out.println(e);
                    break;
                }
                count++;
                escape = action.apply(id);
                System.out.println(String.format("%s thread obtained the lock, escape is: %s", id, escape));
                lock.unlock();
            }
            System.out.println(String.format("%s thread exit count is %s", id, count));
            return count;
        };
    }

    protected static class JobThread implements Runnable {

        private Function<Lock, Integer> job;

        private Lock lock;

        public JobThread(Function<Lock, Integer> job, Lock lock) {
            this.job = job;
            this.lock = lock;
        }

        @Override
        public void run() {
            job.apply(lock);
        }
    }

}
