package org.hisoka.BarriersQueue;

import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/3/21
 * @copyright: 2016 All rights reserved.
 */
public class BarriersTest {

    protected static final Logger logger = LoggerFactory.getLogger(BarriersTest.class);

    public static void main(String args[]) {
        barrierTest(args);
    }

    public static void barrierTest(String args[]) {
        Barrier b = new Barrier(args[1], "/b1", new Integer(args[2]));
        try {
            boolean flag = b.enter();
            logger.info("Entered barrier: " + args[2]);
            if (!flag) logger.info("Error when entering the barrier");
        } catch (KeeperException e) {

        } catch (InterruptedException e) {

        }

        // Generate random integer
        Random rand = new Random();
        int r = rand.nextInt(100);
        // Loop for rand iterations
        for (int i = 0; i < r; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }
        try {
            b.leave();
        } catch (KeeperException e) {

        } catch (InterruptedException e) {

        }
        logger.info("Left barrier");
    }
}
