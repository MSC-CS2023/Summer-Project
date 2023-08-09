package com.example.myapplication.frontendCustomer.AccountPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.myapplication.R;
import com.example.myapplication.frontendCustomer.loginPage.CustomerLogin;

public class CustomerSettingPage extends AppCompatActivity implements View.OnClickListener {

    ImageButton btn_accountSecurity;
    ImageButton btn_notification;
    ImageButton btn_help;
    ImageButton btn_about;
    ImageButton btn_switch;
    ImageButton btn_logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_setting_page);

        btn_accountSecurity = findViewById(R.id.accountSecurity);
        btn_notification = findViewById(R.id.notification);
        btn_help = findViewById(R.id.help);
        btn_about = findViewById(R.id.about);
        btn_switch = findViewById(R.id.switchAccount);
        btn_logout = findViewById(R.id.logout);

        btn_accountSecurity.setOnClickListener(this);
        btn_notification.setOnClickListener(this);
        btn_help.setOnClickListener(this);
        btn_about.setOnClickListener(this);
        btn_switch.setOnClickListener(this);
        btn_logout.setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // back to previous page
            }
        });


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.accountSecurity){
            startActivity(new Intent(CustomerSettingPage.this, CustomerSettingAccountSecurity.class));
        } else if (view.getId() == R.id.notification) {
            startActivity(new Intent(this, CustomerSettingNotification.class));
        }else if (view.getId() == R.id.help) {
            startActivity(new Intent(this, CustomerSettingHelp.class));
        }else if (view.getId() == R.id.about) {
            startActivity(new Intent(this, CustomerSettingAbout.class));
        }else if (view.getId() == R.id.switchAccount) {
//            startActivity(new Intent(this, CustomerSettingNotification.class));
        }else if (view.getId() == R.id.logout) {
            logout();
        }

    }

    private void logout(){
        SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        sp.edit().putBoolean("isLoggedIn", false).apply();
        sp.edit().putString("userType", "none").apply();
        sp.edit().putString("token", null).apply();
        sp.edit().putLong("exp", 0).apply();


        Intent intent = new Intent(this, CustomerLogin.class);
        //finish the other activities
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}