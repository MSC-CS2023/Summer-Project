package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Bean.AdapterData.OrderCard;
import com.example.myapplication.R;
import com.example.myapplication.network.Constant;

import java.util.List;

public class OrderCardAdapterAll extends RecyclerView.Adapter <OrderCardAdapterAll.ViewHolder> {

    private List<OrderCard> orderCards;
    private ServiceCardAdapter.OnItemClickListener myOnItemClickListener;
    private Context context;


    public OrderCardAdapterAll(List<OrderCard> orderCards, Context context) {
        this.orderCards = orderCards;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_card_all_item, parent, false);
        OrderCardAdapterAll.ViewHolder viewHolder = new OrderCardAdapterAll.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        OrderCard orderCard = orderCards.get(position);

        holder.bindData(position);

        holder.orderState.setText(orderCard.getOrderSate());
        holder.orderId.setText("orderID: " + orderCard.getOrderId().toString());
        holder.orderTitle.setText(orderCard.getOrderTitle());
        holder.orderPrice.setText("￡" + orderCard.getOrderPrice());
        Glide.with(this.context).load(orderCard.getPictureLink())
                .apply(Constant.pictureOptions)
                .into(holder.orderPicture);
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
//    TextView orderSate;
    TextView orderId;
    TextView orderTitle;
    TextView orderPrice;
    TextView orderState;
    ImageView orderPicture;

        private int myPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            this.orderSate = itemView.findViewById(R.id.orderState);
            this.orderId = itemView.findViewById(R.id.orderNum);
            this.orderTitle = itemView.findViewById(R.id.orderTitle);
            this.orderPrice = itemView.findViewById(R.id.orderPrice);
            this.orderPicture = itemView.findViewById(R.id.serviceImg);
            this.orderState = itemView.findViewById(R.id.orderState);

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


