package com.example.myapplication.frontendProvider.profilePages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.ChooseActivity;
import com.example.myapplication.R;

public class ProviderSettingActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private ImageButton accountSecurity;
    private ImageButton notification;
    private ImageButton help;
    private ImageButton about;
    private ImageButton resetPassword;
    private ImageButton logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_setting);

        initView();
    }

    private void initView() {
        accountSecurity = findViewById(R.id.accountSecurity);
        accountSecurity.setOnClickListener(this);
        notification = findViewById(R.id.notification);
        notification.setOnClickListener(this);
        help = findViewById(R.id.help);
        help.setOnClickListener(this);
        about = findViewById(R.id.about);
        about.setOnClickListener(this);
        resetPassword = findViewById(R.id.reset_password);
        resetPassword.setOnClickListener(this);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.accountSecurity) {
            Intent intentToAccountSecurity = new Intent(this, ProviderProfileInformationActivity.class);
            startActivity(intentToAccountSecurity);
        } else if(view.getId() == R.id.notification) {
            Intent intentToNotification = new Intent(this, ProviderNotificationActivity.class);
            startActivity(intentToNotification);
        } else if(view.getId() == R.id.help) {
            Toast.makeText(this, "Help clicked", Toast.LENGTH_SHORT).show();
        } else if(view.getId() == R.id.about) {
            Toast.makeText(this, "About clicked", Toast.LENGTH_SHORT).show();
        } else if(view.getId() == R.id.reset_password) {
            Intent intentToResetPassword = new Intent(this, ProviderResetPasswordActivity.class);
            startActivity(intentToResetPassword);
        } else if(view.getId() == R.id.logout) {
            finishAffinity();
            Intent intentToStart = new Intent(this, ChooseActivity.class);
            startActivity(intentToStart);
        }
    }
}