package org.hinsteny.jvm.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

/**
 * @author hinsteny
 * @version NioClient: 2020-05-20 18:42 All rights reserved.
 */
public class NioClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        startClient();
    }

    private static void startClient() throws IOException, InterruptedException {
        InetSocketAddress crunchifyAddr = new InetSocketAddress(NioServer.host, NioServer.port);
        SocketChannel crunchifyClient = SocketChannel.open(crunchifyAddr);
        System.out.println("Connecting to Server on port 1111...");
        ArrayList<String> companyDetails = new ArrayList<String>();
        // create a ArrayList with companyName list
        companyDetails.add("Facebook");
        companyDetails.add("Twitter");
        companyDetails.add("IBM");
        companyDetails.add("Google");
        companyDetails.add("Crunchify");
        companyDetails.add("exit");
        for (String companyName : companyDetails) {
            byte[] message = companyName.getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(message);
            crunchifyClient.write(buffer);
            System.out.println("sending: " + companyName);
            buffer.clear();
            // wait for 2 seconds before sending next message
            Thread.sleep(2000);
        }
        crunchifyClient.close();
    }

}
