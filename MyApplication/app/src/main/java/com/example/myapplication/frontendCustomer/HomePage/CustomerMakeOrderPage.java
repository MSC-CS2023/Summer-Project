package com.example.myapplication.frontendCustomer.HomePage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.data.OrderData;
import com.example.myapplication.R;
import com.example.myapplication.network.CustomerApi;
import com.example.myapplication.network.RetrofitClient;

import java.util.Calendar;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class CustomerMakeOrderPage extends AppCompatActivity implements View.OnClickListener {

    private String token;
    private Long serviceId;

    private Integer serviceYear;
    private Integer serviceMonth;
    private Integer serviceDay;
    private Integer serviceStartHour;
    private Integer serviceStartMinute;
    private Integer serviceEndHour;
    private Integer serviceEndMinute;

    private Long startTimeMills;
    private Long endTimeMills;

    private TextView startDate;
    private TextView startTime;

    private TextView endTime;


    Button selectStartDate;
    Button selectStartTime;
    Button selectEndTime;
    Button pay;


    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_make_order_page);

        SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        this.token = sp.getString("token", "");
        this.serviceId = getIntent().getLongExtra("serviceId", 0);
        serviceYear = 0;
        serviceStartHour = -1;
        serviceEndHour = -1;


        initializeVIew();
    }

    private void initializeVIew() {
        startDate = findViewById(R.id.startDate);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);

        selectStartDate = findViewById(R.id.selectStartDate);
        selectStartTime = findViewById(R.id.selectStartTime);
        selectEndTime = findViewById(R.id.selectEndTime);
        pay = findViewById(R.id.pay);

        selectStartDate.setOnClickListener(this);
        selectStartTime.setOnClickListener(this);
        selectEndTime.setOnClickListener(this);
        pay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.selectStartDate) {
            openDateDialog();
        } else if (v.getId() == R.id.selectStartTime) {
            openStartTimeDialog();
        } else if (v.getId() == R.id.selectEndTime) {
            openEndTimeDialog();
        } else if (v.getId() == R.id.pay) {
            if(checkTime()){
                paymentAlert();
            }
        }
    }

    private void paymentAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerMakeOrderPage.this);

        builder.setTitle("Are you sure to pay ?")
                .setMessage("You need to pay" )
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //update money
                        createCustomerOrder(token, serviceId, startTimeMills, endTimeMills);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", null);

        AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                positiveButton.setTextColor(R.color.black);
                negativeButton.setTextColor(R.color.black);
            }
        });
        alertDialog.show();
    }


    private void openDateDialog() {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectStartDate.setText(String.valueOf(year) + "/" + String.valueOf(month + 1) +
                        "/" + String.valueOf(dayOfMonth));
                serviceYear = year;
                serviceMonth = month;
                serviceDay = dayOfMonth;
            }
        }, 2023, 0, 25);
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dialog.show();
    }

    private void openStartTimeDialog() {
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                selectStartTime.setText(String.valueOf(hourOfDay) + ":" +
                        (minute < 10 ? "0" + String.valueOf(minute) : String.valueOf(minute)));
                serviceStartHour = hourOfDay;
                serviceStartMinute = minute;
            }
        },15,00,true);
        dialog.show();
    }

    private void openEndTimeDialog() {
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                selectEndTime.setText(String.valueOf(hourOfDay) + ":" +
                        (minute < 10 ? "0" + String.valueOf(minute) : String.valueOf(minute)));
                serviceEndHour = hourOfDay;
                serviceEndMinute = minute;
            }
        },15,00,true);
        dialog.show();
    }

    private Boolean checkTime(){
        if(serviceYear == 0){
            Toast.makeText(getApplicationContext(),
                    "Please select service date.", Toast.LENGTH_SHORT).show();
            return false;
        }else if(serviceStartHour == -1){
            Toast.makeText(getApplicationContext(),
                    "Please select service start time.", Toast.LENGTH_SHORT).show();
            return false;
        }else if(serviceEndHour == -1){
            Toast.makeText(getApplicationContext(),
                    "Please select service end time.", Toast.LENGTH_SHORT).show();
            return false;
        }
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, serviceYear);
        calendar.set(Calendar.MONTH, serviceMonth);
        calendar.set(Calendar.DAY_OF_MONTH, serviceDay);
        calendar.set(Calendar.HOUR_OF_DAY, serviceStartHour);
        calendar.set(Calendar.MINUTE, serviceStartMinute);
        calendar.set(Calendar.SECOND, 0);
        startTimeMills = calendar.getTimeInMillis();
        calendar.set(Calendar.HOUR_OF_DAY, serviceEndHour);
        calendar.set(Calendar.MINUTE, serviceEndMinute);
        endTimeMills = calendar.getTimeInMillis();
        if(startTimeMills >= endTimeMills){
            Toast.makeText(getApplicationContext(),
                    "Please select correct service time.", Toast.LENGTH_SHORT).show();
            return false;
        }
        Toast.makeText(getApplicationContext(),
                "suc.", Toast.LENGTH_SHORT).show();
        return true;
    }

    //create new order.
    @SuppressLint("CheckResult")
    private void createCustomerOrder(String token, Long serviceId, Long startTime, Long endTime){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.createCustomerOrder(token, serviceId, startTime, endTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<OrderData>>() {
                    @Override
                    public void onNext(HttpBaseBean<OrderData> orderDataHttpBaseBean) {
                        if(orderDataHttpBaseBean.getSuccess()){
                            Toast.makeText(getApplicationContext(),
                                    "Book service successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "Network error! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {}
                });
    }

}
