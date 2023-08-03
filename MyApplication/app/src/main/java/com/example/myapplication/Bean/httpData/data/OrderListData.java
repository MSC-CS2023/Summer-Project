<<<<<<<< Updated upstream:MyApplication/app/src/main/java/com/example/myapplication/Bean/Httpdata/data/OrderListData.java
package com.example.myapplication.Bean.Httpdata.data;

import com.example.myapplication.Bean.Httpdata.Order;
========
package com.example.myapplication.Bean.httpData.data;

import com.example.myapplication.Bean.httpData.Order;
>>>>>>>> Stashed changes:MyApplication/app/src/main/java/com/example/myapplication/Bean/httpData/data/OrderListData.java

import java.util.List;

public class OrderListData {
    private List<Order> bookingOrders;

    public List<Order> getBookingOrders() { return bookingOrders; }
    public void setBookingOrders(List<Order> value) { this.bookingOrders = value; }
}
