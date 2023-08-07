package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Bean.AdapterData.OrderCard;
import com.example.myapplication.Bean.AdapterData.ServiceCard;
import com.example.myapplication.R;

import java.util.List;

public class OrderCardAdapter extends RecyclerView.Adapter <OrderCardAdapter.ViewHolder> {

    private List<OrderCard> orderCards;
    private ServiceCardAdapter.OnItemClickListener myOnItemClickListener;


    public OrderCardAdapter(List<OrderCard> orderCards) {
        this.orderCards = orderCards;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_card_all_item, parent, false);
        OrderCardAdapter.ViewHolder viewHolder = new OrderCardAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        OrderCard orderCard = orderCards.get(position);

        holder.bindData(position);

        holder.orderSate.setText(orderCard.getOrderSate());
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
    TextView orderSate;
    TextView orderId;
    TextView orderTitle;
    TextView orderPrice;
    ImageView orderPicture;

        private int myPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.orderSate = itemView.findViewById(R.id.orderState);
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


//    @Override
//    public int getCount() {
//        return orderCards.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return orderCards.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        ViewHolder viewHolder;
//        if(view == null){
//            viewHolder = new ViewHolder();
//            view = LayoutInflater.from(context).inflate(R.layout.order_card_item, viewGroup, false);
//
//            viewHolder.orderSate = view.findViewById(R.id.orderState);
//            viewHolder.orderId = view.findViewById(R.id.orderNum);
//            viewHolder.orderTitle = view.findViewById(R.id.orderTitle);
//            viewHolder.orderPrice = view.findViewById(R.id.orderPrice);
//
//            viewHolder.orderPicture = view.findViewById(R.id.serviceImg);
//
//            view.setTag(viewHolder);
//        }else{
//            viewHolder = (ViewHolder) view.getTag();
//        }
//
//        OrderCard orderCard = orderCards.get(i);
//
//        viewHolder.orderSate.setText(orderCard.getOrderSate());
//        viewHolder.orderId.setText("Order NUmber: " + orderCard.getOrderId());
//        viewHolder.orderTitle.setText(orderCard.getOrderTitle());
//        viewHolder.orderPrice.setText(orderCard.getOrderPrice());
//
//        viewHolder.orderPicture.setImageResource(R.drawable.img_sample1);
////        Glide.with(this.context).load(orderCards.get(i).getOrderPictureSrc()).into(viewHolder.orderPicture);
//
//        return view;
//    }


