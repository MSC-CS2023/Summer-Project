package com.example.myapplication.frontendProvider.profilePages;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.ChooseActivity;
import com.example.myapplication.R;
import com.example.myapplication.frontendCustomer.loginPage.CustomerLogin;

public class ProviderSettingActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private ImageButton accountSecurity;
    private ImageButton notification;
    private ImageButton resetPassword;
    private ImageButton help;
    private ImageButton deleteAccount;
    private ImageButton logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_setting);

        initView();
    }

    private void initView() {
        accountSecurity = findViewById(R.id.personalDetails);
        accountSecurity.setOnClickListener(this);
        notification = findViewById(R.id.notification);
        notification.setOnClickListener(this);
        help = findViewById(R.id.help);
        help.setOnClickListener(this);
        resetPassword = findViewById(R.id.reset_password);
        resetPassword.setOnClickListener(this);
        deleteAccount = findViewById(R.id.delete_account);
        deleteAccount.setOnClickListener(this);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.personalDetails) {
            Intent intentToAccountSecurity = new Intent(this, ProviderProfileInformationActivity.class);
            startActivity(intentToAccountSecurity);
        } else if(view.getId() == R.id.notification) {
            Intent intentToNotification = new Intent(this, ProviderNotificationActivity.class);
            startActivity(intentToNotification);
        } else if(view.getId() == R.id.reset_password) {
            Intent intentToResetPassword = new Intent(this, ProviderResetPasswordActivity.class);
            startActivity(intentToResetPassword);
        } else if(view.getId() == R.id.help) {
            Toast.makeText(this, "Help clicked", Toast.LENGTH_SHORT).show();
        } else if(view.getId() == R.id.delete_account) {
            deleteAlert();
        } else if(view.getId() == R.id.logout) {
            logout();
        }
    }

    private void deleteAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning: Delete account!")
                .setMessage("Are you sure to DELETE this account?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //发送请求删除账号
                        Toast.makeText(getApplicationContext(), "Delete", Toast.LENGTH_SHORT).show();
                        finishAffinity();
                        startActivity(new Intent(ProviderSettingActivity.this, ChooseActivity.class));
                    }
                })
                .setNegativeButton("Cancel", null);

        AlertDialog deleteAlert = builder.create();
        deleteAlert.setOnShowListener(new DialogInterface.OnShowListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton = deleteAlert.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = deleteAlert.getButton(AlertDialog.BUTTON_NEGATIVE);

                positiveButton.setTextColor(R.color.black);
                negativeButton.setTextColor(R.color.black);
            }
        });

        deleteAlert.show();
    }

    private void logout(){
        SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        sp.edit().putBoolean("isLoggedIn", false).apply();
        sp.edit().putString("userType", "none").apply();
        sp.edit().putString("token", null).apply();
        sp.edit().putLong("exp", 0).apply();

        finishAffinity();
        Intent intentToStart = new Intent(this, ChooseActivity.class);
        startActivity(intentToStart);
    }
}