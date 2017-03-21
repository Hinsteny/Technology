package org.hisoka.BarriersQueue;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/3/21
 * @copyright: 2016 All rights reserved.
 */
public class SyncPrimitive implements Watcher {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    static ZooKeeper zk = null;
    static Integer mutex;

    String root;

    SyncPrimitive(String address) {
        if(zk == null){
            try {
                logger.info("Starting ZK:");
                zk = new ZooKeeper(address, 3000, this);
                mutex = new Integer(-1);
                logger.info("Finished starting ZK: {}", zk);
            } catch (IOException e) {
                logger.error(e.toString());
                zk = null;
            }
        }
        //else mutex = new Integer(-1);
    }

    synchronized public void process(WatchedEvent event) {
        synchronized (mutex) {
            logger.info("Process: " + event.getType());
            mutex.notify();
        }
    }

}
