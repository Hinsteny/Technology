package com.hisoka.jstorm.memery;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.alibaba.fastjson.JSON;
import com.hisoka.jstorm.common.LimitQueueList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shade.storm.org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * 计算累计字符串长度的计算单元, 当无有效数据传入时, 就终止一次总计数
 * @author Hinsteny
 * @version $ID: MetaSpout 2018-03-28 17:37 All rights reserved.$
 */
public class CountBolt implements IRichBolt {

    private static final long serialVersionUID = 2495121976857546346L;

    private static final Logger logger = LoggerFactory.getLogger(CountBolt.class);

    /**
     * 当前计算单元id
     */
    private Integer             id;

    /**
     * 当前计算单元name
     */
    private String              name;

    /**
     * 数据输出器
     */
    protected OutputCollector      collector;

    /**
     * 内存存储数据
     */
    private LimitQueueList<String> data = new LimitQueueList<>(64);

    /**
     * 当前统计次数的总值
     */
    private int currentCountNumber = 0;

    /**
     * 统计总次数
     */
    private int countTimes = 0;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.name = context.getThisComponentId();
        this.id = context.getThisTaskId();
        this.collector = collector;
    }

    @Override
    public void execute(Tuple tuple) {
        // TODO Auto-generated method stub
        String metaTuple = (String) tuple.getValue(0);
        
        try {
            logger.info("tuple: " + JSON.toJSONString(tuple.getValues()));
        } catch (Exception e) {
            collector.fail(tuple);
            return ;
        }

        boolean flag = count(tuple);
        if (!flag) {
            printCountInfo();
        }

        // ack
        collector.ack(tuple);
    }

    @Override
    public void cleanup() {
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("num"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

    /**
     * 统计输入值
     * @param tuple
     * @return
     */
    private boolean count(Tuple tuple) {
        String metaTuple = (String) tuple.getValue(0);
        if (StringUtils.isBlank(metaTuple)) {
            return false;
        }
        data.offer(metaTuple);
        this.currentCountNumber += metaTuple.length();
        return true;
    }

    /**
     * 打印当前统计信息, 如果当前是一次有效的统计, 则开启一次新的统计
     * @return
     */
    private boolean printCountInfo() {
        if (currentCountNumber > 0) {
            ++countTimes;
            logger.info(String.format("current count times is %s and count number is %s!", countTimes, currentCountNumber));
            currentCountNumber = 0;
            return true;
        } else {
            logger.error("last count number is zero, so not start new count batch!");
            return false;
        }
    }
}
