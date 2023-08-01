package com.example.myapplication.Bean.data;

import com.example.myapplication.Bean.Order;

import java.util.List;

public class OrderListData {
    private List<Order> bookingOrders;

    public List<Order> getBookingOrders() { return bookingOrders; }
    public void setBookingOrders(List<Order> value) { this.bookingOrders = value; }
}
