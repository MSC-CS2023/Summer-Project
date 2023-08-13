package com.example.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Bean.AdapterData.ServiceCard;
import com.example.myapplication.R;

import java.util.List;

public class ServiceCardAdapter extends RecyclerView.Adapter <ServiceCardAdapter.ViewHolder> {


    private List<ServiceCard> serviceCards;
    private OnItemClickListener myOnItemClickListener;
    private Context context;

    public ServiceCardAdapter(List<ServiceCard> serviceCards, Context context) {
        this.serviceCards = serviceCards;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_card_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServiceCard serviceCard = serviceCards.get(position);

        holder.bindData(position);
//        Glide.with(this.context).load(serviceCard.getProviderAvatarSrc()).into(holder.avatar);
//        Glide.with(this.context).load(serviceCard.getServicePictureSrc()).into(holder.serviceImg);
        holder.avatar.setImageResource(serviceCard.getAvatarSrcId());
        holder.serviceImg.setImageResource(serviceCard.getServiceImgSrcId());
        holder.username.setText(serviceCard.getUsername());
        holder.serviceTitle.setText(serviceCard.getServiceTitle());
        holder.servicePrice.setText("￡" + serviceCard.getServicePrice());
        holder.serviceInfo.setText(serviceCard.getServiceInfo());
        holder.collection.setImageResource(serviceCard.getCollectionSrcId());
        holder.serviceState.setText(serviceCard.getState());

        //set avatar circular  Import a square image and automatically crop it into a circle
        //R.drawable.btn_avatar3 can be replaced by serviceCard.getAvatarSrcId()
        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_avatar3);
        Bitmap circularBitmap = getRoundedBitmap(originalBitmap);
        holder.avatar.setImageBitmap(circularBitmap);

    }

    @Override
    public int getItemCount() {
        return serviceCards.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.myOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private Bitmap getRoundedBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int radius = Math.min(width, height) / 2;

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        android.graphics.Canvas canvas = new android.graphics.Canvas(output);

        android.graphics.Path path = new android.graphics.Path();
        path.addCircle(width / 2, height / 2, radius, android.graphics.Path.Direction.CW);

        canvas.clipPath(path);
        canvas.drawBitmap(bitmap, 0, 0, null);

        return output;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView username;
        TextView serviceInfo;
        ImageButton collection;
        ImageView serviceImg;
        TextView serviceTitle;
        TextView servicePrice;
        TextView serviceState;

        private int myPosition;

        public ViewHolder(View itemView) {
            super(itemView);
            this.avatar = itemView.findViewById(R.id.avatar);
            this.username = itemView.findViewById(R.id.username);
            this.serviceInfo = itemView.findViewById(R.id.serviceInfo);
            this.collection = itemView.findViewById(R.id.ic_collection);
            this.serviceImg = itemView.findViewById(R.id.serviceImg);
            this.serviceTitle = itemView.findViewById(R.id.serviceTitle);
            this.servicePrice = itemView.findViewById(R.id.servicePrice);
            this.serviceState = itemView.findViewById(R.id.serviceState);

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

//
//    @Override
//    public int getCount() {
//        return serviceCards.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return serviceCards.get(i);
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
//            view = LayoutInflater.from(context).inflate(R.layout.service_card_item, viewGroup, false);
//
//            viewHolder.serviceTitle = view.findViewById(R.id.serviceTitle);
//            viewHolder.username = view.findViewById(R.id.username);
//            viewHolder.servicePrice = view.findViewById(R.id.servicePrice);
//            viewHolder.avatar = view.findViewById(R.id.avatar);
//
//            viewHolder.serviceInfo = view.findViewById(R.id.serviceInfo);
//            viewHolder.collection = view.findViewById(R.id.ic_collection);
//            viewHolder.serviceImg = view.findViewById(R.id.serviceImg);
//            viewHolder.serviceState = view.findViewById(R.id.state);
//
//            view.setTag(viewHolder);
//        }else{
//            viewHolder = (ViewHolder) view.getTag();
//        }
//
//        ServiceCard serviceCard =  serviceCards.get(i);
//
//        viewHolder.username.setText(serviceCard.getUsername());
//        viewHolder.serviceTitle.setText(serviceCard.getServiceTitle());
//        viewHolder.servicePrice.setText(serviceCard.getServicePrice());
//
//        viewHolder.avatar.setImageResource(serviceCard.getAvatarSrcId());
////        Glide.with(this.context).load(serviceCards.get(i).getProviderAvatarSrc()).into(viewHolder.avatar);
//
//        viewHolder.serviceInfo.setText(serviceCard.getServiceInfo());
//        viewHolder.collection.setImageResource(serviceCard.getCollectionSrcId());
//        viewHolder.serviceImg.setImageResource(serviceCard.getServiceImgSrcId());
//        viewHolder.serviceState.setText(serviceCard.getState());
//
//
//        return view;
//    }



