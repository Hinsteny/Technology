package org.hisoka.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.core.util.ShardingUtil;

import static com.xxl.job.core.util.ShardingUtil.ShardingVO;

import org.springframework.stereotype.Service;

/**
 * 分片广播任务
 *
 * @author Hinsteny
 * @version $ID: SimpleJobHandler 2018-06-29 14:33 All rights reserved.$
 */
@JobHandler(value = "shardingJobHandler")
@Service
public class ShardingJobHandler extends IJobHandler {

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        // 分片参数
        ShardingVO shardingVO = ShardingUtil.getShardingVo();
        XxlJobLogger.log("分片参数：当前分片序号 = {0}, 总分片数 = {1}", shardingVO.getIndex(), shardingVO.getTotal());
        // 业务逻辑
        for (int i = 0; i < shardingVO.getTotal(); i++) {
            if (i == shardingVO.getIndex()) {
                XxlJobLogger.log("第 {0} 片, 命中分片开始处理", i);
            } else {
                XxlJobLogger.log("第 {0} 片, 忽略", i);
            }
        }

        return SUCCESS;
    }

}
