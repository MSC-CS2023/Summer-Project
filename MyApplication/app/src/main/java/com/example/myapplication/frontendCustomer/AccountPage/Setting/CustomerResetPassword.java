package com.example.myapplication.frontendCustomer.AccountPage.Setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.Toast;

import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.R;
import com.example.myapplication.network.CustomerApi;
import com.example.myapplication.network.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class CustomerResetPassword extends AppCompatActivity implements View.OnClickListener {

    private String token;
    EditText oldPassword;
    EditText newPassword1;
    EditText newPassword2;
    ImageButton save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_reset_password);

        SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        this.token = sp.getString("token", "");

        setToolBar();

        initializeViews();
    }

    public void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // back to previous page
            }
        });
    }

    void initializeViews(){
        save = findViewById(R.id.btnSave);
        save.setOnClickListener(this);

        oldPassword = findViewById(R.id.txtOldPassword);
        newPassword1 = findViewById(R.id.txtNewPassword1);
        newPassword2 = findViewById(R.id.txtNewPassword2);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSave){
            resetPassword();
            Toast.makeText(this, "submit click", Toast.LENGTH_SHORT).show();
        }
    }


    private void resetPassword(){
        if(oldPassword.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Old password is mandatory!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!newPassword1.getText().toString().equals(newPassword2.getText().toString())){
            Toast.makeText(getApplicationContext(), "Entered new passwords differ!", Toast.LENGTH_SHORT).show();
            return;
        }
        resetCustomerPassword(token, oldPassword.getText().toString(), newPassword1.getText().toString());
    }

    @SuppressLint("CheckResult")
    private void resetCustomerPassword(String token, String oldPassword, String newPassWord){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.resetCustomerPassword(token, oldPassword, newPassWord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<Object>>() {
                    @Override
                    public void onNext(HttpBaseBean<Object> objectHttpBaseBean) {
                        if(objectHttpBaseBean.getSuccess()){
                            Toast.makeText(getApplicationContext(), "Password reset successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    objectHttpBaseBean.getMessage(), Toast.LENGTH_SHORT).show();

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

}