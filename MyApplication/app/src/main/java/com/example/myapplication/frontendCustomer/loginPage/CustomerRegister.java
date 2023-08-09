package com.example.myapplication.frontendCustomer.loginPage;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.data.LoginData;
import com.example.myapplication.R;
import com.example.myapplication.frontendCustomer.CustomerMainActivity;
import com.example.myapplication.network.PublicMethodApi;
import com.example.myapplication.network.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class CustomerRegister extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnRegisterCustomer;
    EditText txtRegisterCustomerName;
    EditText txtRegisterCustomerNumber;
    EditText txtRegisterCustomerEmail;
    EditText txtRegisterCustomerPassword1;
    EditText txtRegisterCustomerPassword2;
    EditText txtRegisterCustomerAddress;

    //No About me now.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

        btnRegisterCustomer = findViewById(R.id.btnRegisterCustomer);
        btnRegisterCustomer.setOnClickListener(this);

        txtRegisterCustomerName = findViewById(R.id.txtRegisterCustomerName);
        txtRegisterCustomerNumber = findViewById(R.id.txtRegisterCustomerNumber);
        txtRegisterCustomerEmail = findViewById(R.id.txtRegisterCustomerEmail);
        txtRegisterCustomerPassword1 = findViewById(R.id.txtRegisterCustomerPassword1);
        txtRegisterCustomerPassword2 = findViewById(R.id.txtRegisterCustomerPassword2);
        txtRegisterCustomerAddress = findViewById(R.id.txtRegisterCustomerAddress);

    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnRegisterCustomer){
            if(checkRegisterDetail()){
                customerRegister(
                        txtRegisterCustomerEmail.getText().toString(), txtRegisterCustomerName.getText().toString(),
                        txtRegisterCustomerPassword1.getText().toString(), txtRegisterCustomerAddress.getText().toString(),
                        txtRegisterCustomerNumber.getText().toString());
            }
        }

    }

    //Maybe we need more limitation about the register details.
    private Boolean checkRegisterDetail(){
        if(txtRegisterCustomerName.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Username is mandatory!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(txtRegisterCustomerEmail.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Email is mandatory!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(txtRegisterCustomerPassword1.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Password is mandatory!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!txtRegisterCustomerPassword1.getText().toString()
                .equals(txtRegisterCustomerPassword2.getText().toString())){
            Toast.makeText(getApplicationContext(), "Entered passwords differ!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @SuppressLint("CheckResult")
    private void customerRegister(String email, String username, String password, String address, String tel){
        //Optional part for http request.
        address = address.equals("")? null : address;
        tel = tel.equals("")? null : tel;

        PublicMethodApi httpApi = RetrofitClient.getInstance().getService(PublicMethodApi.class);
        httpApi.customerRegister(email, username, password, address, tel)
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
                            sp.edit().putString("userType", "customer").apply();
                            sp.edit().putString("token", loginDataHttpBaseBean.getData().getToken()).apply();
                            sp.edit().putLong("exp", loginDataHttpBaseBean.getData().getExp()).apply();
                            startActivity(new Intent(CustomerRegister.this, CustomerMainActivity.class));
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    loginDataHttpBaseBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "Network error!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}