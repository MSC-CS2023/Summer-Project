package com.example.myapplication.Bean.AdapterData;

import com.example.myapplication.Bean.Httpdata.Order;

public class OrderCard {
    private String orderSate;
    private Long orderId;
    private String orderTitle;
    private String orderPrice;

    private String pictureLink;
    private int orderPictureSrc;

    public OrderCard(Long orderId, String orderTitle, String orderPrice, String pictureLink, String orderSate) {
        this.orderSate = orderSate;
        this.orderId = orderId;
        this.orderTitle = orderTitle;
        this.orderPrice = orderPrice;
        this.pictureLink = pictureLink;
    }

    public String getOrderSate() {
        return orderSate;
    }

    public void setOrderSate(String orderSate) {
        this.orderSate = orderSate;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
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

    public int getOrderPictureSrc() {
        return orderPictureSrc;
    }

    public void setOrderPictureSrc(int orderPictureSrc) {
        this.orderPictureSrc = orderPictureSrc;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }
}
