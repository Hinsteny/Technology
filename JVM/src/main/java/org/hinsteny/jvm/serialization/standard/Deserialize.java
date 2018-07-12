package org.hinsteny.jvm.serialization.standard;

import java.io.*;

/**
 * java standard Deserialize Object example
 *
 * @author Hinsteny
 * @version $ID: Deserialize 2018-07-06 14:10 All rights reserved.$
 */
public class Deserialize {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // serialize test
        deserializeMaster();
        // externalizable test
        deserializeBook();
    }

    public static void deserializeMaster() throws IOException, ClassNotFoundException {
        // create input-stream from object file
        InputStream inputStream = new FileInputStream("master.object");
        // use ObjectInputStream deserialize obe object
        ObjectInputStream in = new ObjectInputStream(inputStream);
        Master master = (Master) in.readObject();
        System.out.println(String.format("Deserialize Object, id: %s, name: %s, age: %s, tools: %s", master.id, master.name, master.age, master.tools));
        in.close();
    }

    public static void deserializeBook() throws IOException, ClassNotFoundException {
        // create input-stream from object file
        InputStream inputStream = new FileInputStream("book.object");
        // use ObjectInputStream deserialize obe object
        ObjectInputStream in = new ObjectInputStream(inputStream);
        Book book = (Book) in.readObject();
        System.out.println(String.format("Deserialize Object, id: %s, name: %s, price: %s", book.id, book.name, book.price));
        in.close();
    }
}
