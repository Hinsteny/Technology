package org.hinsteny.jvm.locksupport;

import java.util.concurrent.locks.LockSupport;

/**
 * @author Hinsteny
 * @version $ID: Lock 2018-05-07 19:10 All rights reserved.$
 */
public class Lock extends Parent {

    /**
     * test LockSupport example
     *
     * @param args
     */
    public static void main(String[] args) {
        lockByPark();

    }

    /**
     * use park/unpark to come true lock/unlock
     */
    private static void lockByPark() {
        Thread main = Thread.currentThread();
        Thread unpark = buildWorkThread(1000L, main, buildUnparkWork());
        unpark.start();
        System.out.println(String.format("%s thread wait park lock", main.getId()));
        LockSupport.park();
        System.out.println(String.format("%s thread got park lock", main.getId()));
    }
}
