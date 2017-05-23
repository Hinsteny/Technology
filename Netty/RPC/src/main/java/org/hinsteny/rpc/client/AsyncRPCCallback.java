package org.hinsteny.rpc.client;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/5/22
 * @copyright: 2016 All rights reserved.
 */
public interface AsyncRPCCallback {

    void success(Object result);

    void fail(Exception e);

}
