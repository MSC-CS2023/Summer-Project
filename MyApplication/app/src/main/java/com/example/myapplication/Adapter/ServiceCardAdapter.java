package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Bean.AdapterData.ServiceCard;
import com.example.myapplication.R;

import java.util.List;

public class ServiceCardAdapter extends BaseAdapter {

    private List<ServiceCard> demoData;
    private Context context;

    public ServiceCardAdapter(List<ServiceCard> demoData, Context context) {
        this.demoData = demoData;
        this.context = context;
    }

    @Override
    public int getCount() {
        return demoData.size();
    }

    @Override
    public Object getItem(int i) {
        return demoData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.service_card_item, viewGroup, false);
        }

        ImageView avatar = view.findViewById(R.id.avatar);
        TextView username = view.findViewById(R.id.username);
        TextView serviceInfo = view.findViewById(R.id.serviceInfo);
        ImageButton collection = view.findViewById(R.id.ic_collection);
        ImageView serviceImg = view.findViewById(R.id.serviceImg);
        TextView serviceTitle = view.findViewById(R.id.serviceTitle);
        TextView servicePrice = view.findViewById(R.id.servicePrice);
        TextView serviceState = view.findViewById(R.id.state);

        ServiceCard serviceCard =  demoData.get(i);

        avatar.setImageResource(serviceCard.getAvatarSrcId());
        username.setText(serviceCard.getUsername());
        serviceInfo.setText(serviceCard.getServiceInfo());
        collection.setImageResource(serviceCard.getColletionSrcId());
        serviceImg.setImageResource(serviceCard.getServiceImgSrcId());
        serviceTitle.setText(serviceCard.getServiceTitle());
        servicePrice.setText(serviceCard.getServicePrice());
        serviceState.setText(serviceCard.getState());


        return view;
    }
}
