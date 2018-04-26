package org.hinsteny.jvm.waitnotify;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import static java.time.temporal.ChronoField.MILLI_OF_DAY;

/**
 * @author Hinsteny
 * @version $ID: Parent 2018-04-26 17:56 All rights reserved.$
 */
public abstract class Parent {

    /**
     * 构建一个wait线程
     *
     * @param lock
     * @return
     */
    protected static Thread buildWaitThread(final Integer lock, final Consumer<Long> action) {
        return new Thread(() -> {
            long id = Thread.currentThread().getId();
            synchronized (lock) {
                System.out.println(String.format("%s Enter wait lock", id));
                try {
                    if (null != action) {
                        action.accept(id);
                    }
                    System.out.println(String.format("%s Enter wait block", id));
                    lock.wait();
                    System.out.println(String.format("%s Exit wait block", id));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(String.format("%s Exit wait lock", id));
            }
        });
    }

    /**
     * 构建一个notify线程
     *
     * @param lock
     * @return
     */
    protected static Thread buildNotifyThread(final Integer lock, final Consumer<Long> action) {
        return new Thread(() -> {
            long id = Thread.currentThread().getId();
            synchronized (lock) {
                System.out.println(String.format("%s Enter notify lock", id));
                lock.notify();
                if (null != action) {
                    action.accept(id);
                }
                System.out.println(String.format("%s Exit notify lock", id));
            }
        });
    }

    /**
     * 构建一个notifyAll线程
     *
     * @param lock
     * @return
     */
    protected static Thread buildNotifyAllThread(final Integer lock, final Consumer<Long> action) {
        return new Thread(() -> {
            long id = Thread.currentThread().getId();
            if (null != action) {
                action.accept(id);
            }
            synchronized (lock) {
                System.out.println(String.format("%s Enter notifyAll lock", id));
                lock.notifyAll();
                System.out.println(String.format("%s Exit notifyAll lock", id));
            }
        });
    }

    /**
     * 构建一个sleep指定时间的子任务
     *
     * @param sleepTime
     * @return
     */
    protected static Consumer<Long> buildSleepConsumer(long sleepTime) {
        return aLong -> {
            try {
                System.out.println(String.format("Thread [%s] will sleep %s millis", aLong, sleepTime));
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        };
    }

    /**
     * 构建一个忙碌指定时间的子任务
     *
     * @param sleepTime
     * @return
     */
    protected static Consumer<Long> buildBusyConsumer(long sleepTime) {
        return aLong -> {
            System.out.println(String.format("Thread [%s] will busy %s millis", aLong, sleepTime));
            int future = LocalDateTime.now().get(MILLI_OF_DAY) + (int) sleepTime;
            while (true) {
                if (future > LocalDateTime.now().get(MILLI_OF_DAY)) {
                    break;
                }
            }
        };
    }
}
