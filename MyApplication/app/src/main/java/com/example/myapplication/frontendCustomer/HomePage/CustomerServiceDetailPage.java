package com.example.myapplication.frontendCustomer.HomePage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.Service;
import com.example.myapplication.Bean.Httpdata.data.FavouriteData;
import com.example.myapplication.Bean.Httpdata.data.OrderData;
import com.example.myapplication.Bean.Httpdata.data.ServiceDetailData;
import com.example.myapplication.Constant;
import com.example.myapplication.R;
import com.example.myapplication.network.CustomerApi;
import com.example.myapplication.network.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class CustomerServiceDetailPage extends AppCompatActivity implements View.OnClickListener {


    ImageButton pay;
    ImageButton avatar;
    ImageButton collection;
    ImageView serviceImg;

    TextView serviceTitle;
    TextView providerName;
    TextView serviceDescribe;
    TextView addressDetail;
    TextView amount;

    TextView balance;
    View view;

    boolean isCollection = false;

    private String token;
    private Long serviceId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service_detail_page);

        SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        this.token = sp.getString("token", "");
        this.serviceId = getIntent().getLongExtra("serviceId", 0);

        initializeView();
//        getServiceDetail(this.token, this.serviceId);
//        checkIfIsFavourite(this.token, this.serviceId);
    }

    private void initializeView(){
        this.pay = findViewById(R.id.pay);
        this.avatar = findViewById(R.id.providerAvatar);
        this.collection = findViewById(R.id.collection);
        this.amount = findViewById(R.id.amount);
        this.serviceTitle = findViewById(R.id.serviceTitle);
        this.providerName = findViewById(R.id.providerName);
        this.serviceDescribe = findViewById(R.id.serviceDescribe);
        this.addressDetail = findViewById(R.id.addressDetail);
        this.serviceImg = findViewById(R.id.serviceImg);

        pay.setOnClickListener(this);
        avatar.setOnClickListener(this);
        collection.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == pay.getId()){
            //alert to payment
            paymentAlert();
        } else if (v.getId() == avatar.getId()) {
            // jump to provider account page
        } else if (v.getId() == collection.getId()) {
            // add this service to collection list
            isCollection = !isCollection;
            if (isCollection){
                collection.setImageResource(R.drawable.btn_redheart);
            }else {
                collection.setImageResource(R.drawable.btn_emptyheart);
            }
//            if(isCollection){
//                removeFromFavourite(token, serviceId);
//            }else {
//                addToFavourite(token, serviceId);
//            }
        }

    }

    private void paymentAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerServiceDetailPage.this);

        builder.setTitle("Are you sure to pay ?")
               .setMessage("You need to pay" + amount.getText().toString())
               .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       //update money
                       createCustomerOrder(token, serviceId, System.currentTimeMillis() + 36000000,
                               System.currentTimeMillis() + 39600000);
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

    @SuppressLint("CheckResult")
    private void getServiceDetail(String token, Long serviceId){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.customerGetServiceDetail(token, serviceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<ServiceDetailData>>() {
                    @Override
                    public void onNext(HttpBaseBean<ServiceDetailData> serviceDetailDataHttpBaseBean) {
                        if(serviceDetailDataHttpBaseBean.getSuccess()){
                            updateView(serviceDetailDataHttpBaseBean.getData().getService());
                        }else{
                            //test
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

    @SuppressLint("SetTextI18n")
    private void updateView(Service service){
        serviceTitle.setText(service.getTitle());
        providerName.setText(service.getUsername());
        serviceDescribe.setText(service.getDescription());
        addressDetail.setText(service.getAddress());
        amount.setText("￡" + service.getFee().toString());
        Glide.with(getApplicationContext()).load(Constant.BASE_URL +
                "public/service_provider/avatar?id=" + service.getProviderId().toString()).into(avatar);
        Glide.with(getApplicationContext()).load(Constant.BASE_URL +
                "get_pic?id=" + service.getPictureId()).into(serviceImg);
    }

    //create new order.
    @SuppressLint("CheckResult")
    private void createCustomerOrder(String token, Long serviceId, Long startTime, Long endTime){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.createCustomerOrder(token, serviceId, startTime, endTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<OrderData>>() {
                    @Override
                    public void onNext(HttpBaseBean<OrderData> orderDataHttpBaseBean) {
                        if(orderDataHttpBaseBean.getSuccess()){
                            Toast.makeText(getApplicationContext(),
                                    "Book service successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            //test
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
    private void checkIfIsFavourite(String token, Long serviceId){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.checkIfInFavourite(token, serviceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<Object>>() {
                    @Override
                    public void onNext(HttpBaseBean<Object> objectHttpBaseBean) {
                        if(objectHttpBaseBean.getSuccess()){
                            isCollection = true;
                            collection.setImageResource(R.drawable.btn_redheart);
                        }else{
                            isCollection = false;
                            collection.setImageResource(R.drawable.btn_emptyheart);
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

    //add service to favourite list
    @SuppressLint("CheckResult")
    private void addToFavourite(String token, Long serviceId){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.addToFavourite(token, serviceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<FavouriteData>>() {
                    @Override
                    public void onNext(HttpBaseBean<FavouriteData> favouriteDataHttpBaseBean) {
                        if(favouriteDataHttpBaseBean.getSuccess()){
                            isCollection = true;
                            collection.setImageResource(R.drawable.btn_redheart);
                            Toast.makeText(getApplicationContext(),
                                    "Add to favourites successfully!", Toast.LENGTH_SHORT).show();
                        }else{

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

    //remove service from favourite list
    @SuppressLint("CheckResult")
    private void removeFromFavourite(String token, Long serviceId){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.removeFromFavourite(token, serviceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<Object>>() {
                    @Override
                    public void onNext(HttpBaseBean<Object> objectHttpBaseBean) {
                        if(objectHttpBaseBean.getSuccess()){
                            isCollection = false;
                            collection.setImageResource(R.drawable.btn_emptyheart);
                            Toast.makeText(getApplicationContext(),
                                    "Remove from favourites successfully!", Toast.LENGTH_SHORT).show();
                        }else{

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