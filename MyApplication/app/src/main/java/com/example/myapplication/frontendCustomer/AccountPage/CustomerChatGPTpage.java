package com.example.myapplication.frontendCustomer.AccountPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.ChatBotAdapter;
import com.example.myapplication.Bean.AdapterData.ChatBotCard;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CustomerChatGPTpage extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView welcomeTxt;
    EditText messageSend;
    ImageButton btnSend;

    List<ChatBotCard> chatBotCardList;

    ChatBotAdapter chatBotAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_chat_gptpage);

        recyclerView = findViewById(R.id.recyclerViewChatBot);
        welcomeTxt = findViewById(R.id.welcomeTxt);
        messageSend = findViewById(R.id.messageContent);
        btnSend = findViewById(R.id.btnSend);

        chatBotCardList = new ArrayList<>();
        chatBotAdapter = new ChatBotAdapter(chatBotCardList);
        recyclerView.setAdapter(chatBotAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageSend.getText().toString().trim();
//                Toast.makeText(CustomerChatGPTpage.this, message, Toast.LENGTH_SHORT).show();
                addToChat(message, ChatBotCard.SENT_BY_ME);
//                messageSend.setText("");
//                welcomeTxt.setVisibility(View.GONE);
            }
        });


    }

    public void addToChat(String message,String sendBy){

        Log.d("DebugInfo", "Message: " + message + ", SentBy: " + sendBy);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chatBotCardList.add(new ChatBotCard(message,sendBy));
                chatBotAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(chatBotAdapter.getItemCount());
            }
        });
    }

}