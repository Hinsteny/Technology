package org.hisoka.log.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * @Auther hinsteny
 * @Desc
 * @Date 2017-06-06
 * @copyright: 2017 All rights reserved.
 */
public class ProduceLogInfo {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private volatile boolean flag = true;
    private Random random = new Random(LocalDateTime.now().getSecond());

    private void produce(){
        while (flag){
            switch (random.nextInt(5)){
                case 1: {
                    logger.trace("Log Trace, now is {}", LocalDateTime.now());
                    break;
                }
                case 2: {
                    logger.debug("Log Debug, now is {}", LocalDateTime.now());
                    break;
                }
                case 3: {
                    logger.info("Log Info, now is {}", LocalDateTime.now());
                    break;
                }
                case 4: {
                    logger.warn("Log Warn, now is {}", LocalDateTime.now());
                    break;
                }
                case 5: {
                    logger.error("Log Error, now is {}", LocalDateTime.now());
                    break;
                }
                default: {
                    logger.trace("Log Fatal, now is {}", LocalDateTime.now());
                }

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args){
        ProduceLogInfo produce = new ProduceLogInfo();
        produce.produce();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        flag = false;
    }
}

