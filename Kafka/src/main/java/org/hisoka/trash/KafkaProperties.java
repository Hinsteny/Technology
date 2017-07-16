package org.hisoka.trash;

/**
 * @Auther hinsteny
 * @Date 2017-03-12
 * @copyright: 2017 All rights reserved.
 */
public class KafkaProperties {

    /* common config info */
    public static final String TOPIC = "test";
    public static final String KAFKA_SERVER_URL = "localhost:9092";
    public static final int KAFKA_PRODUCER_BUFFER_SIZE = 64 * 1024;
    public static final int CONNECTION_TIMEOUT = 100000;
    public static final int SESSION_TIMEOUT = 100000;

    public static final String TOPIC1 = "topic1";
    public static final String TOPIC2 = "topic2";
    public static final String TOPIC3 = "topic3";

    /* Producer config info*/
    public static final String PRODUCER_CLIENT_ID = "SimpleProducerDemoClient";

    /* Consumer config info*/
    public static final String AUTO_OFFSET_RESET_CONFIG = "latest";
    public static final String CONSUMER_GROUP_ID = "SimpleConsumerGroup";
    public static final String ENABLE_AUTO_COMMIT = "true";
    public static final int AUTO_COMMIT_INTERVAL_MS = 1000;
    public static final int ZOOKEEPER_SYNC_TIME_MS = 200;
    public static final String CONSUMER_CLIENT_ID = "SimpleConsumerDemoClient";



    private KafkaProperties() {}
}
