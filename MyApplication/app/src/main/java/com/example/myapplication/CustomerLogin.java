package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class CustomerLogin extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnBackCustomer;
    Button btnCustomerLogin;
    Button btnCustomerRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);


        btnBackCustomer = findViewById(R.id.btnBackCustomer);
        btnBackCustomer.setOnClickListener(this);
        btnCustomerLogin = findViewById(R.id.btnCustomerLogin);
        btnCustomerLogin.setOnClickListener(this);
        btnCustomerRegister = findViewById(R.id.btnCustomerRegister);
        btnCustomerRegister.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnBackCustomer){
            Intent intentToMainPage = new Intent(CustomerLogin.this, MainActivity.class);
            startActivity(intentToMainPage);
        } else if (view.getId() ==  R.id.btnCustomerLogin) {
            // to be complete , verifying via database

            startActivity(new Intent(CustomerLogin.this, CustomerHomePage.class));


            Toast.makeText(getApplicationContext(), "click login", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.btnCustomerRegister) {
            // to be complete

            Toast.makeText(getApplicationContext(),"click register", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CustomerLogin.this, CustomerRegister.class));

        }

    }
}