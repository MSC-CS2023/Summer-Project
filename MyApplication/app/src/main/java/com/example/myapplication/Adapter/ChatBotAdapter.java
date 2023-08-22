package com.example.myapplication.Adapter;

import android.app.Notification;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Bean.AdapterData.ChatBotCard;
import com.example.myapplication.R;

import java.util.List;

public class ChatBotAdapter extends RecyclerView.Adapter<ChatBotAdapter.chatViewHolder>{


    List<ChatBotCard> chatBotCardList;
    public ChatBotAdapter(List<ChatBotCard> chatBotCardList) {
        this.chatBotCardList = chatBotCardList;
    }

    @NonNull
    @Override
    public chatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_bot_item,null);
        chatViewHolder chatViewHolder = new chatViewHolder(view);
        return chatViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull chatViewHolder holder, int position) {
        ChatBotCard chatBotCard = chatBotCardList.get(position);

        if (chatBotCard.getSentBy().equals(ChatBotCard.SENT_BY_ME)) {
            holder.leftChatView.setVisibility(View.GONE);
            holder.rightChatView.setVisibility(View.VISIBLE);
            holder.rightTextView.setText(chatBotCard.getMessage());
        } else {
            holder.rightChatView.setVisibility(View.GONE);
            holder.leftChatView.setVisibility(View.VISIBLE);
            holder.leftTextView.setText(chatBotCard.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return chatBotCardList.size();
    }

    public class chatViewHolder extends RecyclerView.ViewHolder{

        LinearLayout leftChatView;
        LinearLayout rightChatView;
        TextView leftTextView;
        TextView rightTextView;


        public chatViewHolder(@NonNull View itemView) {
            super(itemView);
            leftChatView = itemView.findViewById(R.id.leftMessage);
            rightChatView = itemView.findViewById(R.id.rightMessage);
            leftTextView = itemView.findViewById(R.id.leftMessageTxt);
            rightTextView = itemView.findViewById(R.id.rightMessageTxt);
        }
    }

}
