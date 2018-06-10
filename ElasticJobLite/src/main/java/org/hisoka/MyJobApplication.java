package org.hisoka;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Job Application starter
 *
 * @author Hinsteny
 * @version $ID: MyElasticJob 2018-06-06 15:23 All rights reserved.$
 */
public class MyJobApplication {

    /**
     * start spring container
     * @param args
     */
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/spring-context.xml"});
        context.start();
    }

}
