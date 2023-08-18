package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.data.LoginData;
import com.example.myapplication.frontendCustomer.CustomerMainActivity;
import com.example.myapplication.frontendCustomer.loginPage.CustomerLogin;
import com.example.myapplication.frontendProvider.loginPages.ProviderLogin;
import com.example.myapplication.network.CustomerApi;
import com.example.myapplication.network.ProviderApi;
import com.example.myapplication.network.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class ChooseActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnCustomer;
    ImageButton btnProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        btnCustomer = findViewById(R.id.btnCustomer);
        btnCustomer.setOnClickListener(this);
        btnProvider = findViewById(R.id.btnProvider);
        btnProvider.setOnClickListener(this);
    }

    @Override

    public void onClick(View view) {
        if (view.getId() == R.id.btnCustomer) {
            Intent intentCustomer = new Intent(ChooseActivity.this, CustomerLogin.class);
            startActivity(intentCustomer);
        } else if (view.getId() == R.id.btnProvider) {
            Intent intentProvider = new Intent(ChooseActivity.this, ProviderLogin.class);
            startActivity(intentProvider);
        }
    }
}