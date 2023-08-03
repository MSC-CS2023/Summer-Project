package com.example.myapplication.frontendCustomer.MessagePage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;


public class CustomerMessageListFragment extends Fragment implements View.OnClickListener {

    Button message;
    Button notification;


    public CustomerMessageListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_customer_message_list, container, false);

//        View chatTag =rootView.findViewById(R.id.chat_tag);

//        chatTag.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (view.getId() == chatTag.getId()){
//                    startActivity(new Intent(getContext(), CustomerChatPage.class));
//                }
//            }
//        });

        message = rootView.findViewById(R.id.message);
        notification = rootView.findViewById(R.id.notification);

        message.setOnClickListener(this);
        notification.setOnClickListener(this);



        return rootView;


    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.message){
            Toast.makeText(getContext(), "click message", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.notification) {
            Toast.makeText(getContext(), "click notification", Toast.LENGTH_SHORT).show();
        }
    }
}