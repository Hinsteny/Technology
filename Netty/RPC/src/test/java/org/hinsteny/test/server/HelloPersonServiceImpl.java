package org.hinsteny.test.server;

import org.hinsteny.rpc.server.RpcService;
import org.hinsteny.test.client.HelloPersonService;
import org.hinsteny.test.client.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/5/22
 * @copyright: 2016 All rights reserved.
 */
@RpcService(HelloPersonService.class)
public class HelloPersonServiceImpl implements HelloPersonService {

    @Override
    public List<Person> GetTestPerson(String name, int num) {
        List<Person> persons = new ArrayList<>(num);
        for (int i = 0; i < num; ++i) {
            persons.add(new Person(Integer.toString(i), name));
        }
        return persons;
    }
}
