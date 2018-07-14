package org.hinsteny.jvm.serialization.json.custom;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

/**
 * .fastjson custom serialize-Deserialize Object example
 * @author Hinsteny
 * @version $ID: CustomSerialize 2018-07-13 16:38 All rights reserved.$
 */
public class CustomSerialize {

    static {
        SerializeConfig.getGlobalInstance().addFilter(OrderInfo.class, new CustomNameFilter());
        SerializeConfig.getGlobalInstance().put(OrderStateEnum.class, new OrderStateEnumSerializer());
        ParserConfig.getGlobalInstance().putDeserializer(OrderStateEnum.class, new OrderStateEnumDeserializer());
    }

    public static void main(String args[]) throws Exception {
        // serialize object test
        serializeMaster();

    }

    private static void serializeMaster()throws Exception {
        // create one object
        OrderInfo orderInfo = new OrderInfo("TO2018071400001", OrderStateEnum.SUCC);
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(OrderInfo.class, "orderNo");
        String jsonString = JSON.toJSONString(orderInfo, filter);
        System.out.println(String.format("Serialize OrderInfo, string: %s", jsonString));
        orderInfo = JSON.parseObject(jsonString, OrderInfo.class);
        System.out.println(String.format("Deserialize OrderInfo, orderNo: %s, state: %s", orderInfo.getOrderNo(), orderInfo.getState()));
    }

}
