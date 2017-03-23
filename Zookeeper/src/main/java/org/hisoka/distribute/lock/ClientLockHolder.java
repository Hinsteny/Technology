package org.hisoka.distribute.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author Hinsteny
 * @Describtion 该类负责请求锁, 使用锁, 释放资源一系列完整的过程
 * @date 2017/3/21
 * @copyright: 2016 All rights reserved.
 */
public class ClientLockHolder {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final InterProcessMutex lock;
    private final FakeSoleResource resource;
    private final String clientName;

    public ClientLockHolder(CuratorFramework client, String lockPath, FakeSoleResource resource, String clientName) {
        this.resource = resource;
        this.clientName = clientName;
        lock = new InterProcessMutex(client, lockPath);
    }

    public void doWork(long time, TimeUnit unit) throws Exception {
        if (!lock.acquire(time, unit)) {
            throw new IllegalStateException(clientName + " could not acquire the lock");
        }
        try {
            logger.info("{} has the lock", clientName);
            resource.visit(); //access resource exclusively
        } finally {
            logger.info("{} releasing the lock", clientName);
            lock.release(); // always release the lock in a finally block
        }
    }

}
