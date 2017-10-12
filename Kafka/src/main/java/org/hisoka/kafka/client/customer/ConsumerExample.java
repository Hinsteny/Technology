package org.hisoka.kafka.client.customer;

import org.hisoka.trash.KafkaProperties;

/**
 * @desc
 * @author hinsteny
 * @date 2017-06-06
 * @copyright: 2017 All rights reserved.
 */
public class ConsumerExample {


    public static void main(String[] args)  {
        reciveMessageOnTime(args);
    }

    private static void reciveMessageOnTime(String[] args) {
        Consumer producer = new Consumer(KafkaProperties.TOPIC, true);
        new Thread(producer).start();
    }
}
