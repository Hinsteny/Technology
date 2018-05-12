package org.hinsteny.jvm.locksupport;

import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;

/**
 * @author Hinsteny
 * @version $ID: Parent 2018-05-07 19:26 All rights reserved.$
 */
public abstract class Parent {

    /**
     * build work content
     * @return
     */
    protected static Consumer<Thread> buildUnparkWork() {
        return (thread)->{
            Long threadId = thread.getId();
            System.out.println(String.format("%s thread do unpack", threadId));
            LockSupport.unpark(thread);
        };
    }

    /**
     * build one work thread
     *
     * @param sleepTime
     * @return
     */
    protected static Thread buildWorkThread(final Long sleepTime, final Thread work, final Consumer<Thread> action) {
        return new Thread(() -> {
            long id = Thread.currentThread().getId();
            System.out.println(String.format("%s thread do some sleep [%s]", id, sleepTime));
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("%s thread need to unpark thread [%s] lock", id, work.getId()));
            action.accept(work);
            System.out.println(String.format("%s thread unparked thread [%s] lock", id, work.getId()));
        });
    }

}
