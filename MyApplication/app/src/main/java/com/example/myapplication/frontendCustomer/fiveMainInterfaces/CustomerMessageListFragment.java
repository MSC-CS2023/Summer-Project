package com.example.myapplication.frontendCustomer.fiveMainInterfaces;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.frontendCustomer.CustomerChatPage;
import com.example.myapplication.frontendCustomer.CustomerMainActivity;


public class CustomerMessageListFragment extends Fragment {


    public CustomerMessageListFragment() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_customer_message_list, container, false);

        View chatTag =rootView.findViewById(R.id.chat_tag1);

        chatTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == chatTag.getId()){
                    startActivity(new Intent(getContext(), CustomerChatPage.class));
                }
            }
        });

        return rootView;


    }


}