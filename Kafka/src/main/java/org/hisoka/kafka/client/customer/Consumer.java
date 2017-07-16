package org.hisoka.kafka.client.customer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.hisoka.trash.KafkaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;

/**
 * @Auther hinsteny
 * @Desc
 * @Date 2017-05-16
 * @copyright: 2017 All rights reserved.
 */
public class Consumer implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final KafkaConsumer<Integer, String> consumer;
    private final String topic;
    private final Boolean isAsync;

    public Consumer(String topic, Boolean isAsync) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_SERVER_URL);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaProperties.CONSUMER_GROUP_ID);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, KafkaProperties.ENABLE_AUTO_COMMIT);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, KafkaProperties.AUTO_COMMIT_INTERVAL_MS);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, KafkaProperties.SESSION_TIMEOUT);

        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, KafkaProperties.AUTO_OFFSET_RESET_CONFIG);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumer = new KafkaConsumer<>(props);
        this.topic = topic;
        this.isAsync = isAsync;
    }

    @Override
    public void run() {
        int messageNo = 1;
        boolean flag = true;
        while (flag) {
            consumer.subscribe(Collections.singletonList(this.topic));
            ConsumerRecords<Integer, String> records = consumer.poll(10000);
            if (records.isEmpty()) {
                logger.error("========================================Not get the message records");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                flag = false
            }
            for (ConsumerRecord<Integer, String> record : records) {
                ++messageNo;
                logger.info("Received message: ({}, {}) at offset {}", record.key(), record.value(), record.offset());
            }
        }
        logger.info("Count message number is: {}", messageNo);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        consumer.close();
    }
}

