package com.example.myapplication.frontendCustomer.loginPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.R;

public class CustomerForgetPassword extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnCustomerForgetPasswordSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_forget_password);

        btnCustomerForgetPasswordSend = findViewById(R.id.btnForgetPasswordSend);
        btnCustomerForgetPasswordSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnForgetPasswordSend){
            Toast.makeText(this, "verify code sent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CustomerForgetPassword.this, CustomerResetPassword.class));
        }
    }
}