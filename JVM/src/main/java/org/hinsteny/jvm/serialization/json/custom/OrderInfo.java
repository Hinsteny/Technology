package org.hinsteny.jvm.serialization.json.custom;

/**
 * @author Hinsteny
 * @version $ID: OrderInfo 2018-07-13 16:43 All rights reserved.$
 */
public class OrderInfo {

    public String orderNo;

    public OrderStateEnum state;

    public OrderInfo(String orderNo, OrderStateEnum state) {
        this.orderNo = orderNo;
        this.state = state;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public OrderStateEnum getState() {
        return state;
    }

    public void setState(OrderStateEnum state) {
        this.state = state;
    }
}
