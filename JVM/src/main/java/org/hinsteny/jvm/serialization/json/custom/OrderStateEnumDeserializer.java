package org.hinsteny.jvm.serialization.json.custom;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;

/**
 * @author Hinsteny
 * @version $ID: OrderStateEnumDeserializer 2018-07-13 16:44 All rights reserved.$
 */
public class OrderStateEnumDeserializer implements ObjectDeserializer {

    /**
     * fastjson invokes this call-back method during deserialization when it encounters a field of the
     * specified type.
     * <p>In the implementation of this call-back method, you should consider invoking
     * {@link JSON#parseObject(String, Type, Feature[])} method to create objects
     * for any non-trivial field of the returned object.
     *
     * @param parser    context DefaultJSONParser being deserialized
     * @param type      The type of the Object to deserialize to
     * @param fieldName parent object field name
     * @return a deserialized object of the specified type which is a subclass of {@code T}
     */
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        Integer intValue = parser.parseObject(int.class);
        if (0 == intValue) {
            return (T) OrderStateEnum.SUCC;
        } else if (1 == intValue) {
            return (T) OrderStateEnum.FAIL;
        }
        throw new IllegalStateException();
    }

    @Override
    public int getFastMatchToken() {
        return JSONToken.LITERAL_INT;
    }
}
