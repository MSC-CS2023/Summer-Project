package com.example.myapplication.frontendProvider.messagePages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class ProviderMessageDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView username;
    private Toolbar toolbar;
    private EditText message;
    private ImageButton send;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_message_detail);

        initView();
    }

    private void initView() {
        username = findViewById(R.id.txt_username);
        message = findViewById(R.id.edtxt_message);
        send = findViewById(R.id.btn_send);
        send.setOnClickListener(this);
        recyclerView = findViewById(R.id.rv_provider_message_detail);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_send) {
            //给服务器发送请求
            Toast.makeText(this, "Send message", Toast.LENGTH_SHORT).show();
        }
    }
}