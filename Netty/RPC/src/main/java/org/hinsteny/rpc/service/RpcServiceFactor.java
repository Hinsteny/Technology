package org.hinsteny.rpc.service;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/5/23
 * @copyright: 2016 All rights reserved.
 */
public class RpcServiceFactor {

    // 后面可以做自定义扩展实现
    private static IRpcServiceFactor iRpcServiceFactor = new EMSIRpcServiceFactor();

    public static <T> T getServiceInstanse(Class<T> interfaceClass) {
        return iRpcServiceFactor.getRpcServiceClient(interfaceClass);
    }

}
