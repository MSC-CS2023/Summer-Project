package com.example.myapplication.frontendProvider.orderPages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.frontendProvider.homePages.ProviderServicesAdaptor;

import java.util.List;

public class ProviderOrdersAdaptor extends RecyclerView.Adapter<ProviderOrdersAdaptor.ViewHolder> {

    private List<ProviderOrderCardData> data;
    private Context context;

    public ProviderOrdersAdaptor(List<ProviderOrderCardData> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ProviderOrdersAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.recyclerview_item_provider_order, null);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")    //help toString method below
    @Override
    public void onBindViewHolder(@NonNull ProviderOrdersAdaptor.ViewHolder holder, int position) {

        holder.bindData(position);
        holder.title.setText(data.get(position).getTitle());
        holder.orderNum.setText(data.get(position).getOrderNum().toString());
        holder.price.setText(data.get(position).getPrice());
        holder.image.setImageResource(context.getResources().getIdentifier(data.get(position).getImageSrc(),
                "drawable", context.getPackageName()));
//        Glide.with(this.context).load(data.get(position).getImageLink()).into(holder.image);
        holder.state.setText(data.get(position).getState());

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView orderNum;
        TextView price;
        ImageView image;
        TextView state;

        private int myPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.order_card_title);
            this.orderNum = itemView.findViewById(R.id.order_card_number);
            this.price = itemView.findViewById(R.id.order_card_price);
            this.image = itemView.findViewById(R.id.order_card_image);
            this.state = itemView.findViewById(R.id.order_card_state);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnItemClickListener != null) {
                        mOnItemClickListener.onRecyclerItemClick(myPosition);
                    }
                }
            });
        }

        public void bindData(int position) {
            myPosition = position;
        }

    }

    private ProviderServicesAdaptor.OnRecyclerItemClickListener mOnItemClickListener;

    public void setRecyclerItemClickListener(ProviderServicesAdaptor.OnRecyclerItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnRecyclerItemClickListener {
        void onRecyclerItemClick(int position);
    }
}
