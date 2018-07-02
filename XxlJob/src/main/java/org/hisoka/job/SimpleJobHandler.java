package org.hisoka.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 任务Handler示例（Bean模式）
 * 1. 业务任务需要继续IJobHandler
 * 2. 注册到Spring容器: 添加"@Component"注解, 被Spring容器扫描为Bean实例;
 * 3. 注册到执行器工厂: 添加"@JobHandler(value="自定义jobhandler名称")"注解, 注解value值对应的是调度中心新建任务的JobHandler属性的值.
 * 4. 执行日志: 需要通过 "XxlJobLogger.log" 打印执行日志;
 * 5. 业务日志使用log4j2-logger打印
 *
 * @author Hinsteny
 * @version $ID: SimpleJobHandler 2018-06-29 14:33 All rights reserved.$
 */
@JobHandler(value="simpleJobHandler")
@Component
public class SimpleJobHandler extends IJobHandler {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        logger.trace("Excute job {}", () -> s);
        logger.info("Excute job {}", () -> s);
        XxlJobLogger.log("XXL-JOB, Hello World.");

        for (int i = 0; i < 10; i++) {
            XxlJobLogger.log("beat at:" + i);
            TimeUnit.SECONDS.sleep(2);
        }
        return SUCCESS;
    }
}
