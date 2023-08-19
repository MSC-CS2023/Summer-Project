package com.example.myapplication.frontendCustomer.AccountPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.myapplication.Adapter.OrderTimetableCardAdapter;
import com.example.myapplication.Bean.AdapterData.OrderTimetableCard;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CustomerTimetablePage extends AppCompatActivity {

    CalendarView calendarView;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_timetable_page); 

        calendarView = findViewById(R.id.calender);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(CustomerTimetablePage.this, "you chose" + year + "/" + (month+1) + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();
            }
        });

        updateRecyclerView();

    }

    private void updateRecyclerView() {
        recyclerView = findViewById(R.id.TimetableRecyclerView);
        List<OrderTimetableCard> orderTimetableCards = new ArrayList<>();
        OrderTimetableCard demo1 = new OrderTimetableCard("10:00-12:00","200","furnishing","1111111111");
        OrderTimetableCard demo2 = new OrderTimetableCard("12:00-14:00","200","furnishing","2222111111");
        OrderTimetableCard demo3 = new OrderTimetableCard("14:00-15:00","200","furnishing","1111111111");
        OrderTimetableCard demo4 = new OrderTimetableCard("15:00-17:00","200","furnishing","1111111111");

        orderTimetableCards.add(demo1);
        orderTimetableCards.add(demo2);
        orderTimetableCards.add(demo3);
        orderTimetableCards.add(demo4);

        OrderTimetableCardAdapter adapter = new OrderTimetableCardAdapter(orderTimetableCards);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}