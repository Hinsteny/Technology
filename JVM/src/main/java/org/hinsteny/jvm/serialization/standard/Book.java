package org.hinsteny.jvm.serialization.standard;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 *
 * @author Hinsteny
 * @version $ID: Book 2018-07-06 15:51 All rights reserved.$
 */
public class Book implements Externalizable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 422394820348023984L;

    /**
     * id
     */
    int id;

    /**
     * 书名
     */
    String name;

    /**
     * 价格
     */
    double price;

    public Book() {

    }

    public Book(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(id);
        out.writeUTF(name);
        out.writeDouble(price);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = in.readInt();
        name = in.readUTF();
        price = in.readDouble();
    }
}
