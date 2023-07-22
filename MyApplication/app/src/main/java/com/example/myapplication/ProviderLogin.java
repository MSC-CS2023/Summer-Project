package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class ProviderLogin extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnBackProvider;
    Button btnProviderLogin;
    Button btnProviderRegister;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_login);

        btnBackProvider = findViewById(R.id.btnBackProvider);
        btnBackProvider.setOnClickListener(this);
        btnProviderLogin = findViewById(R.id.btnProviderLogin);
        btnProviderLogin.setOnClickListener(this);
        btnProviderRegister = findViewById(R.id.btnProviderRegister);
        btnProviderRegister.setOnClickListener(this);
    }

    @Override

        public void onClick(View view) {

            if (view.getId() == R.id.btnBackProvider){
                Intent intentToMainPage = new Intent(ProviderLogin.this, MainActivity.class);
                startActivity(intentToMainPage);
            } else if (view.getId() ==  R.id.btnProviderLogin) {
                // to be complete , verifying via database


                Toast.makeText(getApplicationContext(), "click login", Toast.LENGTH_SHORT).show();
            } else if (view.getId() == R.id.btnProviderRegister) {
                // to be complete

                Toast.makeText(getApplicationContext(),"click register", Toast.LENGTH_SHORT).show();
                Intent intentToProviderRegisterPage = new Intent(ProviderLogin.this,ProviderRegister.class);
                startActivity(intentToProviderRegisterPage);
            }

        }
    }