package com.example.myapplication.frontendCustomer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.Service;
import com.example.myapplication.Bean.Httpdata.data.ServiceDetailData;
import com.example.myapplication.R;
import com.example.myapplication.network.PublicMethodApi;
import com.example.myapplication.network.RetrofitClient;
import com.google.errorprone.annotations.RestrictedApi;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class CustomerServiceDetailPage extends AppCompatActivity {

    private String token;
    private Long serviceId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service_detail_page);
    }

    @SuppressLint("CheckResult")
    private void getServiceDetail(Long serviceId){
        PublicMethodApi publicMethodApi = RetrofitClient.getInstance().getService(PublicMethodApi.class);
        publicMethodApi.getServiceDetail(serviceId)
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

    private void updateView(Service service){
    }
}