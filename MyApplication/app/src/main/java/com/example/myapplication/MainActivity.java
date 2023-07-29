package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.myapplication.frontendCustomer.CustomerLogin;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnCustomer;
    ImageButton btnProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCustomer = findViewById(R.id.btnCustomer);
        btnCustomer.setOnClickListener(this);
        btnProvider = findViewById(R.id.btnProvider);
        btnProvider.setOnClickListener(this);

    }

    @Override

    public void onClick(View view) {
        if (view.getId() == R.id.btnCustomer) {
            Intent intentCustomer = new Intent(MainActivity.this, CustomerLogin.class);
            startActivity(intentCustomer);
        } else if (view.getId() == R.id.btnProvider) {
            Intent intentProvider = new Intent(MainActivity.this, ProviderLogin.class);
            startActivity(intentProvider);
        }
    }

}