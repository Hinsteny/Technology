package org.hinsteny.rpc.client;

import org.hinsteny.rpc.proxy.IAsyncObjectProxy;
import org.hinsteny.rpc.proxy.ObjectProxy;
import org.hinsteny.rpc.registry.ServiceDiscovery;
import org.hinsteny.rpc.service.RpcServiceFactor;

import java.lang.reflect.Proxy;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/5/22
 * @copyright: 2016 All rights reserved.
 */
public class RpcClient {

    private String serverAddress;

    private ServiceDiscovery serviceDiscovery;

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(16, 16, 600L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(65536));

    public RpcClient(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public RpcClient(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    public static <T> T create(Class<T> interfaceClass) {
        return RpcServiceFactor.getServiceInstanse(interfaceClass);
    }

    public static <T> IAsyncObjectProxy createAsync(Class<T> interfaceClass) {
        return new ObjectProxy<>(interfaceClass);
    }

    public static void submit(Runnable task) {
        threadPoolExecutor.submit(task);
    }

    public void stop() {
        threadPoolExecutor.shutdown();
        serviceDiscovery.stop();
        ConnectManage.getInstance().stop();
    }
}
