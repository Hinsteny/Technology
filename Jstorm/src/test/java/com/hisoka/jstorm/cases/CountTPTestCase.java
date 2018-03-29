package com.hisoka.jstorm.cases;

import backtype.storm.topology.TopologyBuilder;
import com.hisoka.jstorm.memery.CountBolt;
import com.hisoka.jstorm.memery.MetaSpout;
import org.junit.Test;

/**
 * @author Hinsteny
 * @version $ID: CountTPTestCase 2018-03-29 12:43 All rights reserved.$
 */
public class CountTPTestCase extends BaseTest {

    private static final String spoutId = "memery_data_provide";

    private static final String boltId = "string_length_count";

    @Test
    public void test() {
        super.initConf(configPath);
        submit();
    }

    private Boolean submit() {
        TopologyBuilder builder = super.setupBuilder(spoutId, new MetaSpout(), boltId, new CountBolt());
        super.submitTopology(builder, 10000000);
        return true;
    }

}
