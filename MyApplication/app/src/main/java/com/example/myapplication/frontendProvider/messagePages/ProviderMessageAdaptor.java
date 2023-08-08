package com.example.myapplication.frontendProvider.messagePages;

import android.content.Context;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.frontendProvider.homePages.ProviderServicesAdaptor;

import java.util.List;

public class ProviderMessageAdaptor extends RecyclerView.Adapter<ProviderMessageAdaptor.ViewHoder> {

    private List<ProviderMessageCardData> data;
    private Context context;
    public ProviderMessageAdaptor(List<ProviderMessageCardData> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.recyclerview_item_provider_message, null);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        holder.image.setImageResource(context.getResources().getIdentifier(data.get(position).getImageSrc(),
                "drawable", context.getPackageName()));
        holder.username.setText(data.get(position).getUsername());
        holder.latestMessage.setText(data.get(position).getLatestMessage());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView username;
        TextView latestMessage;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.img_avatar);
            this.username = itemView.findViewById(R.id.txt_username);
            this.latestMessage = itemView.findViewById(R.id.txt_latest_message);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnItemClickListener != null) {
                        mOnItemClickListener.onRecyclerItemClick(getAbsoluteAdapterPosition());
                    }
                }
            });
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
