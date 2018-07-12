package org.hinsteny.jvm.serialization.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * fastjson serialize-Deserialize Object example
 * @author Hinsteny
 * @version $ID: Serialize 2018-07-12 19:58 All rights reserved.$
 */
public class Serialize {

    public static void main(String args[]) throws Exception {
        // serialize object test
        serializeMaster();

        // serialize object-list test
        serializeMasterList();

        // serialize object-map test
        serializeMasterMap();

        // serialize object-list-map test
        serializeMasterListMap();

    }

    private static void serializeMaster()throws Exception {
        // create one object
        Master master = new Master(1, "ravi", 26.0, "art");
        String jsonString = JSON.toJSONString(master);
        master = JSON.parseObject(jsonString, Master.class);
        System.out.println(String.format("Deserialize Object, id: %s, name: %s, age: %s, tools: %s", master.id, master.name, master.age, master.tools));
    }

    private static void serializeMasterList()throws Exception {
        List<Master> masterList = new ArrayList<>(4);
        // create first object
        Master master = new Master(2, "ravi", 24.0, "music");
        masterList.add(master);
        // create second object
        master = new Master(3, "lina", 25.0, "art");
        masterList.add(master);
        String jsonString = JSON.toJSONString(masterList);
        masterList = JSON.parseArray(jsonString, Master.class);
        System.out.println(String.format("Deserialize list, size: %s", masterList.size()));
        masterList.stream().forEach((item) -> System.out.println(String.format("Deserialize Object, id: %s, name: %s, age: %s, tools: %s", item.id, item.name, item.age, item.tools)));
    }

    private static void serializeMasterMap()throws Exception {
        Map<String, Master> masterMap = new HashMap<>(4);
        // create first object
        Master master = new Master(4, "ravi", 24.0, "music");
        masterMap.put("first", master);
        // create second object
        master = new Master(5, "lina", 25.0, "art");
        masterMap.put("second", master);
        String jsonString = JSON.toJSONString(masterMap);
        masterMap = JSON.parseObject(jsonString, new TypeReference<Map<String, Master>>() {}, new Feature[0]);
        System.out.println(String.format("Deserialize map, size: %s", masterMap.size()));
        masterMap.forEach((key, item) -> System.out.println(String.format("Deserialize Object, id: %s, name: %s, age: %s, tools: %s", item.id, item.name, item.age, item.tools)));
    }

    private static void serializeMasterListMap()throws Exception {
        Map<String, Master> masterMap = new HashMap<>(4);
        List<Map<String, Master>> masterList = new ArrayList<>(4);
        // create first object
        Master master = new Master(6, "ravi", 24.0, "music");
        masterMap.put("first", master);
        masterList.add(masterMap);
        String jsonString = JSON.toJSONString(masterList);
        masterList = JSON.parseObject(jsonString, new TypeReference<List<Map<String, Master>>>() {}, new Feature[0]);
        System.out.println(String.format("Deserialize map, size: %s", masterMap.size()));
        masterList.stream().forEach(( entry ) -> {
            Map<String, Master> map =  entry ;
            map.forEach((key, item) -> System.out.println(String.format("Deserialize Object, id: %s, name: %s, age: %s, tools: %s", item.id, item.name, item.age, item.tools)));
        });
    }

}
