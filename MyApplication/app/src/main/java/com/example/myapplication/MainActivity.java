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
import com.example.myapplication.network.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

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

//        accountInitializer();
    }

    @Override

    public void onClick(View view) {
        if (view.getId() == R.id.btnCustomer) {
            Intent intentCustomer = new Intent(MainActivity.this, CustomerLogin.class);
            startActivity(intentCustomer);
            finish();
        } else if (view.getId() == R.id.btnProvider) {
            Intent intentProvider = new Intent(MainActivity.this, ProviderLogin.class);
            startActivity(intentProvider);
        }
    }

    //Auto login function
    private void accountInitializer(){
        SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        if(sp.getBoolean("isLoggedIn", false)){
            switch (sp.getString("userType", "none")){
                case "customer" :
                    customerReLogin(sp.getString("token", null));
                    break;
                case "provider" :
                    providerReLogin(sp.getString("token", null));
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Please Login again.", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @SuppressLint("CheckResult")
    private void customerReLogin(String token){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.customerReLogin(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<LoginData>>() {
                    @Override
                    public void onNext(HttpBaseBean<LoginData> loginDataHttpBaseBean) {
                        if(loginDataHttpBaseBean.getSuccess()){
                            Toast.makeText(getApplicationContext(),
                                    loginDataHttpBaseBean.getData().getUser().getUsername() + " login successfully",
                                    Toast.LENGTH_SHORT).show();
                            SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
                            sp.edit().putBoolean("isLoggedIn", true).apply();
                            sp.edit().putString("userType", "customer").apply();
                            sp.edit().putString("token", loginDataHttpBaseBean.getData().getToken()).apply();
                            sp.edit().putLong("exp", loginDataHttpBaseBean.getData().getExp()).apply();
                            startActivity(new Intent(MainActivity.this, CustomerMainActivity.class)
                                    .putExtra("User", loginDataHttpBaseBean.getData().getUser()));
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Please Login again.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "Network error! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void providerReLogin(String token){

    }

}