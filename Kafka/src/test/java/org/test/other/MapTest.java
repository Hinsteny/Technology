package org.test.other;

import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Auther hinsteny
 * @Desc
 * @Date 2017-06-25
 * @copyright: 2017 All rights reserved.
 */
public class MapTest {

    @Test
    public void hashMapENtryTest () {
        Map<String, Double> data = new HashMap<>();
        data.put("first", 1.0);
        data.put("second", 2.0);
        data.put("third", 3.0);
        data.forEach((key, value)->{
            System.err.println(key + ":" + value);
//            data.remove(key);//can not delete when foreach the entryset
        });
        System.err.println(data.size());
    }

    @Test
    public void hashMapENtrySetTest () {
        Map<String, Double> data = new HashMap<>();
        data.put("first", 1.0);
        data.put("second", 2.0);
        data.put("third", 3.0);
        for (Map.Entry<String, Double> entry : data.entrySet()){
            System.err.println(entry.getKey() + ":" + entry.getValue());
//            data.remove(entry.getKey());//can not delete when foreach the entryset
        }
        System.err.println(data.size());
    }

    @Test
    public void hashMapENtrySetIteratorTest () {
        Map<String, Double> data = new HashMap<>();
        data.put("first", 1.0);
        data.put("second", 2.0);
        data.put("third", 3.0);
        Iterator<Map.Entry<String, Double>> iterator = data.entrySet().iterator();
        for (;iterator.hasNext();){
            Map.Entry<String, Double> entry = iterator.next();
            System.err.println(entry.getKey() + ":" + entry.getValue());
            iterator.remove();
        }
        System.err.println(data.size());
    }


}
