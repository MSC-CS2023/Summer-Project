package com.example.myapplication.frontendProvider.messagePages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ProviderMessageDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView username;
    private Toolbar toolbar;
    private EditText message;
    private ImageButton send;
    private RecyclerView recyclerView;
    private List<ProviderMessageDetailData> data = new ArrayList<>();

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

        setAdapter();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void setAdapter() {
        createDemoData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ProviderMessageDetailAdaptor(data, this));
    }

    private void createDemoData() {
        data.add(new ProviderMessageDetailData("Sarah", "Hello from other side", "20230821014356000"));
        data.add(new ProviderMessageDetailData("Sarah", "lets talk about your service", "20230832014506000"));
        data.add(new ProviderMessageDetailData("Jack3", "Okay", "20230821014550000"));
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_send) {
            //给服务器发送请求
            Toast.makeText(this, "Send message", Toast.LENGTH_SHORT).show();
        }
    }
}