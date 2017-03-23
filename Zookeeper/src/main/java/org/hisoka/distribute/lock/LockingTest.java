package org.hisoka.distribute.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Hinsteny
 * @Describtion 连接动态创建的zkzerver分布式锁测试成功
 * @date 2017/3/22
 * @copyright: 2016 All rights reserved.
 */
public class LockingTest {

    private static final int QTY = 3;
    private static final int REPETITIONS = QTY * 1;
    private static final String PATH = "/examples/locks";

    public static void main(String[] args) throws Exception {
        // all of the useful sample code is in ExampleClientThatLocks.java
        // FakeLimitedResource simulates some external resource that can only be access by one process at a time
        final FakeSoleResource resource = new FakeSoleResource();
        ExecutorService service = Executors.newFixedThreadPool(QTY);
        final TestingServer server = new TestingServer(2182);
        work(resource, service, server);
    }

    private static void work(FakeSoleResource resource, ExecutorService service, final TestingServer server) throws Exception  {
        try {
            for (int i = 0; i < QTY; ++i) {
                final int index = i;
                Callable<Void> task = () -> {
                    CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(), new ExponentialBackoffRetry(1000, 3));
                    try {
                        client.start();
                        ClientLockHolder example = new ClientLockHolder(client, PATH, resource, "Client " + index);
                        for (int j = 0; j < REPETITIONS; ++j) {
                            example.doWork(10, TimeUnit.SECONDS);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } catch (Exception e) {
                        e.printStackTrace();
                        // log or do something
                    } finally {
                        CloseableUtils.closeQuietly(client);
                    }
                    return null;
                };
                service.submit(task);
            }
            service.shutdown();
            service.awaitTermination(10, TimeUnit.MINUTES);
        } finally {
            CloseableUtils.closeQuietly(server);
        }
    }
}
