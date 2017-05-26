package org.hinsteny.rpc.service;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/5/23
 * @copyright: 2016 All rights reserved.
 */
public interface IRpcServiceFactor {

    /**
     * Return a proxy interfaceClass instance as specified by the
     * <code>name</code> parameter.
     *
     * <p>Null-valued name arguments are considered invalid.
     *
     * @param interfaceClass the name of the interfaceClass to return
     * @return a interfaceClass proxy instance
     */
    <T> T getRpcServiceClient(Class<T> interfaceClass);
}
