package com.example.myapplication.frontendProvider.profilePages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.R;

public class ProviderResetPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;
    EditText oldPassword;
    EditText newPassword;
    ImageButton save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_reset_password);

        initView();
    }

    private void initView() {
        oldPassword = findViewById(R.id.edtxt_old_password);
        newPassword = findViewById(R.id.edtxt_new_password);
        save = findViewById(R.id.btn_save);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_save) {
            //保存更改，发送请求
            Toast.makeText(this, "save clicked", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}