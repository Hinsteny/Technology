package org.hisoka.kafka.client.producer;

import org.hisoka.trash.KafkaProperties;

/**
 * @desc
 * @author hinsteny
 * @date 2017-06-06
 * @copyright: 2017 All rights reserved.
 */
public class ProducerExample {

    public static void main(String[] args)  {
        sendMessageOntTime(args);
    }

    private static void sendMessageOntTime(String[] args) {
        Producer producer = new Producer(KafkaProperties.TOPIC, true);
        new Thread(producer).start();
    }
}
