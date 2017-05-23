package org.hinsteny.test.client;

import java.util.List;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/5/22
 * @copyright: 2016 All rights reserved.
 */
public interface HelloPersonService {

    List<Person> GetTestPerson(String name, int num);
}
