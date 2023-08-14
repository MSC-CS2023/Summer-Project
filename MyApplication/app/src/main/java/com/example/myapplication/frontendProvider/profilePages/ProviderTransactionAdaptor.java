package com.example.myapplication.frontendProvider.profilePages;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class ProviderTransactionAdaptor extends RecyclerView.Adapter<ProviderTransactionAdaptor.ViewHolder> {

    private List<ProviderTransactionCardData> data;
    private Context context;

    public ProviderTransactionAdaptor(List<ProviderTransactionCardData> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ProviderTransactionAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.recyclerview_item_provider_transaction, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProviderTransactionAdaptor.ViewHolder holder, int position) {
        holder.name.setText(data.get(position).getName());
        holder.type.setText(data.get(position).getType());
        holder.time.setText(data.get(position).getTime());
        holder.price.setText(data.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView type;
        TextView time;
        TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            type = itemView.findViewById(R.id.type);
            time = itemView.findViewById(R.id.time);
            price = itemView.findViewById(R.id.price);
        }
    }
}
