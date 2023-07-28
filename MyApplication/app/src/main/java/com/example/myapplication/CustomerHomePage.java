package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class CustomerHomePage extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnCustomerAccount;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home_page);

        btnCustomerAccount = findViewById(R.id.btnCustomerAccount);
        btnCustomerAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnCustomerAccount.getId()){
            startActivity(new Intent(CustomerHomePage.this, CustomerAccountPage.class));
        }

    }
}