package com.example.myapplication.frontendCustomer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class CustomerMessageList extends AppCompatActivity implements View.OnClickListener {

    Button testButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custome_message_list);

        testButton = findViewById(R.id.testButton);
        testButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == testButton.getId()){
            startActivity(new Intent(CustomerMessageList.this, CustomerChatPage.class));
        }
    }
}