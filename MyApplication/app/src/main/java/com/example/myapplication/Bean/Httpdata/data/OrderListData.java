package com.example.myapplication.Bean.Httpdata.data;

import com.example.myapplication.Bean.Httpdata.Order;

import java.util.List;

public class OrderListData {
    private List<Order> booking_orders;

    public List<Order> getBookingOrders() { return booking_orders; }
    public void setBookingOrders(List<Order> value) { this.booking_orders = value; }
}
