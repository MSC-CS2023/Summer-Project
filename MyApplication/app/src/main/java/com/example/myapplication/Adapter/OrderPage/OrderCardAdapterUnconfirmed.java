package com.example.myapplication.Adapter.OrderPage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.ServiceCardAdapter;
import com.example.myapplication.Bean.AdapterData.OrderCard;
import com.example.myapplication.R;

import java.util.List;

public class OrderCardAdapterUnconfirmed extends RecyclerView.Adapter <OrderCardAdapterUnconfirmed.ViewHolder> {

    private List<OrderCard> orderCards;
    private ServiceCardAdapter.OnItemClickListener myOnItemClickListener;


    public OrderCardAdapterUnconfirmed(List<OrderCard> orderCards) {
        this.orderCards = orderCards;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_card_unconfirmed_item, parent, false);
        OrderCardAdapterUnconfirmed.ViewHolder viewHolder = new OrderCardAdapterUnconfirmed.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        OrderCard orderCard = orderCards.get(position);

        holder.bindData(position);

//        holder.orderSate.setText(orderCard.getOrderSate());
        holder.orderId.setText(orderCard.getOrderId());
        holder.orderTitle.setText(orderCard.getOrderTitle());
        holder.orderPrice.setText(orderCard.getOrderPrice());
//        holder.orderPicture.setImageResource(orderCard.getOrderPictureSrc());
        //没弄明白为什么加添加图片会报错

    }

    @Override
    public int getItemCount() {
        return orderCards.size();
    }

    public void setOnItemClickListener(ServiceCardAdapter.OnItemClickListener listener) {
        this.myOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
//        TextView orderSate;
        TextView orderId;
        TextView orderTitle;
        TextView orderPrice;
        ImageView orderPicture;

        private int myPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            this.orderSate = itemView.findViewById(R.id.orderState);
            this.orderId = itemView.findViewById(R.id.orderNum);
            this.orderTitle = itemView.findViewById(R.id.orderTitle);
            this.orderPrice = itemView.findViewById(R.id.orderPrice);
            this.orderPicture = itemView.findViewById(R.id.serviceImg);

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