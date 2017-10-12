package org.hisoka.kafka.client.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.hisoka.trash.KafkaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @desc
 * @author hinsteny
 * @date 2017-06-06
 * @copyright: 2017 All rights reserved.
 */
public class Producer implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final KafkaProducer<Integer, String> producer;
    private final String topic;
    private final Boolean isAsync;

    public Producer(String topic, Boolean isAsync) {
        Properties props = new Properties();
        props.put(ProducerConfig.CLIENT_ID_CONFIG, KafkaProperties.PRODUCER_CLIENT_ID);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_SERVER_URL);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producer = new KafkaProducer<>(props);
        this.topic = topic;
        this.isAsync = isAsync;
    }

    @Override
    public void run() {
        int messageNo = 1;
        while (true) {
            String messageStr = "Message_" + messageNo;
            long startTime = System.currentTimeMillis();
            if (isAsync) {
                // Send asynchronously
                producer.send(new ProducerRecord<>(topic, messageNo, messageStr), new DemoCallBack(startTime, messageNo, messageStr));
            } else {
                // Send synchronously
                try {
                    producer.send(new ProducerRecord<>(topic, messageNo, messageStr)).get();
                    logger.info("Sent message: ({}, {})", messageNo, messageStr);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            ++messageNo;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        producer.close();
    }
}

