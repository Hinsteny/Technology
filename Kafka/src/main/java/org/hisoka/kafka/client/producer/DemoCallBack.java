package org.hisoka.kafka.client.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther hinsteny
 * @Desc
 * @Date 2017-05-07
 * @copyright: 2017 All rights reserved.
 */
public class DemoCallBack implements Callback {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final long startTime;
    private final Object key;
    private final String message;

    public DemoCallBack(long startTime, int key, String message) {
        this.startTime = startTime;
        this.key = key;
        this.message = message;
    }

    /**
     * A callback method the user can implement to provide asynchronous handling of request completion. This method will
     * be called when the record sent to the server has been acknowledged. Exactly one of the arguments will be
     * non-null.
     *
     * @param metadata  The metadata for the record that was sent (i.e. the partition and offset). Null if an error
     *                  occurred.
     * @param exception The exception thrown during processing of this record. Null if no error occurred.
     */
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        if (metadata != null) {
            logger.info("message( {}, {}) sent to partition({}), offset({}) in {} ms", key, message, metadata.partition(), metadata.offset(), elapsedTime);
        } else {
            exception.printStackTrace();
        }
    }
}
