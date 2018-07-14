package org.hinsteny.jvm.serialization.json.custom;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author Hinsteny
 * @version $ID: OrderStateEnumSerializer 2018-07-15 16:44 All rights reserved.$
 */
public class OrderStateEnumSerializer implements ObjectSerializer {

    /**
     * fastjson invokes this call-back method during serialization when it encounters a field of the
     * specified type.
     *
     * @param serializer
     * @param object     src the object that needs to be converted to Json.
     * @param fieldName  parent object field name
     * @param fieldType  parent object field type
     * @param features   parent object field serializer features
     * @throws IOException
     */
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;

        OrderStateEnum value = (OrderStateEnum) object;
        if (value == null) {
            out.writeString("");
            return;
        }
        int code = value.getCode();
        out.writeInt(code);
    }
}
