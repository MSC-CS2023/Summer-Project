package com.example.myapplication.frontendProvider.messagePages;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

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

    @NonNull
    @Override
    public ProviderMessageDetailAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.recyclerview_item_local_message, null);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ProviderMessageDetailAdaptor.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
