package com.example.myapplication.frontendCustomer.loginPage;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Bean.HttpBaseBean;
import com.example.myapplication.Bean.User;
import com.example.myapplication.Bean.data.LoginData;
import com.example.myapplication.R;
import com.example.myapplication.frontendCustomer.CustomerMainActivity;
import com.example.myapplication.network.PublicMethodApi;
import com.example.myapplication.network.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class CustomerLogin extends AppCompatActivity implements View.OnClickListener {


    ImageButton btnCustomerLogin;
    TextView txtCustomerForgetPassword;
    TextView txtCustomerRegister;
    EditText txtCustomerUsername;
    EditText txtCustomerPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        txtCustomerRegister = findViewById(R.id.txtCustomerRegister);
        txtCustomerRegister.setOnClickListener(this);
        txtCustomerForgetPassword = findViewById(R.id.txtCustomerForgetPassword);
        txtCustomerForgetPassword.setOnClickListener(this);
        btnCustomerLogin = findViewById(R.id.btnCustomerLogin);
        btnCustomerLogin.setOnClickListener(this);
        txtCustomerUsername = findViewById(R.id.txtCustomerUsername);
        txtCustomerPassword = findViewById(R.id.txtCustomerPassword);


    }



    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txtCustomerForgetPassword){
            Toast.makeText(this, "forget password", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CustomerLogin.this, CustomerForgetPassword.class));
        } else if (view.getId() ==  R.id.btnCustomerLogin) {
            customerLogin();
//            startActivity(new Intent(CustomerLogin.this, CustomerMainActivity.class));
        }
        else if (view.getId() == R.id.txtCustomerRegister) {
            startActivity(new Intent(CustomerLogin.this, CustomerRegister.class));
        }

    }


    @SuppressLint("CheckResult")
    private void customerLogin(){
        PublicMethodApi httpApi = RetrofitClient.getInstance().getService(PublicMethodApi.class);
        httpApi.customerLogin(txtCustomerUsername.getText().toString(),txtCustomerPassword.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<LoginData>>() {
                    @Override
                    public void onNext(HttpBaseBean<LoginData> loginDataHttpBaseBean) {
                        if(loginDataHttpBaseBean.getSuccess()){
                            Toast.makeText(getApplicationContext(),
                                    loginDataHttpBaseBean.getData().getUser().getUsername() + " login successfully",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(CustomerLogin.this, CustomerMainActivity.class)
                                    .putExtra("User", loginDataHttpBaseBean.getData().getUser())
                                    .putExtra("token", loginDataHttpBaseBean.getData().getToken())
                                    .putExtra("exp", loginDataHttpBaseBean.getData().getExp()));
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    loginDataHttpBaseBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "Something wrong" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}