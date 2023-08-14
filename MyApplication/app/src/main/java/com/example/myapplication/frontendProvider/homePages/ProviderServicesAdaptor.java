package com.example.myapplication.frontendProvider.homePages;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.frontendProvider.messagePages.ProviderMessageAdaptor;

import java.util.ArrayList;
import java.util.List;

public class ProviderServicesAdaptor extends RecyclerView.Adapter<ProviderServicesAdaptor.ViewHolder> {

    private List<ProviderServiceCardData> data;
    private Context context;
    private OnRecyclerItemClickListener mOnItemClickListener;

    public ProviderServicesAdaptor(List<ProviderServiceCardData> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ProviderServicesAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.recyclerview_item_provider_service, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProviderServicesAdaptor.ViewHolder holder, int position) {
        holder.bindData(position);
        holder.title.setText(data.get(position).getTitle());
        holder.description.setText(data.get(position).getDescription());
        holder.price.setText(data.get(position).getPrice());
        holder.image.setImageResource(context.getResources().getIdentifier(data.get(position).getImageSrc(),
                "drawable", context.getPackageName()));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    public void setRecyclerItemClickListener(OnRecyclerItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnRecyclerItemClickListener {
        void onRecyclerItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private TextView price;
        private ImageView image;

        private int myPosition;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.service_card_title);
            description = itemView.findViewById(R.id.service_card_description);
            price = itemView.findViewById(R.id.service_card_price);
            image = itemView.findViewById(R.id.service_card_image);

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


}
