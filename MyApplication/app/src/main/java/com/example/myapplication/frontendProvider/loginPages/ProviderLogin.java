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
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.data.LoginData;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.frontendProvider.ProviderMain;
import com.example.myapplication.network.PublicMethodApi;
import com.example.myapplication.network.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class ProviderLogin extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnProviderLogin;
    TextView txtProviderRegister;
    EditText txtUsername;
    EditText txtPassword;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_login);

        btnProviderLogin = findViewById(R.id.btn_provider_login);
        btnProviderLogin.setOnClickListener(this);
        txtProviderRegister = findViewById(R.id.txt_sign_up);
        txtProviderRegister.setOnClickListener(this);

        txtUsername = findViewById(R.id.edtxt_username);
        txtPassword = findViewById(R.id.edtxt_password);
    }

    @Override

        public void onClick(View view) {

            if (view.getId() ==  R.id.btn_provider_login) {
                providerLogin(txtUsername.getText().toString(), txtPassword.getText().toString());
//                startActivity(new Intent(ProviderLogin.this, ProviderMain.class));
            } else if (view.getId() == R.id.txt_sign_up) {
                startActivity(new Intent(ProviderLogin.this, ProviderRegister.class));
            }
        }

        @SuppressLint("CheckResult")
        private void providerLogin(String username, String password){
            PublicMethodApi publicMethodApi = RetrofitClient.getInstance().getService(PublicMethodApi.class);
            publicMethodApi.providerLogin(username, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ResourceSubscriber<HttpBaseBean<LoginData>>() {
                        @Override
                        public void onNext(HttpBaseBean<LoginData> loginDataHttpBaseBean) {
                            if(loginDataHttpBaseBean.getSuccess()){
                                try {
                                    Toast.makeText(getApplicationContext(),
                                            loginDataHttpBaseBean.getData().getUser().getUsername() + " login successfully",
                                            Toast.LENGTH_SHORT).show();
                                    SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
                                    sp.edit().putBoolean("isLoggedIn", true).apply();
                                    sp.edit().putString("userType", "provider").apply();
                                    sp.edit().putString("token", loginDataHttpBaseBean.getData().getToken()).apply();
                                    sp.edit().putLong("exp", loginDataHttpBaseBean.getData().getExp()).apply();
                                    startActivity(new Intent(ProviderLogin.this, ProviderMain.class));
                                    finishAffinity();
                                }catch (NullPointerException ignored){}
                            }else{
                                Toast.makeText(getApplicationContext(),
                                        loginDataHttpBaseBean.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable t) {
                            Toast.makeText(getApplicationContext(),
                                    "Network error! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onComplete() {}
                    });
        }
    }