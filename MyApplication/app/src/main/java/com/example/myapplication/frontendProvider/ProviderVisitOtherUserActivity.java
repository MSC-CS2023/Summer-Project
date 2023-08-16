package com.example.myapplication.frontendProvider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

public class ProviderVisitOtherUserActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView avatar;
    TextView username;
    TextView email;
    TextView phone;
    TextView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_visit_other_user);

        initView();
    }

    private void initView() {
        avatar = findViewById(R.id.img_avatar);
        username = findViewById(R.id.txt_username);
        email = findViewById(R.id.txt_email);
        phone = findViewById(R.id.txt_phone);
        address = findViewById(R.id.txt_address);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
    }
}