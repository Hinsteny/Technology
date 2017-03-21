package org.hisoka.distributed.lock;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Hinsteny
 * @Describtion 假定的唯一客户端访问资源
 * @date 2017/3/21
 * @copyright: 2016 All rights reserved.
 */
public class FakeSoleResource {

    private final AtomicBoolean resou = new AtomicBoolean(false);

    /**
     * 访问唯一资源
     * 这个例子在使用锁的情况下不会非法并发异常IllegalStateException, 但是在无锁的情况由于sleep了一段时间，很容易抛出异常
     * @throws InterruptedException
     */
    public void visit () throws InterruptedException {
        if (!resou.compareAndSet(false, true)) {
            throw new IllegalStateException("Needs to be used by only one client at a time");
        }
        try {
            Thread.sleep((long) (3 * Math.random()));
        } finally {
            resou.set(false);
        }
    }
}
