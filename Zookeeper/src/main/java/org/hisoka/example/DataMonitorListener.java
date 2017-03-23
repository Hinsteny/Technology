package org.hisoka.example;

/**
 * @author Hinsteny
 * @Describtion Other classes use the DataMonitor by implementing this method
 * @date 2017/3/22
 * @copyright: 2016 All rights reserved.
 */
public interface DataMonitorListener {
    /**
     * The existence status of the node has changed.
     */
    void exists(byte data[]);

    /**
     * The ZooKeeper session is no longer valid.
     *
     * @param rc the ZooKeeper reason code
     */
    void closing(int rc);
}
