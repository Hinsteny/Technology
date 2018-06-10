package org.hisoka.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * use ScheduledThreadPoolExecutor to execute task
 * @author Hinsteny
 * @version $ID: ScheduledPoolTaskJob 2018-06-10 16:15 All rights reserved.$
 */
public class ScheduledPoolTaskJob implements SimpleJob {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 使用计划执行线程池执行定时任务，实现延迟队列的功能；
     */
    private final ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(50);

    private Random random = new Random();

    @Override
    public void execute(ShardingContext shardingContext) {
        String taskId = UUID.randomUUID().toString();
        long currentTime = System.currentTimeMillis() / 10;
        long delay = randomDelayTime();
        Runnable task = buildTask(taskId, shardingContext, currentTime + delay * 100);
        logger.info("taskId: {}, add at: [{}], delay: [{}]", taskId, currentTime, delay);
        executor.schedule(task, delay, TimeUnit.SECONDS);
    }

    private Runnable buildTask(final String taskId, final ShardingContext shardingContext, final long awaitTime) {
        return () -> logger.error("execute task: [{}], time difference: [{}]", taskId, System.currentTimeMillis() / 10 - awaitTime);
    }

    private long randomDelayTime() {
        return random.nextInt(100);
    }
}
