package org.hinsteny.jvm.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author hinsteny
 * @version BioClient: 2020-05-20 18:21 All rights reserved.
 */
public class BioClient {

    public static void main(String[] args) throws IOException {
        startClient();
    }

    private static void startClient() throws IOException {
        Socket socket = new Socket(BioServer.host, BioServer.port);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        // println 自带换行
        out.println("hi, Bio Server");
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String response = in.readLine();
        System.out.println(response);
    }

}
