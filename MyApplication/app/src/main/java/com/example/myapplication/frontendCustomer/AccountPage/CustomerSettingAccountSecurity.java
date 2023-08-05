package com.example.myapplication.frontendCustomer.AccountPage;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.data.ModifyDetailData;
import com.example.myapplication.R;
import com.example.myapplication.network.CustomerApi;
import com.example.myapplication.network.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class CustomerSettingAccountSecurity extends AppCompatActivity implements View.OnClickListener {

      ImageButton AS_username;
      ImageButton AS_phone;
      ImageButton AS_email;
      ImageButton As_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_setting_account_seurity);

        setToolBar();
        initializeButton();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.AS_username) {
//            alertDialog();
            startActivity(new Intent(CustomerSettingAccountSecurity.this, CustomerResetUsername.class));
        } else if (view.getId() == R.id.AS_phone) {
            startActivity(new Intent(CustomerSettingAccountSecurity.this, CustomerResetPhone.class));
        }else if (view.getId() == R.id.AS_email) {
            startActivity(new Intent(CustomerSettingAccountSecurity.this, CustomerResetEmail.class));
        }else if (view.getId() == R.id.AS_password) {
            startActivity(new Intent(CustomerSettingAccountSecurity.this, CustomerResetPassword.class));
        }

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

    private void initializeButton() {
        AS_username = findViewById(R.id.AS_username);
        AS_phone = findViewById(R.id.AS_phone);
        AS_email = findViewById(R.id.AS_email);
        As_password = findViewById(R.id.AS_password);

        AS_username.setOnClickListener(this);
        AS_phone.setOnClickListener(this);
        AS_email.setOnClickListener(this);
        As_password.setOnClickListener(this);

    }

    //For future use.
    @SuppressLint("CheckResult")
    private void modifyPersonalDetail(String token, String modifiedItem, String modifiedContent){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.modifyCustomerDetail(token, modifiedItem, modifiedContent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<ModifyDetailData>>() {
                    @Override
                    public void onNext(HttpBaseBean<ModifyDetailData> modifyDetailDataHttpBaseBean) {
                        if(modifyDetailDataHttpBaseBean.getSuccess()){
                            Toast.makeText(getApplicationContext(),
                                    "Account detail modified successfully.", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    modifyDetailDataHttpBaseBean.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void alertDialog(){
        AlertDialog alertDialog= new AlertDialog.Builder(this)
                .setIcon(R.drawable.btn_redheart)
                .setTitle("title")
                .setMessage("message")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(CustomerSettingAccountSecurity.this, "sssss", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create();
        alertDialog.show();

        Button btnPos = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        btnPos.setTextColor(Color.RED);
        Button btnNeg = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        btnNeg.setTextColor(Color.BLACK);
    }

}

