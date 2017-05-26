package org.hinsteny.test.app;

import org.hinsteny.rpc.client.RpcClient;
import org.hinsteny.rpc.registry.ServiceDiscovery;
import org.hinsteny.test.client.HelloService;

import java.util.concurrent.CountDownLatch;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/5/22
 * @copyright: 2016 All rights reserved.
 */
public class Benchmark {

    public static void main(String[] args) throws InterruptedException {

        ServiceDiscovery serviceDiscovery = new ServiceDiscovery("127.0.0.1:2181");
        final RpcClient rpcClient = new RpcClient(serviceDiscovery);

        int threadNum = 10;
        final int requestNum = 100;
        final CountDownLatch latch = new CountDownLatch(threadNum);
        long startTime = System.currentTimeMillis();
        //benchmark for sync call
        for(int i = 0; i < threadNum; ++i){
            new Thread(() -> {
                for (int j = 0; j < requestNum; j++) {
                    final HelloService syncClient = rpcClient.create(HelloService.class);
                    String result = syncClient.hello(Integer.toString(j));
                    if (!result.equals("Hello! " + j))
                        System.out.print("error = " + result);
                }
                latch.countDown();
            }).start();
        }
        latch.await();
        long timeCost = (System.currentTimeMillis() - startTime);
        String msg = String.format("Sync call total-time-cost:%sms, req/s=%s",timeCost,((double)(requestNum * threadNum)) / timeCost * 1000);
        System.out.println(msg);

        rpcClient.stop();
    }
}
