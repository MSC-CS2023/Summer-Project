package com.example.myapplication.frontendProvider.loginPages;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.R;

public class ProviderRegister extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnRegisterProviderBack;
    ImageButton btnRegisterProviderUploadPortrait;
    ImageButton btnRegisterProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_register);

        btnRegisterProviderBack = findViewById(R.id.btn_back_login);
        btnRegisterProviderBack.setOnClickListener(this);
        btnRegisterProviderUploadPortrait = findViewById(R.id.btn_upload_portrait);
        btnRegisterProviderUploadPortrait.setOnClickListener(this);
        btnRegisterProvider = findViewById(R.id.btn_provider_register);
        btnRegisterProvider.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_back_login) {
            Intent intentToProviderLoginPage = new Intent(ProviderRegister.this, ProviderLogin.class);
            startActivity(intentToProviderLoginPage);
            finish();
        } else if (view.getId() == R.id.btn_upload_portrait) {
            // to be completed

            Toast.makeText(this, "Upload portrait", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.btn_provider_register) {
            Toast.makeText(this, "Register successfully", Toast.LENGTH_SHORT).show();
            Intent intentToProviderLoginPage = new Intent(ProviderRegister.this, ProviderLogin.class);
            startActivity(intentToProviderLoginPage);
            finish();
        }

    }
}