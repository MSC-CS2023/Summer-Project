package com.example.myapplication.frontendProvider.profilePages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ProviderWalletActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView balance;
    private RecyclerView recyclerView;
    private List<ProviderTransactionCardData> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_wallet);

        initView();
    }

    private void initView() {
        balance = findViewById(R.id.balance);

        initData();
        initAdaptor();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initData() {
        data = new ArrayList<>();
        data.add(new ProviderTransactionCardData("Sarah", "Income", "08/14/2023", "300.00"));
        data.add(new ProviderTransactionCardData("Ben", "Income", "08/13/2023", "250.00"));
        data.add(new ProviderTransactionCardData("Jake", "Income", "08/12/2023", "200.00"));
        data.add(new ProviderTransactionCardData("Jane", "Income", "08/11/2023", "150.00"));
        data.add(new ProviderTransactionCardData("May", "Income", "08/10/2023", "100.00"));
    }

    private void initAdaptor() {
        recyclerView = findViewById(R.id.rv_provider_transaction);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ProviderTransactionAdaptor providerTransactionAdaptor = new ProviderTransactionAdaptor(data, this);
        recyclerView.setAdapter(providerTransactionAdaptor);
    }
}