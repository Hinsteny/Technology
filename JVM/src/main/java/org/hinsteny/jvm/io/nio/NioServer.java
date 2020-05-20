package org.hinsteny.jvm.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * NIO(非阻塞IO)
 * <p>BIO模式下, 服务端主动去请求操作系统等待客户端连接, 直到有连接进来之前都会阻塞住;</p>
 * <p>NIO模式下, 操作系统在接受到客户端连接时会将对应的事件放到Kqueue中，服务端轮询去查询Kqueue即可;</p>
 *
 * @author hinsteny
 * @version NioServer: 2020-05-19 17:11 All rights reserved.
 */
public class NioServer {

    protected static final String host = "127.0.0.1";

    protected static final int port = 9999;

    public static void main(String[] args) {
        // 单线程处理客户端请求
        startNioServer(false);
        // 线程池处理客户端请求
//        startNioServer(true);
    }

    private static void startNioServer(boolean useThreadPool) {
        ExecutorService executorService = new ThreadPoolExecutor(5, 5,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        try (Selector selector = Selector.open(); ServerSocketChannel serverSocket = ServerSocketChannel.open();) {
            serverSocket.bind(new InetSocketAddress(host, port));
            serverSocket.configureBlocking(false);
            // 注册到Selector，并说明关注点为连接建立时
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.selectNow();// 阻塞等待就绪的Channel，这是关键点之一(可以阻塞; 支持超时; 也可以立即返回)
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                while (iter.hasNext()) {
                    // 生产系统中一般会额外进行就绪状态检查
                    SelectionKey key = iter.next();
                    if (useThreadPool) {
                        executorService.submit(() -> handlerServerSocketConnection((ServerSocketChannel) key.channel()));
                    } else {
                        handlerServerSocketConnection((ServerSocketChannel) key.channel());
                    }
                    iter.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void handlerServerSocketConnection(ServerSocketChannel channel) {
        ByteBuffer readBuf = ByteBuffer.allocate(1024);
        try (SocketChannel client = channel.accept()) {
//            client.configureBlocking(true);
            while (true) {
                int count = client.read(readBuf);
                String message = new String(readBuf.array(), 0, count);
                System.out.println(message);
                if ("exit".equals(message)) {
                    break;
                }
                readBuf.clear();
            }
            // sleep 3 second
            Thread.sleep(3000L);
            client.write(Charset.defaultCharset().encode("say goodbye!"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
