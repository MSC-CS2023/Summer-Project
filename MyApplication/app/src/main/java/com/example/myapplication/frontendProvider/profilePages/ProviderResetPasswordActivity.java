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
import android.widget.Toast;

import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.R;
import com.example.myapplication.network.CustomerApi;
import com.example.myapplication.network.ProviderApi;
import com.example.myapplication.network.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class ProviderResetPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private String token;
    Toolbar toolbar;
    EditText oldPassword;
    EditText newPassword;
    EditText newPassword2;
    ImageButton save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_reset_password);

        SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        this.token = sp.getString("token", "");

        initView();
    }

    private void initView() {
        oldPassword = findViewById(R.id.edtxt_old_password);
        newPassword = findViewById(R.id.edtxt_new_password);
        newPassword2 = findViewById(R.id.edtxt_new_password2);
        save = findViewById(R.id.btn_save);
        save.setOnClickListener(this);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_save) {
            resetPassword();
            Toast.makeText(this, "save clicked", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetPassword(){
        if(oldPassword.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Old password is mandatory!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!newPassword.getText().toString().equals(newPassword2.getText().toString())){
            Toast.makeText(getApplicationContext(), "Entered new passwords differ!", Toast.LENGTH_SHORT).show();
            return;
        }
        resetProviderPassword(token, oldPassword.getText().toString(), newPassword.getText().toString());
    }

    @SuppressLint("CheckResult")
    private void resetProviderPassword(String token, String oldPassword, String newPassWord){
        ProviderApi providerApi = RetrofitClient.getInstance().getService(ProviderApi.class);
        providerApi.resetProviderPassword(token, oldPassword, newPassWord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<Object>>() {
                    @Override
                    public void onNext(HttpBaseBean<Object> objectHttpBaseBean) {
                        if(objectHttpBaseBean.getSuccess()){
                            Toast.makeText(getApplicationContext(), "Password reset successfully", Toast.LENGTH_SHORT).show();
                            finish();
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