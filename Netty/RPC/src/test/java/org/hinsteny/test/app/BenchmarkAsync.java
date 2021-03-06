package org.hinsteny.test.app;

import org.hinsteny.rpc.client.RPCFuture;
import org.hinsteny.rpc.client.RpcClient;
import org.hinsteny.rpc.proxy.IAsyncObjectProxy;
import org.hinsteny.rpc.registry.ServiceDiscovery;
import org.hinsteny.test.client.HelloService;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/5/22
 * @copyright: 2016 All rights reserved.
 */
public class BenchmarkAsync {

    public static void main(String[] args) throws InterruptedException {
        ServiceDiscovery serviceDiscovery = new ServiceDiscovery("127.0.0.1:2181");
        final RpcClient rpcClient = new RpcClient(serviceDiscovery);

        int threadNum = 10;
        final int requestNum = 20;
        final CountDownLatch latch = new CountDownLatch(threadNum);

        long startTime = System.currentTimeMillis();
        //benchmark for async call
        for (int i = 0; i < threadNum; ++i) {
            new Thread(() ->{
                    for (int j = 0; j < requestNum; j++) {
                        try {
                            IAsyncObjectProxy client = rpcClient.createAsync(HelloService.class);
                            RPCFuture helloFuture = client.call("hello", Integer.toString(j));
                            String result = (String) helloFuture.get(3000, TimeUnit.MILLISECONDS);
                            //System.out.println(result);
                            if (!result.equals("Hello! " + j))
                                System.out.println("error = " + result);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }).start();
        }
        latch.await();
        long timeCost = (System.currentTimeMillis() - startTime);
        String msg = String.format("Async call total-time-cost:%sms, req/s=%s", timeCost, ((double) (requestNum * threadNum)) / timeCost * 1000);
        System.out.println(msg);

        rpcClient.stop();

    }
}
