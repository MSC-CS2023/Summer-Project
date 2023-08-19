package com.example.myapplication.frontendProvider.orderPages;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.myapplication.frontendProvider.ProviderVisitOtherUserActivity;
import com.example.myapplication.frontendProvider.messagePages.ProviderMessageDetailActivity;
import com.example.myapplication.network.ProviderApi;
import com.example.myapplication.network.PublicMethodApi;
import com.example.myapplication.network.RetrofitClient;
import com.google.gson.Gson;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class ProviderOrderDetailUnconfirmedActivity extends AppCompatActivity implements View.OnClickListener{

    private String token;
    private Long orderId;
    private Order order = new Order();
    private User customer = new User();

    Toolbar toolbar;
    TextView title;
    ImageView image;
    TextView orderNumber;
    TextView state;
    Button confirm;
    Button reject;

    //下单客户的信息相关View属性
    ImageView avatar;
    TextView username;
    TextView email;
    TextView address;
    ImageButton message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_order_detail_unconfirmed);

        SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        this.token = sp.getString("token", "");
        this.orderId = getIntent().getLongExtra("orderId", 0);

        initView();
        updateOrder(token, orderId);
    }

    private void initView() {
        title = findViewById(R.id.order_detail_title);
        image = findViewById(R.id.order_detail_image);
        orderNumber = findViewById(R.id.order_detail_number);
        state = findViewById(R.id.order_detail_state);
        confirm = findViewById(R.id.btn_confirm);
        confirm.setOnClickListener(this);
        reject = findViewById(R.id.btn_reject);
        reject.setOnClickListener(this);

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
        if(view.getId() == R.id.btn_confirm) {
            confirmOrder(token, orderId);
        } else if(view.getId() == R.id.btn_reject) {
            alertDialog();
        } else if(view.getId() == R.id.img_avatar) {
            Intent intentToOtherUser = new Intent(this, ProviderVisitOtherUserActivity.class);
            startActivity(intentToOtherUser);
        } else if(view.getId() == R.id.btn_message) {
            Intent intentToMessage = new Intent(this, ProviderMessageDetailActivity.class);
            startActivity(intentToMessage);
        }
    }

    private void alertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ProviderOrderDetailUnconfirmedActivity.this);

        builder.setTitle("Are you sure to reject?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rejectOrder(token, orderId);
                    }
                })
                .setNegativeButton("Cancel", null);

        AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                positiveButton.setTextColor(R.color.black);
                negativeButton.setTextColor(R.color.black);
            }
        });
        alertDialog.show();
    }

    private void updateView(){
        title.setText(order.getServiceShort().getTitle());
        if(order.getId() != null){
            orderNumber.setText(order.getId().toString());
        }
        state.setText("");
        Glide.with(this)
                .load(Constant.BASE_URL + "get_pic?id=" + order.getServiceShort().getPictureId())
                .apply(Constant.pictureOptions)
                .into(image);
    }

    private void updateCustomerView(){
        username.setText(customer.getUsername());
        address.setText(customer.getAddress());
        email.setText(customer.getEmail());
        if(customer.getId() != null){
            Glide.with(this)
                    .load(Constant.BASE_URL + "public/service_provider/avatar?id=" + customer.getId())
                    .apply(Constant.avatarOptions)
                    .into(image);
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

    @SuppressLint("CheckResult")
    private void confirmOrder(String token, Long orderId){
        ProviderApi providerApi = RetrofitClient.getInstance().getService(ProviderApi.class);
        providerApi.confirmProviderOrder(token, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<OrderData>>() {
                    @Override
                    public void onNext(HttpBaseBean<OrderData> orderDataHttpBaseBean) {
                        if(orderDataHttpBaseBean.getSuccess()){
                            Toast.makeText(getApplicationContext(),
                                    "Confirm order successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    orderDataHttpBaseBean.getMessage(), Toast.LENGTH_SHORT).show();
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
    private void rejectOrder(String token, Long orderId){
        ProviderApi providerApi = RetrofitClient.getInstance().getService(ProviderApi.class);
        providerApi.rejectProviderOrder(token, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<OrderData>>() {
                    @Override
                    public void onNext(HttpBaseBean<OrderData> orderDataHttpBaseBean) {
                        if(orderDataHttpBaseBean.getSuccess()){
                            Toast.makeText(getApplicationContext(),
                                    "Reject order successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    orderDataHttpBaseBean.getMessage(), Toast.LENGTH_SHORT).show();
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
    private void updateCustomer(){
        PublicMethodApi publicMethodApi = RetrofitClient.getInstance().getService(PublicMethodApi.class);
        publicMethodApi.getCustomerDetail(order.getCustomerId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<SelfDetailData>>() {
                    @Override
                    public void onNext(HttpBaseBean<SelfDetailData> selfDetailDataHttpBaseBean) {
                        if(selfDetailDataHttpBaseBean.getSuccess()){
                            customer = selfDetailDataHttpBaseBean.getData().getUser();
                            updateCustomer();
                            updateCustomerView();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}