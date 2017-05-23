package org.hinsteny.test.util;

import org.hinsteny.rpc.protocol.JsonUtil;
import org.hinsteny.rpc.protocol.RpcRequest;
import org.hinsteny.rpc.protocol.RpcResponse;
import org.hinsteny.rpc.protocol.SerializationUtil;
import org.hinsteny.test.client.Person;
import org.hinsteny.test.server.HelloServiceImpl;

import java.util.UUID;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/5/22
 * @copyright: 2016 All rights reserved.
 */
public class JsonTest {

    public static void main(String[] args){
        RpcResponse response = new RpcResponse();
        response.setRequestId(UUID.randomUUID().toString());
        response.setError("Error msg");
        System.out.println(response.getRequestId());

        byte[] datas = JsonUtil.serialize(response);
        System.out.println("Json byte length: " + datas.length);

        byte[] datas2 = SerializationUtil.serialize(response);
        System.out.println("Protobuf byte length: " + datas2.length);

        RpcResponse resp = (RpcResponse)JsonUtil.deserialize(datas,RpcResponse.class);
        System.out.println(resp.getRequestId());
    }


    private static void TestJsonSerialize(){
        RpcRequest request = new RpcRequest();
        request.setClassName(HelloServiceImpl.class.getName());
        request.setMethodName(HelloServiceImpl.class.getDeclaredMethods()[0].getName());
        Person person = new Person("lu","xiaoxun");
        request.setParameters(new Object[]{person});
        request.setRequestId(UUID.randomUUID().toString());
        System.out.println(request.getRequestId());

        byte[] datas = JsonUtil.serialize(request);
        System.out.println("Json byte length: " + datas.length);

        byte[] datas2 = SerializationUtil.serialize(request);
        System.out.println("Protobuf byte length: " + datas2.length);

        RpcRequest req = (RpcRequest)JsonUtil.deserialize(datas,RpcRequest.class);
        System.out.println(req.getRequestId());
    }

}
