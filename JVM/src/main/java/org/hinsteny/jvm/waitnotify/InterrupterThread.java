package org.hinsteny.jvm.waitnotify;

/**
 * 在wait前调用中断
 * @author Hinsteny
 * @version $ID: InterrupterThread 2018-04-26 16:37 All rights reserved.$
 */
public class InterrupterThread extends Parent {

    /**
     * Do some test
     * @param args
     */
    public static void main(String[] args) {
        // case one
        interrupteBeforeThreadStart();
        // case two
        interrupteBeforeThreadWait();
    }

    /**
     * 测试线程被创建后但是在start被中断, 那么等它start后进行wait, 中断不可恢复, 即无效
     */
    private static void interrupteBeforeThreadStart() {
        final Integer lock = new Integer(1);
        // wait thread
        Thread wait = buildWaitThread(lock, buildSleepConsumer(3000));
        // interrupt before start
        wait.interrupt();
        wait.start();

        // notify thread
        Thread notify = buildNotifyThread(lock, buildSleepConsumer(3000));
        notify.start();
    }

    /**
     * 测试线程被创建并start但是还未进入wait被中断, 那么等它wait, 会立即抛出中断异常, 相当于wait没有起到暂停作用
     */
    private static void interrupteBeforeThreadWait() {
        final Integer lock = new Integer(1);
        // wait thread
        Thread wait = buildWaitThread(lock, buildBusyConsumer(3000));
        wait.start();
        // interrupt after but before wait
        wait.interrupt();

        // notify thread
        Thread notify = buildNotifyThread(lock, buildSleepConsumer(3000));
        notify.start();
    }

}
