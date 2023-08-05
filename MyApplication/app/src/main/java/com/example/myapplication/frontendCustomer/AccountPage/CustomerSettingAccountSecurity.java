package com.example.myapplication.frontendCustomer.AccountPage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;

public class CustomerSettingAccountSecurity extends AppCompatActivity implements View.OnClickListener {

      ImageButton AS_username;
      ImageButton AS_phone;
      ImageButton AS_email;
      ImageButton As_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_setting_account_seurity);

        setToolBar();
        initializeButton();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.AS_username) {
            startActivity(new Intent(CustomerSettingAccountSecurity.this, CustomerResetUsername.class));
        } else if (view.getId() == R.id.AS_phone) {
            startActivity(new Intent(CustomerSettingAccountSecurity.this, CustomerResetPhone.class));
        }else if (view.getId() == R.id.AS_email) {
            startActivity(new Intent(CustomerSettingAccountSecurity.this, CustomerResetEmail.class));
        }else if (view.getId() == R.id.AS_password) {
            startActivity(new Intent(CustomerSettingAccountSecurity.this, CustomerResetPassword.class));
        }

    }



    public void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // back to previous page
            }
        });
    }

    private void initializeButton() {
        AS_username = findViewById(R.id.AS_username);
        AS_phone = findViewById(R.id.AS_phone);
        AS_email = findViewById(R.id.AS_email);
        As_password = findViewById(R.id.AS_password);

        AS_username.setOnClickListener(this);
        AS_phone.setOnClickListener(this);
        AS_email.setOnClickListener(this);
        As_password.setOnClickListener(this);

    }

}

