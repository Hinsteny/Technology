package org.hinsteny.test.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/5/22
 * @copyright: 2016 All rights reserved.
 */
public class RpcServerBootstrap {

    private static String[] configPath = {"classpath*:server-spring.xml"};
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(configPath);
        context.start();
    }

}
