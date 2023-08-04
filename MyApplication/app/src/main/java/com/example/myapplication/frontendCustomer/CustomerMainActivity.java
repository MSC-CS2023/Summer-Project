package com.example.myapplication.frontendCustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication.R;
import com.example.myapplication.frontendCustomer.AccountPage.CustomerAccountFragment;
import com.example.myapplication.frontendCustomer.CollectionPage.CustomerCollectionFragment;
import com.example.myapplication.frontendCustomer.HomePage.CustomerHomePageFragment;
import com.example.myapplication.frontendCustomer.MessagePage.CustomerMessageListFragment;
import com.example.myapplication.frontendCustomer.SearchPage.CustomerSearchPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class CustomerMainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    CustomerHomePageFragment customerHomePageFragment;
    CustomerSearchPageFragment customerSearchPageFragment;
    CustomerMessageListFragment customerMessageListFragment;
    CustomerCollectionFragment customerCollectionFragment;
    CustomerAccountFragment customerAccountFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);

        customerHomePageFragment = new CustomerHomePageFragment();
        customerSearchPageFragment = new CustomerSearchPageFragment();
        customerMessageListFragment = new CustomerMessageListFragment();
        customerCollectionFragment = new CustomerCollectionFragment();
        customerAccountFragment =new CustomerAccountFragment();

//        Intent intent = getIntent();
//        User user = (User) intent.getSerializableExtra("User");
//        String token = intent.getStringExtra("token");
//        Long exp = intent.getLongExtra("exp",0);
//        Toast.makeText(this, token, Toast.LENGTH_LONG).show();


        bottomNavigationView = findViewById(R.id.nav_bar);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.btnCustomerHomepage){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,customerHomePageFragment).commit();
                }else if (item.getItemId() == R.id.btnCustomerSearch) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,customerSearchPageFragment).commit();
                }else if (item.getItemId() == R.id.btnCustomerMessage) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,customerMessageListFragment).commit();
                }else if (item.getItemId() == R.id.btnCustomerCollection) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,customerCollectionFragment).commit();
                }else if (item.getItemId() == R.id.btnCustomerAccount) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,customerAccountFragment).commit();
                }

                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.btnCustomerHomepage);

    }

}