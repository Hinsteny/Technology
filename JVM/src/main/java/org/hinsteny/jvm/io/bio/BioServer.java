package org.hinsteny.jvm.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * BIO(阻塞IO)
 * <p>BIO模式下, 服务端主动去请求操作系统等待客户端连接, 直到有连接进来之前都会阻塞住;</p>
 *
 * @author hinsteny
 * @version BioServer: 2020-05-19 16:06 All rights reserved.
 */
public class BioServer {

    protected static final String host = "127.0.0.1";

    protected static final int port = 9999;

    public static void main(String[] args) {
        // 构建 ServerSocket
        ServerSocket serverSocket = buildServerSocket();
        // 单线程处理客户端请求, 多个客户端同时调用会顺序处理
        startBioServerSingleThread(serverSocket);
        // 多线程处理客户端请求, 为每个连接上的客户端New一个独立的线程处理请求
//        startBioServerMultiThread(serverSocket);
        // 多线程处理客户端请求, 使用线程池处理每个客户端的请求(实现资源复用, 有点类似Tomcat的工作方式)
//        startBioServerThreadPool(serverSocket);
    }

    private static ServerSocket buildServerSocket() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(host, port));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverSocket;
    }

    private static void startBioServerSingleThread(ServerSocket serverSocket) {
        try {
            while (true) {
                Socket accept = serverSocket.accept();
                handlerServerSocketConnection(accept);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startBioServerMultiThread(ServerSocket serverSocket) {
        try {
            while (true) {
                Socket accept = serverSocket.accept();
                new Thread(() -> handlerServerSocketConnection(accept)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startBioServerThreadPool(ServerSocket serverSocket) {
        ExecutorService executorService = new ThreadPoolExecutor(5, 5,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        try {
            while (true) {
                Socket accept = serverSocket.accept();
                executorService.submit(() -> handlerServerSocketConnection(accept));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handlerServerSocketConnection(Socket accept) {
        try {
            InputStream inputStream = accept.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            System.out.println(bufferedReader.readLine());
            // sleep 3 second
            Thread.sleep(3000L);
            OutputStream outputStream = accept.getOutputStream();
            //输出一行内容
            outputStream.write("hi, gays\n".getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
