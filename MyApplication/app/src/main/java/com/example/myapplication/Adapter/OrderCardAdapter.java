package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Bean.AdapterData.OrderCard;
import com.example.myapplication.Bean.AdapterData.ServiceCard;
import com.example.myapplication.R;

import java.util.List;

public class OrderCardAdapter extends BaseAdapter {

    private List<OrderCard> orderCards;
    private Context context;

    public OrderCardAdapter(List<OrderCard> orderCards, Context context) {
        this.orderCards = orderCards;
        this.context = context;
    }


    @Override
    public int getCount() {
        return orderCards.size();
    }

    @Override
    public Object getItem(int i) {
        return orderCards.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.order_card_item, viewGroup, false);

            viewHolder.orderSate = view.findViewById(R.id.orderState);
            viewHolder.orderId = view.findViewById(R.id.orderNum);
            viewHolder.orderTitle = view.findViewById(R.id.orderTitle);
            viewHolder.orderPrice = view.findViewById(R.id.orderPrice);

            viewHolder.orderPicture = view.findViewById(R.id.serviceImg);

            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        OrderCard orderCard = orderCards.get(i);

        viewHolder.orderSate.setText(orderCard.getOrderSate());
        viewHolder.orderId.setText("Order NUmber: " + orderCard.getOrderId());
        viewHolder.orderTitle.setText(orderCard.getOrderTitle());
        viewHolder.orderPrice.setText(orderCard.getOrderPrice());

        viewHolder.orderPicture.setImageResource(R.drawable.img_sample1);
//        Glide.with(this.context).load(orderCards.get(i).getOrderPictureSrc()).into(viewHolder.orderPicture);

        return view;
    }

    private final class ViewHolder{
        TextView orderSate;
        TextView orderId;
        TextView orderTitle;
        TextView orderPrice;
        ImageView orderPicture;
    }
}
