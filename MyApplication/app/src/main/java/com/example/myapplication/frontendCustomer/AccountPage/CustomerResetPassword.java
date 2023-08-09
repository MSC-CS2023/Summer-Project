package com.example.myapplication.frontendCustomer.AccountPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
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

    EditText oldPassword;
    EditText newPassword;
    ImageButton submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_reset_password);

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
        submit = findViewById(R.id.btnSubmit);
        submit.setOnClickListener(this);
        oldPassword = findViewById(R.id.txtNewUsername);

        newPassword = findViewById(R.id.txtNewUsername);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit){
            Toast.makeText(this, "submit click", Toast.LENGTH_SHORT).show();
        }
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
                            Toast.makeText(getApplicationContext(), "password reset successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    objectHttpBaseBean.getMessage(), Toast.LENGTH_SHORT).show();

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