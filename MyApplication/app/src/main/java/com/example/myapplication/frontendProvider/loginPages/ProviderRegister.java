package com.example.myapplication.frontendProvider.loginPages;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.data.LoginData;
import com.example.myapplication.R;
import com.example.myapplication.frontendCustomer.CustomerMainActivity;
import com.example.myapplication.frontendCustomer.loginPage.CustomerRegister;
import com.example.myapplication.network.PublicMethodApi;
import com.example.myapplication.network.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class ProviderRegister extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnRegisterProviderBack;
    ImageButton btnRegisterProviderUploadPortrait;
    ImageButton btnRegisterProvider;

    EditText txtRegisterProviderName;
    EditText txtRegisterProviderNumber;
    EditText txtRegisterProviderEmail;
    EditText txtRegisterProviderPassword1;
    EditText txtRegisterProviderPassword2;
    EditText txtRegisterProviderAddress;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_register);

        btnRegisterProviderBack = findViewById(R.id.btn_back_login);
        btnRegisterProviderBack.setOnClickListener(this);
        btnRegisterProviderUploadPortrait = findViewById(R.id.btn_upload_portrait);
        btnRegisterProviderUploadPortrait.setOnClickListener(this);
        btnRegisterProvider = findViewById(R.id.btn_provider_register);
        btnRegisterProvider.setOnClickListener(this);

        txtRegisterProviderName = findViewById(R.id.txtRegisterProviderName);
        txtRegisterProviderNumber = findViewById(R.id.txtRegisterProviderNumber);
        txtRegisterProviderEmail = findViewById(R.id.txtRegisterProviderEmail);
        txtRegisterProviderPassword1 = findViewById(R.id.txtRegisterProviderPassword1);
        txtRegisterProviderPassword2 = findViewById(R.id.txtRegisterProviderPassword2);
        txtRegisterProviderAddress = findViewById(R.id.txtRegisterProviderAddress);

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_back_login) {
            Intent intentToProviderLoginPage = new Intent(ProviderRegister.this, ProviderLogin.class);
            startActivity(intentToProviderLoginPage);
            finish();
        } else if (view.getId() == R.id.btn_upload_portrait) {
            // to be completed

            Toast.makeText(this, "Upload portrait", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.btn_provider_register) {
            if(checkRegisterDetail()){
                providerRegister(
                        txtRegisterProviderEmail.getText().toString(), txtRegisterProviderName.getText().toString(),
                        txtRegisterProviderPassword1.getText().toString(), txtRegisterProviderAddress.getText().toString(),
                        txtRegisterProviderNumber.getText().toString());
            }
        }

    }

    private Boolean checkRegisterDetail(){
        if(txtRegisterProviderName.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Username is mandatory!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(txtRegisterProviderEmail.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Email is mandatory!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(txtRegisterProviderPassword1.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Password is mandatory!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!txtRegisterProviderPassword1.getText().toString()
                .equals(txtRegisterProviderPassword2.getText().toString())){
            Toast.makeText(getApplicationContext(), "Entered passwords differ!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @SuppressLint("CheckResult")
    private void providerRegister(String email, String username, String password, String address, String tel){
        address = address.equals("")? null : address;
        tel = tel.equals("")? null : tel;

        PublicMethodApi publicMethodApi = RetrofitClient.getInstance().getService(PublicMethodApi.class);
        publicMethodApi.providerRegister(email, username, password, address, tel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<LoginData>>() {
                    @Override
                    public void onNext(HttpBaseBean<LoginData> loginDataHttpBaseBean) {
                        if(loginDataHttpBaseBean.getSuccess()){
                            Toast.makeText(getApplicationContext(),
                                    loginDataHttpBaseBean.getData().getUser().getUsername() + "sign up successfully",
                                    Toast.LENGTH_SHORT).show();
                            SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
                            sp.edit().putBoolean("isLoggedIn", true).apply();
                            sp.edit().putString("userType", "provider").apply();
                            sp.edit().putString("token", loginDataHttpBaseBean.getData().getToken()).apply();
                            sp.edit().putLong("exp", loginDataHttpBaseBean.getData().getExp()).apply();
                            startActivity(new Intent(ProviderRegister.this, CustomerMainActivity.class));
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    loginDataHttpBaseBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}