package com.example.myapplication.frontendCustomer.HomePage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.frontendCustomer.CustomerServiceDetailPage;

public class CustomerHomePageFragment extends Fragment {


    public CustomerHomePageFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_customer_home_page, container, false);

        View ServiceDetail = rootView.findViewById(R.id.service1);

        ServiceDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == ServiceDetail.getId()){
                    startActivity(new Intent(getContext(), CustomerServiceDetailPage.class));
                }
            }
        });

        return rootView;
    }
}