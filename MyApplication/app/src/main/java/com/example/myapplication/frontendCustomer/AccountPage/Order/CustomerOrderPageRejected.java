package com.example.myapplication.frontendCustomer.AccountPage.Order;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.Order;
import com.example.myapplication.Bean.Httpdata.User;
import com.example.myapplication.Bean.Httpdata.data.OrderData;
import com.example.myapplication.Bean.Httpdata.data.SelfDetailData;
import com.example.myapplication.network.Constant;
import com.example.myapplication.R;
import com.example.myapplication.network.CustomerApi;
import com.example.myapplication.network.PublicMethodApi;
import com.example.myapplication.network.RetrofitClient;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class CustomerOrderPageRejected extends AppCompatActivity {

    private String token;
    private Long orderId;
    private Order order = new Order();
    private User provider = new User();

    CircleImageView providerAvatar;
    TextView providerName;
    TextView providerEmail;
    TextView providerAddress;

    TextView title;
    ImageView image;
    TextView orderNumber;
    TextView state;

    ImageButton message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_page_rejected);

        SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        this.token = sp.getString("token", "");
        this.orderId = getIntent().getLongExtra("orderId", 0);

        initialView();

        updateOrder(token, orderId);
        updateProvider();

        message = findViewById(R.id.btn_message);

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustomerOrderPageRejected.this, "click message", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initialView(){
        title = findViewById(R.id.order_detail_title);
        image = findViewById(R.id.order_detail_image);
        orderNumber = findViewById(R.id.order_detail_number);
        state = findViewById(R.id.order_detail_state);

        providerName = findViewById(R.id.txt_provider_name);
        providerEmail = findViewById(R.id.txt_email);
        providerAddress = findViewById(R.id.txt_address);
        providerAvatar = findViewById(R.id.img_avatar);
    }

    private void updateOrderView(){
        title.setText(order.getServiceShort().getTitle());
        if(order.getId() != null){
            orderNumber.setText(order.getId().toString());
        }
        state.setText(order.getState());
        if(order.getServiceShort().getPictureId() != null){
            Glide.with(this)
                    .load(Constant.BASE_URL + "get_pic?id=" + order.getServiceShort().getPictureId())
                    .apply(Constant.pictureOptions)
                    .into(image);
        }
    }

    private void updateProviderView(){
        providerName.setText(provider.getUsername());
        providerAddress.setText(provider.getAddress());
        providerEmail.setText(provider.getEmail());
        if(provider.getId() != null){
            Glide.with(this)
                    .load(Constant.BASE_URL + "public/service_provider/avatar?id=" + provider.getId())
                    .apply(Constant.avatarOptions)
                    .into(providerAvatar);
        }
    }

    @SuppressLint("CheckResult")
    private void updateOrder(String token, Long orderId){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.getCertainCustomerOrder(token, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<OrderData>>() {
                    @Override
                    public void onNext(HttpBaseBean<OrderData> orderDataHttpBaseBean) {
                        if(orderDataHttpBaseBean.getSuccess()){
                            try {
                                order = orderDataHttpBaseBean.getData().getBookingOrder();
                                updateOrderView();
                            }catch (NullPointerException ignored){}
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "Network error! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("CheckResult")
    private void updateProvider(){
        PublicMethodApi publicMethodApi = RetrofitClient.getInstance().getService(PublicMethodApi.class);
        publicMethodApi.getProviderDetail(order.getServiceShort().getProviderId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<SelfDetailData>>() {
                    @Override
                    public void onNext(HttpBaseBean<SelfDetailData> selfDetailDataHttpBaseBean) {
                        if(selfDetailDataHttpBaseBean.getSuccess()){
                            try {
                                provider = selfDetailDataHttpBaseBean.getData().getUser();
                                updateProviderView();
                            }catch (NullPointerException ignored){}
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "Network error! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {}
                });
    }
}