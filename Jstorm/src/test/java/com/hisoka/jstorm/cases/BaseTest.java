package com.hisoka.jstorm.cases;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.TopologyBuilder;
import com.alibaba.jstorm.utils.JStormUtils;
import com.hisoka.jstorm.memery.CountBolt;
import com.hisoka.jstorm.memery.MetaSpout;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import shade.storm.org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Hinsteny
 * @version $ID: BaseTest 2018-03-29 12:37 All rights reserved.$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public abstract class BaseTest {

    protected static String configPath = "E:\\project\\github\\Technology\\Jstorm\\src\\test\\resources\\metaspout.yaml";

    protected static Map conf = new HashMap<>();

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected void initConf(String config) {
        if (config.length() == 0) {
            System.err.println("Please input configuration file");
            System.exit(-1);
        }

        LoadConf(config);
    }

    private static void LoadConf(String arg) {
        if (arg.endsWith("yaml")) {
            LoadYaml(arg);
        } else {
            LoadProperty(arg);
        }
    }

    private static void LoadProperty(String prop) {
        Properties properties = new Properties();

        try {
            InputStream stream = new FileInputStream(prop);
            properties.load(stream);
        } catch (FileNotFoundException e) {
            System.out.println("No such file " + prop);
        } catch (Exception e1) {
            e1.printStackTrace();

            return;
        }

        conf.putAll(properties);
    }

    private static void LoadYaml(String confPath) {
        Yaml yaml = new Yaml();
        try {
            InputStream stream = new FileInputStream(confPath);
            conf = (Map) yaml.load(stream);
            if (conf == null || conf.isEmpty() == true) {
                throw new RuntimeException("Failed to read config file");
            }
        } catch (FileNotFoundException e) {
            System.out.println("No such file " + confPath);
            throw new RuntimeException("No config file");
        } catch (Exception e1) {
            e1.printStackTrace();
            throw new RuntimeException("Failed to read config file");
        }

        return;
    }

    public static boolean local_mode(Map conf) {
        String mode = (String) conf.get(Config.STORM_CLUSTER_MODE);
        if (mode != null) {
            if (mode.equals("local")) {
                return true;
            }
        }

        return false;
    }

    /**
     * 根据子类传入的
     * @param spoutId
     * @param spout
     * @param boltId
     * @param bolt
     * @return
     */
    protected TopologyBuilder setupBuilder(String spoutId, IRichSpout spout, String boltId, IRichBolt bolt) {
        TopologyBuilder builder = new TopologyBuilder();
        int writerParallel = JStormUtils.parseInt(conf.get("topology.writer.parallel"), 1);
        int spoutParallel = JStormUtils.parseInt(conf.get("topology.spout.parallel"), 1);
        builder.setSpout(spoutId, spout, spoutParallel);
        builder.setBolt(boltId, bolt, writerParallel).localOrShuffleGrouping(spoutId);

        return builder;
    }

    /**
     * 提交拓扑结构
     * @param builder
     */
    protected void submitTopology(TopologyBuilder builder, final int sleepTime) {
        try {
            if (local_mode(conf)) {
                LocalCluster cluster = new LocalCluster();
                cluster.submitTopology(String.valueOf(conf.get("topology.name")), conf, builder.createTopology());
                Thread.sleep(sleepTime);
                cluster.shutdown();
            } else {
                StormSubmitter.submitTopology(String.valueOf(conf.get("topology.name")), conf, builder.createTopology());
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e.getCause());
        }
    }

}
