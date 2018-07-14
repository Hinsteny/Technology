package org.hinsteny.jvm.serialization.json.custom;

/**
 * order state class
 * @author Hinsteny
 * @version $ID: OrderStateEnum 2018-07-13 16:42 All rights reserved.$
 */
public enum OrderStateEnum {

    FAIL(1), SUCC(0);

    private int code;

    OrderStateEnum(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
