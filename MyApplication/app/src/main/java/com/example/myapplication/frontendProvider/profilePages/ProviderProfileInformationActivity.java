package com.example.myapplication.frontendProvider.profilePages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class ProviderProfileInformationActivity extends AppCompatActivity implements View.OnClickListener{

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

        initView();
    }

    private void initView() {
        edit = findViewById(R.id.btn_edit);
        edit.setOnClickListener(this);

        username = findViewById(R.id.txt_username);
        email = findViewById(R.id.txt_email);
        phone = findViewById(R.id.txt_phone);
        address = findViewById(R.id.txt_Address);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_edit) {
            setContentView(R.layout.activity_provider_profile_information_editable);
            initEditView();
        } else if(view.getId() == R.id.btn_save) {
            //保存更改，发送请求
            Toast.makeText(this, "save clicked", Toast.LENGTH_SHORT).show();
            finish();
        } else if(view.getId() == R.id.btn_cancel) {
            setContentView(R.layout.activity_provider_profile_information);
            initView();
        }
    }

    private void initEditView() {
        save = findViewById(R.id.btn_save);
        save.setOnClickListener(this);
        cancel = findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(this);
    }
}