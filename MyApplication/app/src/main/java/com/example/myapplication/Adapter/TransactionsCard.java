package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.ServiceCardAdapter;
import com.example.myapplication.Bean.AdapterData.TransactionCard;
import com.example.myapplication.R;

import java.util.List;

public class TransactionsCard extends RecyclerView.Adapter <TransactionsCard.ViewHolder> {

    private List<TransactionCard> transactionCards;
    private ServiceCardAdapter.OnItemClickListener myOnItemClickListener;


    public TransactionsCard(List<TransactionCard> transactionCards) {
        this.transactionCards = transactionCards;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transfer_card_item, parent, false);
        TransactionsCard.ViewHolder viewHolder = new TransactionsCard.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TransactionCard transactionCard = transactionCards.get(position);

        holder.bindData(position);

        holder.name.setText(transactionCard.getName());
        holder.type.setText(transactionCard.getType());
        holder.time.setText(transactionCard.getTime());
        holder.amount.setText(transactionCard.getAmount());

    }

    @Override
    public int getItemCount() {
        return transactionCards.size();
    }

    public void setOnItemClickListener(ServiceCardAdapter.OnItemClickListener listener) {
        this.myOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView type;
        TextView time;
        TextView amount;

        private int myPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.name = itemView.findViewById(R.id.name);
            this.type = itemView.findViewById(R.id.type);
            this.time = itemView.findViewById(R.id.time);
            this.amount = itemView.findViewById(R.id.amount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (myOnItemClickListener != null) {
                        myOnItemClickListener.onItemClick(myPosition);
                    }
                }
            });

        }
        public void bindData(int position) {
            myPosition = position;
        }
    }
}

