package com.hisoka.jstorm.memery;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import com.alibaba.fastjson.JSON;
import com.alibaba.jstorm.client.spout.IAckValueSpout;
import com.alibaba.jstorm.client.spout.IFailValueSpout;
import com.alibaba.jstorm.utils.JStormUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 数据提供源, 随机生成字符串指定次数
 * @author Hinsteny
 * @version $ID: MetaSpout 2018-03-28 17:37 All rights reserved.$
 */
public class MetaSpout implements IRichSpout, IAckValueSpout, IFailValueSpout {

    private static final Logger logger = LoggerFactory.getLogger(MetaSpout.class);

    protected Map conf;

    protected SpoutOutputCollector collector;

    private int start = 0;

    private int max = 100;

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.conf = conf;
        this.collector = collector;
        this.max = JStormUtils.parseInt(conf.get("max_item_number"));
    }

    @Override
    public void close() {

    }

    @Override
    public void activate() {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void nextTuple() {
        if (start < max) {
            String str = UUID.randomUUID().toString();
            collector.emit(new Values(str), str);
        } else {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            collector.emit( new Values(""),"");
        }

        ++start;
    }

    @Override
    public void ack(Object msgId) {
        logger.debug("ACK OK msgId: {}"  + JSON.toJSONString(msgId));
    }

    @Override
    public void fail(Object msgId) {
        System.out.println("ACK FAIL:"  + JSON.toJSONString(msgId));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("uuid"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

    @Override
    public void ack(Object msgId, List<Object> values) {
        logger.debug("ACK OK msgId: {}, values:{}",  JSON.toJSONString(msgId), JSON.toJSONString(values));
    }

    @Override
    public void fail(Object msgId, List<Object> values) {
        logger.debug("ACK FAIL:"  + JSON.toJSONString(msgId));
    }
}
