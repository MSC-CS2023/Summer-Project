package com.example.myapplication.frontendCustomer.AccountPage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

public class CustomerOrderPage extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_page);

        initialTopBar();




    }

    void initialTopBar(){
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        TabLayout.Tab tab1 = tabLayout.newTab();
        tab1.setText("All");
        tabLayout.addTab(tab1);

        TabLayout.Tab tab2 = tabLayout.newTab();
        tab2.setText("Unpaid");
        tabLayout.addTab(tab2);

        TabLayout.Tab tab3 = tabLayout.newTab();
        tab3.setText("Unconfirmed");
        tabLayout.addTab(tab3);

        TabLayout.Tab tab4 = tabLayout.newTab();
        tab4.setText("Processing");
        tabLayout.addTab(tab4);

        TabLayout.Tab tab5 = tabLayout.newTab();
        tab5.setText("Review");
        tabLayout.addTab(tab5);

        TabLayout.Tab tab6 = tabLayout.newTab();
        tab6.setText("Refund");
        tabLayout.addTab(tab6);
    }

}