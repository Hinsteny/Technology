package org.hisoka.kafka.client;

import java.io.UnsupportedEncodingException;

/**
 * @author Hinsteny
 * @version $ID: Test 2018-07-18 09:55 All rights reserved.$
 */
public class Test {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String content = "世界如此美好";
        byte[] contentBytes = content.getBytes("GBK");
        String gbkContent = new String(contentBytes, "UTF-8");
        System.out.println(gbkContent);
        contentBytes = gbkContent.getBytes("UTF-8");
        gbkContent = new String(contentBytes, "GBK");
        System.out.println(gbkContent);
    }
}
