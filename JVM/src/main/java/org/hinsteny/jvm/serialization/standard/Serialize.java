package org.hinsteny.jvm.serialization.standard;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * java standard serialize Object example
 *
 * @author Hinsteny
 * @version $ID: Serialize 2018-07-06 14:03 All rights reserved.$
 */
public class Serialize {

    public static void main(String args[]) throws Exception {
        // serialize test
        serializeMaster();
        // externalizable test
        serializeBook();
    }

    private static void serializeMaster()throws Exception {
        // create one object
        Master master = new Master(211, "ravi", 26.0, "art");
        // create file-out-stream object
        FileOutputStream fout = new FileOutputStream("master.object");
        // use ObjectOutputStream serialize person object and write to file-out-stream
        ObjectOutputStream out = new ObjectOutputStream(fout);
        out.writeObject(master);
        out.flush();
        System.out.println(String.format("serialize %s object to out-stream success", "master"));
    }

    private static void serializeBook()throws Exception {
        // create one object
        Book book = new Book(122, "soul", 23.2);
        // create file-out-stream object
        FileOutputStream fout = new FileOutputStream("book.object");
        // use ObjectOutputStream serialize person object and write to file-out-stream
        ObjectOutputStream out = new ObjectOutputStream(fout);
        out.writeObject(book);
        out.flush();
        System.out.println(String.format("serialize %s object to out-stream success", "book"));
    }

}
