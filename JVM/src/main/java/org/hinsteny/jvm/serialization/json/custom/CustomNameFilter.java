package org.hinsteny.jvm.serialization.json.custom;

import com.alibaba.fastjson.serializer.NameFilter;

/**
 * 拦截器, 可以针对特定的class在做序列化时进行自定义操作
 * @author Hinsteny
 * @version $ID: CustomNameFilter 2018-07-14 15:20 All rights reserved.$
 */
public class CustomNameFilter implements NameFilter {

    @Override
    public String process(Object object, String name, Object value) {
        return name.toUpperCase();
    }
}
