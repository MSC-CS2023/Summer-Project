package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Bean.AdapterData.OrderTimetableCard;
import com.example.myapplication.R;

import java.util.List;

public class OrderTimetableCardAdapter extends RecyclerView.Adapter <OrderTimetableCardAdapter.ViewHolder>{

    private List<OrderTimetableCard> orderTimetableCards;
    private ServiceCardAdapter.OnItemClickListener myOnItemClickListener;


    public OrderTimetableCardAdapter(List<OrderTimetableCard> orderTimetableCards) {
        this.orderTimetableCards = orderTimetableCards;
    }

    @NonNull
    @Override
    public OrderTimetableCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_timetable_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderTimetableCardAdapter.ViewHolder holder, int position) {

        OrderTimetableCard orderTimetableCard = orderTimetableCards.get(position);
        holder.bindData(position);

        holder.time.setText(orderTimetableCard.getTime());
        holder.price.setText("￡" + orderTimetableCard.getPrice());
        holder.title.setText(orderTimetableCard.getTitle());
        holder.orderNum.setText("orderID: " + orderTimetableCard.getOrderNum());

    }

    @Override
    public int getItemCount() {
        return orderTimetableCards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView price;
        public TextView time;
        public TextView orderNum;

        private int myPosition;

        public ViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.orderTime);
            title = itemView.findViewById(R.id.orderTitle);
            price = itemView.findViewById(R.id.orderPrice);
            orderNum = itemView.findViewById(R.id.orderNum);


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
