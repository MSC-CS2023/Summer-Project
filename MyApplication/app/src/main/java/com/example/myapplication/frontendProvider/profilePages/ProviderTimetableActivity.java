package com.example.myapplication.frontendProvider.profilePages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.myapplication.Adapter.OrderTimetableCardAdapter;
import com.example.myapplication.Bean.AdapterData.OrderTimetableCard;
import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.Order;
import com.example.myapplication.Bean.Httpdata.data.OrderListData;
import com.example.myapplication.R;
import com.example.myapplication.frontendCustomer.AccountPage.CustomerTimetablePage;
import com.example.myapplication.network.CustomerApi;
import com.example.myapplication.network.RetrofitClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class ProviderTimetableActivity extends AppCompatActivity {

    private String token;

    CalendarView calendarView;
    RecyclerView recyclerView;

    List<OrderTimetableCard> orderTimetableCards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_timetable_page);

        SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        this.token = sp.getString("token", "");

        calendarView = findViewById(R.id.calender1);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//                Toast.makeText(ProviderTimetableActivity.this,
//                        "you chose" + year + "/" + (month+1) + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();
                getOrderByDate(token, getTimestamp(dayOfMonth, month, year));
            }
        });

//        createDemo();

    }

    private void createDemo(){
        orderTimetableCards = new ArrayList<>();
        OrderTimetableCard demo1 = new OrderTimetableCard("10:00-12:00","200","furnishing","1111111111");
        OrderTimetableCard demo2 = new OrderTimetableCard("12:00-14:00","200","furnishing","2222111111");
        OrderTimetableCard demo3 = new OrderTimetableCard("14:00-15:00","200","furnishing","1111111111");
        OrderTimetableCard demo4 = new OrderTimetableCard("15:00-17:00","200","furnishing","1111111111");

        orderTimetableCards.add(demo1);
        orderTimetableCards.add(demo2);
        orderTimetableCards.add(demo3);
        orderTimetableCards.add(demo4);
        updateRecyclerView();
    }

    private void updateRecyclerView() {
        recyclerView = findViewById(R.id.TimetableRecyclerView);

        OrderTimetableCardAdapter adapter = new OrderTimetableCardAdapter(orderTimetableCards);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private Long getTimestamp(Integer day, Integer month, Integer year){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    @SuppressLint("CheckResult")
    private void getOrderByDate(String token, Long timestamp){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.getCustomerOrdersByDate(token, timestamp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<OrderListData>>() {
                    @Override
                    public void onNext(HttpBaseBean<OrderListData> orderListDataHttpBaseBean) {
                        if(orderListDataHttpBaseBean.getSuccess()){
                            try {
                                orderTimetableCards = getOrderTimetableCards(
                                        orderListDataHttpBaseBean.getData().getBookingOrders());
                                updateRecyclerView();
                            }catch (NullPointerException ignored){}
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {}
                });
    }

    private List<OrderTimetableCard> getOrderTimetableCards(List<Order> orders){
        List<OrderTimetableCard> list = new ArrayList<>();
        OrderTimetableCard orderTimetableCard;
        for(Order order : orders){
            orderTimetableCard = new OrderTimetableCard(
                    getTime(order.getStartTimestamp()) + "-" + getTime(order.getEndTimestamp()),
                    order.getServiceShort().getFee().toString(), order.getServiceShort().getTitle(),
                    order.getId().toString());
            list.add(orderTimetableCard);
        }
        return list;
    }

    private String getTime(Long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.setTimeInMillis(time);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return hour + ":" + (minute < 10 ? "0" + minute : minute);
    }



}