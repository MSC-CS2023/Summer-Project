package com.example.myapplication.frontendCustomer.AccountPage.Order;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.Order;
import com.example.myapplication.Bean.Httpdata.User;
import com.example.myapplication.Bean.Httpdata.data.OrderData;
import com.example.myapplication.network.Constant;
import com.example.myapplication.R;
import com.example.myapplication.network.CustomerApi;
import com.example.myapplication.network.RetrofitClient;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class CustomerOrderPageFinished extends AppCompatActivity {

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

    ImageButton submit;
    private SeekBar ratingSeekBar;

    ImageButton message;

    int mark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_page_finished);

        SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        this.token = sp.getString("token", "");
        this.orderId = getIntent().getLongExtra("orderId", 0);

        initialView();

        updateOrder(token, orderId);
        updateProvider();

        //seekBar part

        ratingSeekBar = findViewById(R.id.ratingSeekBar);
        ratingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mark = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // operation when start drag the controls
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mark = seekBar.getProgress();
                Toast.makeText(CustomerOrderPageFinished.this, "you choose " + mark + " marks", Toast.LENGTH_SHORT).show();
            }
        });


        //submit button
        submit = findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markOrder(token, orderId, mark);
            }
        });

        message = findViewById(R.id.btn_message);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustomerOrderPageFinished.this, "click message", Toast.LENGTH_SHORT).show();
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

    private void updateProvider(){
    }

    @SuppressLint("CheckResult")
    private void markOrder(String token, Long orderId, Integer mark){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.markOrder(token, orderId, mark)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<OrderData>>() {
                    @Override
                    public void onNext(HttpBaseBean<OrderData> orderDataHttpBaseBean) {
                        if(orderDataHttpBaseBean.getSuccess()){
                            Toast.makeText(getApplicationContext(), "Mark successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), orderDataHttpBaseBean.getMessage(), Toast.LENGTH_SHORT).show();
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
}