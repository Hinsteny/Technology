package org.hisoka;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 应用程序启动入口
 * @author Hinsteny
 * @version $ID: MyJobApplication 2018-06-29 15:01 All rights reserved.$
 */
public class MyJobApplication {

    /**
     * start spring container
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"appcontext-xxl-job.xml"});
        context.start();
        Thread.sleep(1000000);
    }

}
