package com.example.myapplication.frontendCustomer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.myapplication.R;

public class CustomerHomePage extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnCustomerAccount;
    ImageButton btnCustomerCollection;
    ImageButton btnCustomerMessage;
    ImageButton btnCustomerHomePage;

    LinearLayout serviceDetail1;
    LinearLayout serviceDetail2;
    LinearLayout serviceDetail3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home_page);

        btnCustomerAccount = findViewById(R.id.btnCustomerAccount1);
        btnCustomerAccount.setOnClickListener(this);
        btnCustomerCollection = findViewById(R.id.btnCustomerCollection1);
        btnCustomerCollection.setOnClickListener(this);
        btnCustomerMessage = findViewById(R.id.btnCustomerMessage1);
        btnCustomerMessage.setOnClickListener(this);
        btnCustomerHomePage = findViewById(R.id.btnCustomerHomepage1);
        btnCustomerHomePage.setOnClickListener(this);


        serviceDetail1 = findViewById(R.id.service1);
        serviceDetail1.setOnClickListener(this);
        serviceDetail2 = findViewById(R.id.service2);
        serviceDetail2.setOnClickListener(this);
        serviceDetail3 = findViewById(R.id.service3);
        serviceDetail3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnCustomerAccount.getId()){
            startActivity(new Intent(CustomerHomePage.this, CustomerAccountPage.class));
        }else if (view.getId() == btnCustomerCollection.getId()) {
            startActivity(new Intent(CustomerHomePage.this, CustomerCollection.class));
        }else if (view.getId() == btnCustomerMessage.getId()) {
            startActivity(new Intent(CustomerHomePage.this, CustomerMessage.class));
        }else if (view.getId() == btnCustomerHomePage.getId()) {
//            startActivity(new Intent(CustomerHomePage.this, CustomerCollection.class));
        }else if (view.getId() == serviceDetail1.getId()) {
            startActivity(new Intent(CustomerHomePage.this, CustomerServiceDetailPage.class));
        }else if (view.getId() == serviceDetail2.getId()) {
            startActivity(new Intent(CustomerHomePage.this, CustomerServiceDetailPage.class));
        }else if (view.getId() == serviceDetail3.getId()) {
            startActivity(new Intent(CustomerHomePage.this, CustomerServiceDetailPage.class));
        }

    }
}