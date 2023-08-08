package com.example.myapplication.frontendProvider.loginPages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.frontendProvider.ProviderMain;

public class ProviderLogin extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnBackMain;
    ImageButton btnProviderLogin;
    TextView txtProviderRegister;
    TextView txtProviderForgetPassword;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_login);

        btnBackMain = findViewById(R.id.btn_back_main);
        btnBackMain.setOnClickListener(this);
        btnProviderLogin = findViewById(R.id.btn_provider_login);
        btnProviderLogin.setOnClickListener(this);
        txtProviderRegister = findViewById(R.id.txt_sign_up);
        txtProviderRegister.setOnClickListener(this);
        txtProviderForgetPassword = findViewById(R.id.txt_forget_password);
        txtProviderForgetPassword.setOnClickListener(this);
    }

    @Override

        public void onClick(View view) {

            if (view.getId() == R.id.btn_back_main){
                Intent intentToMainPage = new Intent(ProviderLogin.this, MainActivity.class);
                startActivity(intentToMainPage);
                finish();
            } else if (view.getId() ==  R.id.btn_provider_login) {
                // to be completed , verifying via database

                Intent intentToProviderMainPage = new Intent(this, ProviderMain.class);
                startActivity(intentToProviderMainPage);
                finish();
                Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
            } else if (view.getId() == R.id.txt_sign_up) {
                // to be completed

                Toast.makeText(getApplicationContext(),"click register", Toast.LENGTH_SHORT).show();
                Intent intentToProviderRegisterPage = new Intent(ProviderLogin.this, ProviderRegister.class);
                startActivity(intentToProviderRegisterPage);
            } else if (view.getId() == R.id.txt_forget_password) {
                // not need to be completed

                Toast.makeText(getApplicationContext(),"click forget password", Toast.LENGTH_SHORT).show();
//                Intent intentToProviderForgetPage = new Intent();
//                finish();
            }

        }
    }