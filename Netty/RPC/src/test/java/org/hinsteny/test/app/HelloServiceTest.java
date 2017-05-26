package org.hinsteny.test.app;


import org.hinsteny.rpc.client.RPCFuture;
import org.hinsteny.rpc.client.RpcClient;
import org.hinsteny.rpc.client.RpcClientHandler;
import org.hinsteny.rpc.proxy.IAsyncObjectProxy;
import org.hinsteny.test.client.HelloPersonService;
import org.hinsteny.test.client.HelloService;
import org.hinsteny.test.client.Person;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/5/22
 * @copyright: 2016 All rights reserved.
 */
@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:client-spring.xml")
public class HelloServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(RpcClientHandler.class);

    @Autowired
    private RpcClient rpcClient;

    @Test
    public void helloTest1() {
        HelloService helloService = rpcClient.create(HelloService.class);
        String result = helloService.hello("World");
        Assert.assertEquals("Hello! World", result);
    }

    @Test
    public void helloTest2() {
        HelloService helloService = rpcClient.create(HelloService.class);
        Person person = new Person("Yong", "Huang");
        String result = helloService.hello(person);
        Assert.assertEquals("Hello! Yong Huang", result);
    }

    @Test
    public void helloPersonTest(){
        HelloPersonService helloPersonService = rpcClient.create(HelloPersonService.class);
        int num = 5;
        List<Person>  persons = helloPersonService.GetTestPerson("xiaoming",num);
        List<Person> expectedPersons = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            expectedPersons.add(new Person(Integer.toString(i), "xiaoming"));
        }
        assertThat(persons, equalTo(expectedPersons));

        for (int i = 0; i<persons.size(); ++i){
            System.out.println(persons.get(i));
        }
    }

    @Test
    public void helloFutureTest1() throws ExecutionException, InterruptedException {
        IAsyncObjectProxy helloService = rpcClient.createAsync(HelloService.class);
        RPCFuture result = helloService.call("hello", "World");
        Assert.assertEquals("Hello! World", result.get());
    }

    @Test
    public void helloFutureTest2() throws ExecutionException, InterruptedException {
        IAsyncObjectProxy helloService = rpcClient.createAsync(HelloService.class);
        Person person = new Person("Yong", "Huang");
        RPCFuture result = helloService.call("hello", person);
        Assert.assertEquals("Hello! Yong Huang", result.get());
    }

    @Test
    public void helloPersonFutureTest1() throws ExecutionException, InterruptedException {
        IAsyncObjectProxy helloPersonService = rpcClient.createAsync(HelloPersonService.class);
        int num = 5;
        RPCFuture result = helloPersonService.call("GetTestPerson", "xiaoming", num);
        List<Person> persons = (List<Person>) result.get();
        List<Person> expectedPersons = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            expectedPersons.add(new Person(Integer.toString(i), "xiaoming"));
        }
        assertThat(persons, equalTo(expectedPersons));

        for (int i = 0; i < num; ++i) {
            System.out.println(persons.get(i));
        }
    }

    // 执行单个方法测试开启此方法, 如果是单次执行该类中的所有方法, 需要注释此方法
    @After
    public void setTear(){
        logger.error("Stop client!");
        if(rpcClient != null) {
            rpcClient.stop();
        }
    }

}
