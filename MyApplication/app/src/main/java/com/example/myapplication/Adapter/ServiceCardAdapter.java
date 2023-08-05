package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Bean.AdapterData.ServiceCard;
import com.example.myapplication.R;

import java.util.List;

public class ServiceCardAdapter extends BaseAdapter {

    private List<ServiceCard> serviceCards;
    private Context context;

    public ServiceCardAdapter(List<ServiceCard> serviceCards, Context context) {
        this.serviceCards = serviceCards;
        this.context = context;
    }

    @Override
    public int getCount() {
        return serviceCards.size();
    }

    @Override
    public Object getItem(int i) {
        return serviceCards.get(i);
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
            view = LayoutInflater.from(context).inflate(R.layout.service_card_item, viewGroup, false);

            viewHolder.serviceTitle = view.findViewById(R.id.serviceTitle);
            viewHolder.username = view.findViewById(R.id.username);
            viewHolder.servicePrice = view.findViewById(R.id.servicePrice);
            viewHolder.avatar = view.findViewById(R.id.avatar);

            viewHolder.serviceInfo = view.findViewById(R.id.serviceInfo);
            viewHolder.collection = view.findViewById(R.id.ic_collection);
            viewHolder.serviceImg = view.findViewById(R.id.serviceImg);
            viewHolder.serviceState = view.findViewById(R.id.state);

            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        ServiceCard serviceCard =  serviceCards.get(i);

        viewHolder.username.setText(serviceCard.getUsername());
        viewHolder.serviceTitle.setText(serviceCard.getServiceTitle());
        viewHolder.servicePrice.setText(serviceCard.getServicePrice());

        viewHolder.avatar.setImageResource(serviceCard.getAvatarSrcId());
//        Glide.with(this.context).load(serviceCards.get(i).getProviderAvatarSrc()).into(viewHolder.avatar);

        viewHolder.serviceInfo.setText(serviceCard.getServiceInfo());
        viewHolder.collection.setImageResource(serviceCard.getCollectionSrcId());
        viewHolder.serviceImg.setImageResource(serviceCard.getServiceImgSrcId());
        viewHolder.serviceState.setText(serviceCard.getState());


        return view;
    }

    private final class ViewHolder{
        ImageView avatar;
        TextView username;
        TextView serviceInfo;
        ImageButton collection;
        ImageView serviceImg;
        TextView serviceTitle;
        TextView servicePrice;
        TextView serviceState;
    }
}
