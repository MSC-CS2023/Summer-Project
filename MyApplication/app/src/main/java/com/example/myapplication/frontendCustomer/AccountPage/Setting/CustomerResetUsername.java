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
import com.example.myapplication.Bean.Httpdata.data.ModifyDetailData;
import com.example.myapplication.R;
import com.example.myapplication.network.CustomerApi;
import com.example.myapplication.network.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class CustomerResetUsername extends AppCompatActivity implements View.OnClickListener {


    private String token;
    EditText newName;
    ImageButton save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_reset_username);

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

        newName = findViewById(R.id.txtNewUsername);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnSave){
            if(newName.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Please enter your content.", Toast.LENGTH_SHORT).show();
            }else{
                modifyPersonalDetail(token, "username", newName.getText().toString());
            }
            Toast.makeText(this, "submit click", Toast.LENGTH_SHORT).show();
        }

    }

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
                                    "Account detail "+ modifiedItem + " modified successfully.", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    modifyDetailDataHttpBaseBean.getMessage(), Toast.LENGTH_SHORT).show();
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