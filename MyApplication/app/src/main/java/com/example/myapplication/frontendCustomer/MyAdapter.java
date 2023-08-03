package com.example.myapplication.frontendCustomer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private List<ItemData> demoData;
    private LayoutInflater layoutInflater;
    private Context context;

    public MyAdapter(List<ItemData> demoData, Context context) {
        this.demoData = demoData;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
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

        view = layoutInflater.inflate(R.layout.homepage_service_item,  viewGroup,false);

        ImageView avatar = view.findViewById(R.id.avatar);
        TextView username = view.findViewById(R.id.username);
        TextView serviceInfo = view.findViewById(R.id.serviceInfo);
        ImageButton collection = view.findViewById(R.id.ic_collection);
        ImageView serviceImg = view.findViewById(R.id.serviceImg);
        TextView serviceTitle = view.findViewById(R.id.serviceTitle);
        TextView servicePrice = view.findViewById(R.id.servicePrice);
        TextView serviceState = view.findViewById(R.id.state);

        ItemData itemData =  demoData.get(i);

        avatar.setImageResource(itemData.getAvatarSrcId());
        username.setText(itemData.getUsername());
        serviceInfo.setText(itemData.getServiceInfo());
        collection.setImageResource(itemData.getColletionSrcId());
        serviceImg.setImageResource(itemData.getServiceImgSrcId());
        serviceTitle.setText(itemData.getServiceTitle());
        servicePrice.setText(itemData.getServicePrice());
        serviceState.setText(itemData.getState());


        return view;
    }
}
