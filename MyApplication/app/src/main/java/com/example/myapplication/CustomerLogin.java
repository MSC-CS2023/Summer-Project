package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerLogin extends AppCompatActivity implements View.OnClickListener {


    ImageButton btnCustomerLogin;
    TextView txtCustomerForgetPassword;
    TextView txtCustomerRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        txtCustomerRegister = findViewById(R.id.txtCustomerRegister);
        txtCustomerRegister.setOnClickListener(this);
        txtCustomerForgetPassword = findViewById(R.id.txtCustomerForgetPassword);
        txtCustomerForgetPassword.setOnClickListener(this);
        btnCustomerLogin = findViewById(R.id.btnCustomerLogin);
        btnCustomerLogin.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.txtCustomerForgetPassword){
            Toast.makeText(this, "forget password", Toast.LENGTH_SHORT).show();
//            Intent intentToMainPage = new Intent(CustomerLogin.this, MainActivity.class);
//            startActivity(intentToMainPage);
        } else if (view.getId() ==  R.id.btnCustomerLogin) {
            // to be complete , verifying via database

            startActivity(new Intent(CustomerLogin.this, CustomerHomePage.class));


            Toast.makeText(getApplicationContext(), "click login", Toast.LENGTH_SHORT).show();
        }
        else if (view.getId() == R.id.txtCustomerRegister) {
            // to be complete

            Toast.makeText(getApplicationContext(),"click register", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CustomerLogin.this, CustomerRegister.class));

        }

    }
}