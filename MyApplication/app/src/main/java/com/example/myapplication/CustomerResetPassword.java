package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class CustomerResetPassword extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnCustomerResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_reset_password);

        btnCustomerResetPassword = findViewById(R.id.btnCustomerResetPassword);
        btnCustomerResetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnCustomerResetPassword){
            Toast.makeText(this, "password reset successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CustomerResetPassword.this,CustomerLogin.class));
        }

    }
}