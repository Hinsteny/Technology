package org.hinsteny.jvm.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author hinsteny
 * @version AioClient: 2020-05-20 10:59 All rights reserved.
 */
public class AioClient {

    private static final String host = "127.0.0.1";

    private static final int port = 9999;

    public static void main(String[] args) throws Exception {
        startClient();
    }

    private static void startClient() throws IOException, InterruptedException, ExecutionException {
        AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
        Future<Void> future = client.connect(new InetSocketAddress(host, port));
        future.get();
        ByteBuffer buffer = ByteBuffer.allocate(100);
        client.read(buffer, null, new CompletionHandler<Integer, Void>() {
            @Override
            public void completed(Integer result, Void attachment) {
                System.out.println("client received: " + new String(buffer.array()));
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                exc.printStackTrace();
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread.sleep(10000);
    }

}
