package org.hinsteny.rpc.proxy;

import org.hinsteny.rpc.client.RPCFuture;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/5/22
 * @copyright: 2016 All rights reserved.
 */
public interface IAsyncObjectProxy {

    RPCFuture call(String funcName, Object... args);
}
