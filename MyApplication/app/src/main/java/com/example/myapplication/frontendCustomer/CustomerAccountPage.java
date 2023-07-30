package com.example.myapplication.frontendCustomer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.myapplication.R;

public class CustomerAccountPage extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnCustomerAccount;
    ImageButton btnCustomerCollection;
    ImageButton btnCustomerMessage;
    ImageButton btnCustomerHomePage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_account_page);

        btnCustomerAccount = findViewById(R.id.btnCustomerAccount4);
        btnCustomerAccount.setOnClickListener(this);
        btnCustomerCollection = findViewById(R.id.btnCustomerCollection4);
        btnCustomerCollection.setOnClickListener(this);
        btnCustomerMessage = findViewById(R.id.btnCustomerMessage4);
        btnCustomerMessage.setOnClickListener(this);
        btnCustomerHomePage = findViewById(R.id.btnCustomerHomepage4);
        btnCustomerHomePage.setOnClickListener(this);


    }




    public void onClick(View view) {
        if (view.getId() == btnCustomerAccount.getId()){
//            startActivity(new Intent(CustomerAccountPage.this, CustomerAccountPage.class));
        }else if (view.getId() == btnCustomerCollection.getId()) {
            startActivity(new Intent(CustomerAccountPage.this, CustomerCollection.class));
        }else if (view.getId() == btnCustomerMessage.getId()) {
            startActivity(new Intent(CustomerAccountPage.this, CustomerMessageList.class));
        }else if (view.getId() == btnCustomerHomePage.getId()) {
            startActivity(new Intent(CustomerAccountPage.this, CustomerHomePage.class));
        }

    }


}