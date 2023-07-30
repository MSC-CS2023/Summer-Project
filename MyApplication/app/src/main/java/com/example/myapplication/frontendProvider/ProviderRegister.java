package com.example.myapplication.frontendProvider;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.R;

public class ProviderRegister extends AppCompatActivity implements View.OnClickListener {

    Button btnRegisterProvider;
    ImageButton btnRegisterProviderBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_register);

        btnRegisterProvider = findViewById(R.id.btnRegisterProvider);
        btnRegisterProvider.setOnClickListener(this);
        btnRegisterProviderBack = findViewById(R.id.btnRegisterProviderBack);
        btnRegisterProviderBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnRegisterProvider){
            // to be completed
            Toast.makeText(this, "Register successfully", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.btnRegisterProviderBack) {
            Intent intentToProviderLoginPage = new Intent(ProviderRegister.this, ProviderLogin.class);
            startActivity(intentToProviderLoginPage);
        }

    }
}