package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.data.LoginData;
import com.example.myapplication.frontendCustomer.CustomerMainActivity;
import com.example.myapplication.frontendProvider.ProviderMain;
import com.example.myapplication.network.CustomerApi;
import com.example.myapplication.network.ProviderApi;
import com.example.myapplication.network.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accountInitializer();
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
                    startActivity(new Intent(MainActivity.this,ChooseActivity.class));
                    finish();
                    break;
            }
        }else {
            startActivity(new Intent(MainActivity.this,ChooseActivity.class));
            finish();
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
                            try {
                                Toast.makeText(getApplicationContext(),
                                        loginDataHttpBaseBean.getData().getUser().getUsername() + " login successfully",
                                        Toast.LENGTH_SHORT).show();
                                SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
                                sp.edit().putBoolean("isLoggedIn", true).apply();
                                sp.edit().putString("userType", "customer").apply();
                                sp.edit().putString("token", loginDataHttpBaseBean.getData().getToken()).apply();
                                sp.edit().putLong("exp", loginDataHttpBaseBean.getData().getExp()).apply();
                                startActivity(new Intent(MainActivity.this, CustomerMainActivity.class));
                                finish();
                            }catch (Exception e){
                                Toast.makeText(getApplicationContext(),
                                        "Please Login again.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this,ChooseActivity.class));
                                finish();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Please Login again.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,ChooseActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "Network error! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,ChooseActivity.class));
                        finish();
                    }
                    @Override
                    public void onComplete() {}
                });
    }

    @SuppressLint("CheckResult")
    private void providerReLogin(String token){
        ProviderApi providerApi = RetrofitClient.getInstance().getService(ProviderApi.class);
        providerApi.providerReLogin(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<LoginData>>() {
                    @Override
                    public void onNext(HttpBaseBean<LoginData> loginDataHttpBaseBean) {
                        if(loginDataHttpBaseBean.getSuccess()){
                            try {
                                Toast.makeText(getApplicationContext(),
                                        loginDataHttpBaseBean.getData().getUser().getUsername() + " login successfully",
                                        Toast.LENGTH_SHORT).show();
                                SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
                                sp.edit().putBoolean("isLoggedIn", true).apply();
                                sp.edit().putString("userType", "provider").apply();
                                sp.edit().putString("token", loginDataHttpBaseBean.getData().getToken()).apply();
                                sp.edit().putLong("exp", loginDataHttpBaseBean.getData().getExp()).apply();
                                startActivity(new Intent(MainActivity.this, ProviderMain.class));
                                finish();
                            }catch (Exception e){
                                Toast.makeText(getApplicationContext(),
                                        "Please Login again.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this,ChooseActivity.class));
                                finish();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Please Login again.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,ChooseActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "Network error! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,ChooseActivity.class));
                        finish();
                    }
                    @Override
                    public void onComplete() {}
                });
    }
}