package org.test.other;

/**
 * Created by hinsteny on 17-5-6.
 */
public class NotifyTest {

    public synchronized void testWait(){
        System.out.println(Thread.currentThread().getName() +" Start-----");
        try {
            this.wait(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() +" End-------");
        if (Thread.currentThread().getName().equals("Thread_0")){
            for (int i=0; i<10000; i++){
                if (i%100 ==0)
                    System.out.println("......" + i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final NotifyTest test = new NotifyTest();
        String threadName = "Thread_";
        for(int i=0;i<5;i++) {
            new Thread(new Runnable() {

                public void run() {
                    test.testWait();
                }
            }, threadName+i).start();
        }

        synchronized (test) {
            test.notify();
        }
        Thread.sleep(3000);
        System.out.println("-----------分割线-------------");

        synchronized (test) {
            test.notifyAll();
        }
    }
}