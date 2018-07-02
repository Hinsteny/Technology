package org.hinsteny.jvm.synchronize;

/**
 * @author Hinsteny
 * @version $ID: ThreadSafeSample 2018-06-30 10:57 All rights reserved.$
 */
public class ThreadSafeSample {

    private int sharedState;

    public static void main(String[] args) throws InterruptedException {
        testNonSafeAction();
        testSafeAction();
    }

    private static void testNonSafeAction() throws InterruptedException {
        ThreadSafeSample sample = new ThreadSafeSample();
        Thread a = new Thread(() -> {
           sample.nonSafeAction();
        });
        Thread b = new Thread(() -> {
            sample.nonSafeAction();
        });

        a.start();
        b.start();
        a.join();
        b.join();
    }

    private static void testSafeAction() throws InterruptedException {
        ThreadSafeSample sample = new ThreadSafeSample();
        Thread a = new Thread(() -> {
           sample.safeAction();
        });
        Thread b = new Thread(() -> {
            sample.safeAction();
        });

        a.start();
        b.start();
        a.join();
        b.join();
    }

    private void nonSafeAction() {
        while (sharedState < 100000) {
            int former = sharedState++;
            int latter = sharedState;
            if (former != latter - 1) {
                System.err.println(String.format("Observed data state, former is %s, latter is %s", former, latter));
                break;
            }
        }
        System.err.println(String.format("Observed data state thread [%s] is finished", Thread.currentThread().getName()));
    }

    private void safeAction() {
        while (sharedState < 100000) {
            synchronized (this) {
                int former = sharedState++;
                int latter = sharedState;
                if (former != latter - 1) {
                    System.err.println(String.format("Observed data state, former is %s, latter is %s", former, latter));
                }
            }
        }
        System.err.println(String.format("Observed data state thread [%s] is finished", Thread.currentThread().getName()));
    }

}
