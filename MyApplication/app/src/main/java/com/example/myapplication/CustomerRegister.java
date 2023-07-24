package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class CustomerRegister extends AppCompatActivity implements View.OnClickListener {

    Button btnRegisterCustomer;
    ImageButton btnRegisterCustomerBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

        btnRegisterCustomer = findViewById(R.id.btnRegisterCustomer);
        btnRegisterCustomer.setOnClickListener(this);
        btnRegisterCustomerBack = findViewById(R.id.btnRegisterCustomerBack);
        btnRegisterCustomerBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnCustomerRegister){
            // to be completed
            Toast.makeText(this, "Register successfully", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.btnRegisterCustomerBack) {
            Intent intentToCustomerLoginPage = new Intent(CustomerRegister.this, CustomerLogin.class);
            startActivity(intentToCustomerLoginPage);
        }

    }
}