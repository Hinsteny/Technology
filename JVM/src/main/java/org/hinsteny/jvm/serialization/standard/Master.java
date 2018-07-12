package org.hinsteny.jvm.serialization.standard;

import java.io.Serializable;

/**
 * @author Hinsteny
 * @version $ID: Person 2018-07-06 14:01 All rights reserved.$
 */
public class Master implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 422394820348023984L;

    /**
     * id
     */
    int id;

    /**
     * 姓名
     */
    String name;

    /**
     * 年龄
     */
    double age;

    /**
     * 技能
     */
    transient String tools;

    public Master(int id, String name, double age, String tools) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.tools = tools;
    }


}
