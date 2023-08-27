package com.example.myapplication.frontendProvider.messagePages;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class ProviderMessageDetailAdaptor extends RecyclerView.Adapter<ProviderMessageDetailAdaptor.ViewHolder> {
    private List<ProviderMessageDetailData> data;
    private Context context;

    public ProviderMessageDetailAdaptor(List<ProviderMessageDetailData> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        ProviderMessageDetailData message = data.get(position);
        if(message.getSender().equals("Sarah")) {
            return R.layout.recyclerview_item_remote_message;
        } else {
            return R.layout.recyclerview_item_local_message;
        }
    }

    @NonNull
    @Override
    public ProviderMessageDetailAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, viewType, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProviderMessageDetailAdaptor.ViewHolder holder, int position) {
        holder.message.setText(data.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView message;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.txt_message);
        }
    }
}
