package com.example.myapplication.Bean.AdapterData;

public class OrderTimetableCard {

    String time;
    String price;
    String title;
    String orderNum;

    public OrderTimetableCard(String time, String price, String title, String orderNum) {
        this.time = time;
        this.price = price;
        this.title = title;
        this.orderNum = orderNum;
    }

    public OrderTimetableCard() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
