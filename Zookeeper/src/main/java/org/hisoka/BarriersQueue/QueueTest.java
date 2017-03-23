package org.hisoka.BarriersQueue;

import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hinsteny
 * @Describtion 基于队列的生产者与消费者实现
 * @date 2017/3/21
 * @copyright: 2016 All rights reserved.
 */
public class QueueTest {

    protected static final Logger logger = LoggerFactory.getLogger(QueueTest.class);

    public static void main(String args[]) {
        queueTest(args);
    }

    public static void queueTest(String args[]) {
        Queue q = new Queue(args[1], "/app1");
        logger.info("Input: " + args[1]);
        int i;
        Integer max = new Integer(args[2]);
        if (args[3].equals("p")) {
            logger.info("Producer");
            for (i = 0; i < max; i++)
                try {
                    q.produce(10 + i);
                } catch (KeeperException e) {

                } catch (InterruptedException e) {

                }
        } else {
            logger.info("Consumer");
            for (i = 0; i < max; i++) {
                try {
                    int r = q.consume();
                    logger.info("Item: " + r);
                } catch (KeeperException e) {
                    i--;
                } catch (InterruptedException e) {

                }
            }
        }
    }
}
