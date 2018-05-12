package org.hinsteny.jvm.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.function.Function;

/**
 * @author Hinsteny
 * @version $ID: Parent 2018-05-01 15:06 All rights reserved.$
 */
public class Parent {

    private static Integer score = 0;

    /**
     * build work content
     * @return
     */
    protected static Function<Long, Boolean> buildWork() {
        return (threadId)->{
            boolean flag = score < 99 ? false : true;
            if (!flag) {
                ++score;
                System.out.println(String.format("%s thread add score to %s", threadId, score));
            }
            return flag;
        };
    }

    /**
     * build one work thread
     *
     * @param lock
     * @return
     */
    protected static Thread buildWorkThread(final Lock lock, final Function<Long, Boolean> action) {
        return new Thread(() -> {
            int count = 0;
            long id = Thread.currentThread().getId();
            boolean escape = action.apply(id);
            while (!escape) {
                System.out.println(String.format("%s thread need get lock", id));
                lock.lock();
                count++;
                escape = action.apply(id);
                System.out.println(String.format("%s thread obtained the lock, escape is: %s", id, escape));
                lock.unlock();
            }
            System.out.println(String.format("%s thread exit count is %s", id, count));
        });
    }

}
