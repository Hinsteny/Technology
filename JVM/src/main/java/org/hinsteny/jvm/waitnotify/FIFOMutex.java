package org.hinsteny.jvm.waitnotify;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Hinsteny
 * @version FIFOMutex: 2019-07-25 18:13 All rights reserved.$
 */
public class FIFOMutex {

    private final AtomicBoolean locked = new AtomicBoolean(false);
    private final Queue<Thread> waiters = new ConcurrentLinkedQueue<>();

    public void lock() {
        boolean wasInterrupted = false;
        Thread current = Thread.currentThread();
        waiters.add(current);

        // Block while not first in queue or cannot acquire lock
        while (waiters.peek() != current ||
            !locked.compareAndSet(false, true)) {
            LockSupport.park(this);
            // ignore interrupts while waiting
            if (Thread.interrupted()) {
                wasInterrupted = true;
            }
        }

        waiters.remove();
        // reassert interrupt status on exit
        if (wasInterrupted) {
            current.interrupt();
        }
    }

    public void unlock() {
        locked.set(false);
        LockSupport.unpark(waiters.peek());
    }

    public static void main(String[] args) {
        final FIFOMutex fifoMutex = new FIFOMutex();
        Runnable task = () -> {
            System.out.println(Thread.currentThread().getId() + ":::" + "ask lock");
            fifoMutex.lock();
            System.out.println(Thread.currentThread().getId() + ":::" + "lock");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getId() + ":::" + "unlock");
            fifoMutex.unlock();
        };
        // one
        new Thread(task).start();
        // two
        new Thread(task).start();
    }

}
