package com.example.myapplication.frontendCustomer.loginPage;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.data.LoginData;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.frontendCustomer.CustomerMainActivity;
import com.example.myapplication.network.PublicMethodApi;
import com.example.myapplication.network.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class CustomerLogin extends AppCompatActivity implements View.OnClickListener {
    ImageButton btnCustomerLogin;
    TextView txtCustomerRegister;
    EditText txtCustomerUsername;
    EditText txtCustomerPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        txtCustomerRegister = findViewById(R.id.txtCustomerRegister);
        txtCustomerRegister.setOnClickListener(this);
        btnCustomerLogin = findViewById(R.id.btnCustomerLogin);
        btnCustomerLogin.setOnClickListener(this);
        txtCustomerUsername = findViewById(R.id.txtCustomerUsername);
        txtCustomerPassword = findViewById(R.id.txtCustomerPassword);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() ==  R.id.btnCustomerLogin) {
            customerLogin(txtCustomerUsername.getText().toString(),txtCustomerPassword.getText().toString());
//            startActivity(new Intent(CustomerLogin.this, CustomerMainActivity.class));
        }
        else if (view.getId() == R.id.txtCustomerRegister) {
            startActivity(new Intent(CustomerLogin.this, CustomerRegister.class));
        }

    }

    @SuppressLint("CheckResult")
    private void customerLogin(String username, String password){
        PublicMethodApi httpApi = RetrofitClient.getInstance().getService(PublicMethodApi.class);
        httpApi.customerLogin(username, password)
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
                                startActivity(new Intent(CustomerLogin.this, CustomerMainActivity.class));
                                finishAffinity();
                            }catch (NullPointerException ignored){}
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    loginDataHttpBaseBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "Network error! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {}
                });
    }
}