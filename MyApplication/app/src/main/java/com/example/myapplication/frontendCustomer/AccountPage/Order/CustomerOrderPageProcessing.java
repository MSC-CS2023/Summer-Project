package com.example.myapplication.frontendCustomer.AccountPage.Order;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.R;

public class CustomerOrderPageProcessing extends AppCompatActivity {

    ImageButton finish;
    ImageButton message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_page_processing);

        finish = findViewById(R.id.btn_finish);
        message = findViewById(R.id.btn_message);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //add this order to finish list
                Toast.makeText(CustomerOrderPageProcessing.this, "add this order to finish list", Toast.LENGTH_SHORT).show();

            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustomerOrderPageProcessing.this, "click message", Toast.LENGTH_SHORT).show();
            }
        });


    }



}