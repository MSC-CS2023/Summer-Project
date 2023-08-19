package com.example.myapplication.frontendCustomer.HomePage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.myapplication.R;

import java.util.Calendar;

public class CustomerMakeOrderPage extends AppCompatActivity implements View.OnClickListener {

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
            openDateDialog(startDate);
        } else if (v.getId() == R.id.selectStartTime) {
            openTimeDialog(startTime);
        } else if (v.getId() == R.id.selectEndTime) {
            openTimeDialog(endTime);
        } else if (v.getId() == R.id.pay) {
            paymentAlert();
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
//                        createCustomerOrder(token, serviceId, System.currentTimeMillis() + 36000000,
//                                System.currentTimeMillis() + 39600000);
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


    private void openDateDialog(TextView textView) {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                textView.setText(String.valueOf(year) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(dayOfMonth));
            }
        }, 2023, 0, 25);
        dialog.show();
    }

    private void openTimeDialog(TextView textView) {
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                textView.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
            }
        },15,00,true);
        dialog.show();
    }
}
