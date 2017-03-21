package org.hisoka.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/3/21
 * @copyright: 2016 All rights reserved.
 */
public class ZkSever implements Closeable {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * zkServer 主机ip
     */
    private String host;

    /**
     * 主机端口
     */
    private String port;

    /**
     * 目录路径
     */
    private String path;

    public ZkSever(String host, String port) {
        this(host, port, "");
    }

    public ZkSever(String host, String port, String path) {
        this.host = host;
        this.port = port;
        this.path = path;
    }

    public void close() throws IOException {
        logger.info("Close zkSever connection!");
    }

    public String getConnectString() {
        return this.host + ":" + this.port;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }
}
