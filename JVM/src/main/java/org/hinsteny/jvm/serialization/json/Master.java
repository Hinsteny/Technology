package org.hinsteny.jvm.serialization.json;

/**
 * @author Hinsteny
 * @version $ID: Person 2018-07-12 21:01 All rights reserved.$
 */
public class Master {

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

    /*private Master() {
    }*/

    public Master(int id, String name, double age, String tools) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.tools = tools;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public String getTools() {
        return tools;
    }

    public void setTools(String tools) {
        this.tools = tools;
    }
}
