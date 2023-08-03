package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

<<<<<<< Updated upstream
import com.example.myapplication.Bean.AdapterData.ServiceCard;
=======
import com.example.myapplication.Bean.Adapterdata.ServiceCard;
>>>>>>> Stashed changes
import com.example.myapplication.R;

import java.util.List;

public class ServiceCardAdapter extends BaseAdapter {
<<<<<<< Updated upstream

    private List<ServiceCard> demoData;
    private Context context;

    public ServiceCardAdapter(List<ServiceCard> demoData, Context context) {
        this.demoData = demoData;
=======
    private List<ServiceCard> serviceCardList;
    private Context context;

    public ServiceCardAdapter(List<ServiceCard> data, Context context) {
        this.serviceCardList = data;
>>>>>>> Stashed changes
        this.context = context;
    }

    @Override
    public int getCount() {
<<<<<<< Updated upstream
        return demoData.size();
=======
        return serviceCardList.size();
>>>>>>> Stashed changes
    }

    @Override
    public Object getItem(int i) {
<<<<<<< Updated upstream
        return demoData.get(i);
=======
        return null;
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream

=======
        
>>>>>>> Stashed changes
        ImageView avatar = view.findViewById(R.id.avatar);
        TextView username = view.findViewById(R.id.username);
        TextView serviceInfo = view.findViewById(R.id.serviceInfo);
        ImageButton collection = view.findViewById(R.id.ic_collection);
        ImageView serviceImg = view.findViewById(R.id.serviceImg);
        TextView serviceTitle = view.findViewById(R.id.serviceTitle);
        TextView servicePrice = view.findViewById(R.id.servicePrice);
        TextView serviceState = view.findViewById(R.id.state);

<<<<<<< Updated upstream
        ServiceCard serviceCard =  demoData.get(i);
=======
        ServiceCard serviceCard =  serviceCardList.get(i);
>>>>>>> Stashed changes

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
