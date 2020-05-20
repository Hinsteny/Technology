package org.hinsteny.jvm.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * NIO2(异步IO-AIO)
 * <p>AIO模式下, 接收客户端连接请求，读请求，写请求都是异步的，通过回调函数或者返回Future处理结果;</p>
 *
 * @author hinsteny
 * @version AioServer: 2020-05-20 10:01 All rights reserved.
 */
public class AioServer {

    private static final String host = "127.0.0.1";

    private static final int port = 9999;

    private static Charset charset = Charset.forName("US-ASCII");

    private static CharsetEncoder encoder = charset.newEncoder();

    public static void main(String[] args) throws Exception {
        startAioServer();
    }

    private static void startAioServer() throws IOException, InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(5, 5,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        AsynchronousChannelGroup group = AsynchronousChannelGroup.withThreadPool(executorService);
        AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(group)
            .bind(new InetSocketAddress(host, port));
        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
            @Override
            public void completed(AsynchronousSocketChannel result, Void attachment) {
                server.accept(null, this); // 接受下一个连接
                try {
                    String now = new Date().toString();
                    ByteBuffer buffer = encoder.encode(CharBuffer.wrap(now + "\r\n"));
                    //result.write(buffer, null, new CompletionHandler<Integer,Void>(){...}); //callback or
                    Future<Integer> f = result.write(buffer);
                    f.get();
                    System.out.println("sent to client: " + now);
                    result.close();
                } catch (IOException | InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                exc.printStackTrace();
            }
        });
        group.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
    }
}
