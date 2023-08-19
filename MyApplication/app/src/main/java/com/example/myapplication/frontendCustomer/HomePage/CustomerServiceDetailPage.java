package com.example.myapplication.frontendCustomer.HomePage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.myapplication.network.Constant;
import com.example.myapplication.R;
import com.example.myapplication.network.CustomerApi;
import com.example.myapplication.network.RetrofitClient;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class CustomerServiceDetailPage extends AppCompatActivity implements View.OnClickListener {

    ImageButton order;
    CircleImageView avatar;
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
        getServiceDetail(this.token, this.serviceId);
        checkIfIsFavourite(this.token, this.serviceId);
    }

    private void initializeView(){
        this.order = findViewById(R.id.order);
        this.avatar = findViewById(R.id.providerAvatar);
        this.collection = findViewById(R.id.collection);
        this.amount = findViewById(R.id.amount);
        this.serviceTitle = findViewById(R.id.serviceTitle);
        this.providerName = findViewById(R.id.providerName);
        this.serviceDescribe = findViewById(R.id.serviceDescribe);
        this.addressDetail = findViewById(R.id.addressDetail);
        this.serviceImg = findViewById(R.id.serviceImg);

        order.setOnClickListener(this);
        avatar.setOnClickListener(this);
        collection.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == order.getId()){
            //alert to payment
            startActivity(new Intent(CustomerServiceDetailPage.this, CustomerMakeOrderPage.class)
                    .putExtra("serviceId", serviceId));
        } else if (v.getId() == avatar.getId()) {
            // jump to provider account page
        } else if (v.getId() == collection.getId()) {
            // add this service to collection list
//            isCollection = !isCollection;
//            if (isCollection){
//                collection.setImageResource(R.drawable.btn_redheart);
//            }else {
//                collection.setImageResource(R.drawable.btn_emptyheart);
//            }
            if(isCollection){
                removeFromFavourite(token, serviceId);
            }else {
                addToFavourite(token, serviceId);
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private void updateView(Service service){
        serviceTitle.setText(service.getTitle());
        providerName.setText(service.getUsername());
        serviceDescribe.setText(service.getDescription());
        addressDetail.setText(service.getAddress());
        if(service.getFee() != null){
            amount.setText("￡" + service.getFee().toString());
        }
        Glide.with(getApplicationContext()).load(Constant.BASE_URL +
                "public/service_provider/avatar?id=" + service.getProviderId().toString()).into(avatar);
        Glide.with(getApplicationContext()).load(Constant.BASE_URL +
                "get_pic?id=" + service.getPictureId()).into(serviceImg);
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
                            try {
                                updateView(serviceDetailDataHttpBaseBean.getData().getService());
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
                    public void onComplete() {}
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
                        }else {
                            Toast.makeText(getApplicationContext(),
                                    favouriteDataHttpBaseBean.getMessage(), Toast.LENGTH_SHORT).show();
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
                        }else {
                            Toast.makeText(getApplicationContext(),
                                    objectHttpBaseBean.getMessage(), Toast.LENGTH_SHORT).show();
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