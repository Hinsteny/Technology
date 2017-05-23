package org.hinsteny.test.client;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/5/22
 * @copyright: 2016 All rights reserved.
 */
public interface HelloService {

    String hello(String name);

    String hello(Person person);
}
