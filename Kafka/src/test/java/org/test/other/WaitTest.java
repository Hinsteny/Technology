package org.test.other;

/**
 * Created by hinsteny on 17-5-6.
 */
public class WaitTest {

    public synchronized void testWait(){//增加Synchronized关键字
        System.out.println("Start-----");
        try {
            wait(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("End-------");
    }

    public static void main(String[] args) {
        final WaitTest test = new WaitTest();
        new Thread(new Runnable() {

            public void run() {
                test.testWait();
            }
        }).start();
    }
}
