package com.example.myapplication.frontendCustomer.loginPage;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.Bean.HttpBaseBean;
import com.example.myapplication.Bean.data.LoginData;
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
    EditText txtRegisterCustomerCode;

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
        txtRegisterCustomerCode = findViewById(R.id.txtRegisterCustomerCode);

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnRegisterCustomer){
            if(checkRegisterDetail()){
                customerRegister();
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
    private void customerRegister(){
        //Optional part for http request.
        String address = txtRegisterCustomerCode.getText().toString().equals("")? null : txtRegisterCustomerCode.getText().toString();
        String tel = txtRegisterCustomerNumber.getText().toString().equals("")? null : txtRegisterCustomerNumber.getText().toString();

        PublicMethodApi httpApi = RetrofitClient.getInstance().getService(PublicMethodApi.class);
        httpApi.customerRegister(
                        txtRegisterCustomerEmail.getText().toString(), txtRegisterCustomerName.getText().toString(),
                        txtRegisterCustomerPassword1.getText().toString(), address, tel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<LoginData>>() {
                    @Override
                    public void onNext(HttpBaseBean<LoginData> loginDataHttpBaseBean) {
                        if(loginDataHttpBaseBean.getSuccess()){
                            Toast.makeText(getApplicationContext(),
                                    loginDataHttpBaseBean.getData().getUser().getUsername() + "sign up successfully",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(CustomerRegister.this, CustomerMainActivity.class)
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