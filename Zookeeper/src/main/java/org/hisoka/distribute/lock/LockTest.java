package org.hisoka.distribute.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.hisoka.base.ZkSever;

import java.util.UUID;
import java.util.concurrent.*;

/**
 * @author Hinsteny
 * @Describtion 发现这个分布式锁引用的[curator]是3.X, 所以需要连接的zk版本是3.5.x及以上
 * @date 2017/3/22
 * @copyright: 2016 All rights reserved.
 */
public class LockTest {

    public static void main(String[] args)  throws InterruptedException {
        ZkSever zk = new ZkSever(args[0], args[1]);
        String lockPath = args.length > 2 ? args[2] : "/hisoka_lock";
        final int QTY = 5;
        final int REPETITIONS = QTY * 10;
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(100, 3);
        work(zk, lockPath, retryPolicy, QTY, REPETITIONS);
    }

    private static void work(ZkSever server, final String lockPath, RetryPolicy retryPolicy, int QTY, int REPETITIONS) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(QTY);
        final FakeSoleResource resource = new FakeSoleResource();
        try {
            for (int i = 0; i < QTY; i++) {
                final String key = String.valueOf(i) + "__" + UUID.randomUUID().toString();
                Callable<Void> task = () -> {
                    CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(), retryPolicy);
//                    CuratorFramework client = CuratorFrameworkFactory.builder().connectString(server.getConnectString()).sessionTimeoutMs(1000).connectionTimeoutMs(2000).retryPolicy(new ExponentialBackoffRetry(100, 3)).build();
                    try {
                        client.start();
                        ClientLockHolder example = new ClientLockHolder(client, lockPath, resource, key);
                        for (int j = 0; j < REPETITIONS; ++j) {
                            example.doWork(3, TimeUnit.SECONDS);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } catch (Exception e) {
                        e.printStackTrace();
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
