package com.example.myapplication.Bean.AdapterData;

import com.example.myapplication.Bean.Httpdata.Order;

public class OrderCard {
    private String orderSate;
    private String orderId;
    private String orderTitle;
    private String orderPrice;
    private String orderPictureSrc;


    public OrderCard(String orderSate, String orderId, String orderTitle, String orderPrice, String orderPictureSrc){
        this.orderSate = orderSate;
        this.orderId = orderId;
        this.orderTitle = orderTitle;
        this.orderPrice = orderPrice;
        this.orderPictureSrc = orderPictureSrc;
    }

    public String getOrderSate() {
        return orderSate;
    }

    public void setOrderSate(String orderSate) {
        this.orderSate = orderSate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderPictureSrc() {
        return orderPictureSrc;
    }

    public void setOrderPictureSrc(String orderPictureSrc) {
        this.orderPictureSrc = orderPictureSrc;
    }
}
