package com.example.myapplication.frontendCustomer.AccountPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.myapplication.R;

public class CustomerTimetablePage extends AppCompatActivity {

    CalendarView calendarView;

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

    }
}