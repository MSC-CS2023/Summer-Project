package com.example.myapplication.frontendProvider.profilePages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.User;
import com.example.myapplication.Bean.Httpdata.data.ModifyDetailData;
import com.example.myapplication.Bean.Httpdata.data.SelfDetailData;
import com.example.myapplication.Constant;
import com.example.myapplication.R;
import com.example.myapplication.network.CustomerApi;
import com.example.myapplication.network.ProviderApi;
import com.example.myapplication.network.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class ProviderProfileInformationActivity extends AppCompatActivity implements View.OnClickListener{

    private User user = new User();
    private String token;

    private Toolbar toolbar;
    private ImageButton edit;
    private ImageButton save;
    private ImageButton cancel;

    private TextView username;
    private TextView email;
    private TextView phone;
    private TextView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_profile_information);

        SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        this.token = sp.getString("token", "");

        initView();

        getProviderDetail(this.token);

    }

    private void initView() {
        edit = findViewById(R.id.btn_edit);
        edit.setOnClickListener(this);

        username = findViewById(R.id.txt_username);
        email = findViewById(R.id.txt_email);
        phone = findViewById(R.id.txt_phone);
        address = findViewById(R.id.txt_address);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_edit) {
            setContentView(R.layout.activity_provider_profile_information_editable);
            initEditView();
            updateView(user);
        } else if(view.getId() == R.id.btn_save) {
            modifyPersonalDetail();
            setContentView(R.layout.activity_provider_profile_information);
            initView();
            getProviderDetail(this.token);
        } else if(view.getId() == R.id.btn_cancel) {
            setContentView(R.layout.activity_provider_profile_information);
            initView();
            updateView(user);
        }
    }

    private void initEditView() {
        save = findViewById(R.id.btn_save);
        save.setOnClickListener(this);
        cancel = findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(this);

        username = findViewById(R.id.edtxt_username);
        email = findViewById(R.id.edtxt_email);
        phone = findViewById(R.id.edtxt_phone);
        address = findViewById(R.id.edtxt_address);


        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void updateView(User user){
        username.setText(user.getUsername());
        email.setText(user.getEmail());
        phone.setText(user.getTel());
        address.setText(user.getAddress());
    }

    private void modifyPersonalDetail(){
        if(user.getAddress() == null || !user.getAddress().equals(address.getText().toString())){
            modifyProviderDetail(token, "address", address.getText().toString());
        }
        if(user.getEmail() == null || !user.getEmail().equals(email.getText().toString())){
            modifyProviderDetail(token, "email", email.getText().toString());
        }
        if(user.getTel() == null || !user.getTel().equals(phone.getText().toString())){
            modifyProviderDetail(token, "tel", phone.getText().toString());
        }
        if(user.getUsername() == null || !user.getUsername().equals(username.getText().toString())){
            modifyProviderDetail(token, "username", username.getText().toString());
        }
    }

    @SuppressLint("CheckResult")
    private void getProviderDetail(String token){
        ProviderApi providerApi= RetrofitClient.getInstance().getService(ProviderApi.class);
        providerApi.getProviderDetail(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<SelfDetailData>>() {
                    @Override
                    public void onNext(HttpBaseBean<SelfDetailData> selfDetailDataHttpBaseBean) {
                        if(selfDetailDataHttpBaseBean.getSuccess()){
                            user = selfDetailDataHttpBaseBean.getData().getUser();
                            updateView(user);
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

    @SuppressLint("CheckResult")
    private void modifyProviderDetail(String token, String modifiedItem, String modifiedContent){
        ProviderApi providerApi = RetrofitClient.getInstance().getService(ProviderApi.class);
        providerApi.modifyProviderDetail(token, modifiedItem, modifiedContent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<ModifyDetailData>>() {
                    @Override
                    public void onNext(HttpBaseBean<ModifyDetailData> modifyDetailDataHttpBaseBean) {
                        if(modifyDetailDataHttpBaseBean.getSuccess()){
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