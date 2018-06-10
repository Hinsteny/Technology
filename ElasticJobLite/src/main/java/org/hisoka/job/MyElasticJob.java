package org.hisoka.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * job test case
 * @author Hinsteny
 * @version $ID: MyElasticJob 2018-06-06 15:23 All rights reserved.$
 */
public class MyElasticJob implements SimpleJob {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void execute(ShardingContext context) {
        String jobName = context.getJobName();
        String taskId = context.getTaskId();
        int count = context.getShardingTotalCount();
        String jobParam = context.getShardingParameter();
        logger.info("TaskName: {}, taskId: {}, count: {}, jobParam: {}", jobName, taskId,  count, jobParam);
    }


}

