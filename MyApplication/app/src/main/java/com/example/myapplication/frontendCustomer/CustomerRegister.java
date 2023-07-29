package com.example.myapplication.frontendCustomer;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.R;

public class CustomerRegister extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnRegisterCustomer;
    Button btnRegisterCustomerSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

        btnRegisterCustomer = findViewById(R.id.btnRegisterCustomer);
        btnRegisterCustomer.setOnClickListener(this);
        btnRegisterCustomerSend = findViewById(R.id.btnRegisterCustomerSend);
        btnRegisterCustomerSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnRegisterCustomer){
            // to be completed
            Toast.makeText(this, "Register successfully", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.btnRegisterCustomerSend) {
            Toast.makeText(this, "verify code sent", Toast.LENGTH_SHORT).show();
        }

    }
}