package com.example.myapplication.frontendProvider.orderPages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.example.myapplication.network.Constant;
import com.example.myapplication.R;
import com.example.myapplication.frontendProvider.ProviderVisitOtherUserActivity;
import com.example.myapplication.frontendProvider.messagePages.ProviderMessageDetailActivity;
import com.example.myapplication.network.ProviderApi;
import com.example.myapplication.network.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class ProviderOrderDetailCancelledActivity extends AppCompatActivity implements View.OnClickListener{
    private String token;
    private Long orderId;
    private Order order = new Order();
    private User customer = new User();

    Toolbar toolbar;
    TextView title;
    ImageView image;
    TextView orderNumber;
    TextView state;

    //下单客户的信息相关View属性
    ImageView avatar;
    TextView username;
    TextView email;
    TextView address;

    ImageButton message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_order_detail_rejected);

        SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        this.token = sp.getString("token", "");
        this.orderId = getIntent().getLongExtra("orderId", 0);

        initView();
        updateOrder(token, orderId);
        updateCustomer();
    }

    private void initView() {
        title = findViewById(R.id.order_detail_title);
        image = findViewById(R.id.order_detail_image);
        orderNumber = findViewById(R.id.order_detail_number);
        state = findViewById(R.id.order_detail_state);

        //用户信息相关
        avatar = findViewById(R.id.img_avatar);
        avatar.setOnClickListener(this);
        username = findViewById(R.id.txt_customer_name);
        email = findViewById(R.id.txt_email);
        address = findViewById(R.id.txt_address);

        message = findViewById(R.id.btn_message);
        message.setOnClickListener(this);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.img_avatar) {
            Intent intentToOtherUser = new Intent(this, ProviderVisitOtherUserActivity.class);
            startActivity(intentToOtherUser);
        } else if(view.getId() == R.id.btn_message) {
            Intent intentToMessage = new Intent(this, ProviderMessageDetailActivity.class);
            startActivity(intentToMessage);
        }
    }

    private void updateView(){
        title.setText(order.getServiceShort().getTitle());
        if(order.getId() != null){
            orderNumber.setText(order.getId().toString());
        }
        state.setText("");
        if(order.getServiceShort().getPictureId() != null){
            Glide.with(this)
                    .load(Constant.BASE_URL + "get_pic?id=" + order.getServiceShort().getPictureId())
                    .into(image);
        }
    }

    private void updateCustomerView(){
        username.setText(customer.getUsername());
        address.setText(customer.getAddress());
        email.setText(customer.getEmail());
        if(customer.getId() != null){
            Glide.with(this)
                    .load(Constant.BASE_URL + "public/service_provider/avatar?id=" + customer.getId())
                    .into(avatar);
        }
    }

    @SuppressLint("CheckResult")
    private void updateOrder(String token, Long orderId){
        ProviderApi providerApi = RetrofitClient.getInstance().getService(ProviderApi.class);
        providerApi.getCertainProviderOrder(token, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<OrderData>>() {
                    @Override
                    public void onNext(HttpBaseBean<OrderData> orderDataHttpBaseBean) {
                        if(orderDataHttpBaseBean.getSuccess()){
                            order = orderDataHttpBaseBean.getData().getBookingOrder();
                            updateView();
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

    private void updateCustomer(){

    }
}