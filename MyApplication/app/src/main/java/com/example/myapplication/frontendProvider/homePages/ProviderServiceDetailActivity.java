package com.example.myapplication.frontendProvider.homePages;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.myapplication.Bean.Httpdata.data.ServiceDetailData;
import com.example.myapplication.Constant;
import com.example.myapplication.R;
import com.example.myapplication.network.ProviderApi;
import com.example.myapplication.network.PublicMethodApi;
import com.example.myapplication.network.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;
import retrofit2.http.Part;

public class ProviderServiceDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private String token;
    private Long serviceId;
    private Service service;

    private Toolbar toolbar;
    private ImageButton edit;
    private ImageButton delete;
    private ImageButton save;
    private ImageButton cancel;
    ImageView serviceImg;
    private TextView title;
    private TextView description;
    private TextView price;
    private TextView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_service_detail);

        SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        this.token = sp.getString("token", "");
        this.serviceId = getIntent().getLongExtra("serviceId", 0);

//        service = new Service();
//        service.setTitle("123");
//        service.setDescription("sadafaafas");
//        service.setFee(5.0);
//        service.setAddress("bs1");
//        service.setPictureId("1");

        initView();
//        getServiceDetail();
    }

    private void initView() {
        edit = findViewById(R.id.btn_edit);
        edit.setOnClickListener(this);
        delete = findViewById(R.id.btn_delete);
        delete.setOnClickListener(this);

        //还需要获取各个可更改文本的view控件填入对应的data
        title = findViewById(R.id.detail_title);
        description = findViewById(R.id.detail_description);
        price = findViewById(R.id.detail_price);
        address = findViewById(R.id.detail_address);
        serviceImg = findViewById(R.id.detail_image);

        //click on back button
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_edit) {
            setContentView(R.layout.activity_provider_service_detail_editable);
            initEditView();
//            updateView();
        } else if(view.getId() == R.id.btn_delete) {
            deleteAlert();
        } else if(view.getId() == R.id.btn_save) {
//            modifyService();
            Toast.makeText(this, "save clicked", Toast.LENGTH_SHORT).show();
            finish();
        } else if(view.getId() == R.id.btn_cancel) {
            setContentView(R.layout.activity_provider_service_detail);
            initView();
        }
    }

    private void initEditView() {
        save = findViewById(R.id.btn_save);
        save.setOnClickListener(this);
        cancel = findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(this);

        //还需要获取各个可更改文本的editView控件进行设置(编辑时还显示原来的标题等信息是用的hint,这个主要改hint应该是）
        title = findViewById(R.id.edtxt_title);
        description = findViewById(R.id.edtxt_description);
        price = findViewById(R.id.edtxt_price);
        address = findViewById(R.id.edtxt_address);
        serviceImg = findViewById(R.id.ed_image);

        //click on back button
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void deleteAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete service!")
                .setMessage("Are you sure to DELETE this service?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        deleteService(token, serviceId);
                        Toast.makeText(getApplicationContext(), "delete", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton("Cancel", null);

        AlertDialog deleteAlert = builder.create();
        deleteAlert.setOnShowListener(new DialogInterface.OnShowListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton = deleteAlert.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = deleteAlert.getButton(AlertDialog.BUTTON_NEGATIVE);

                positiveButton.setTextColor(R.color.black);
                negativeButton.setTextColor(R.color.black);
            }
        });

        deleteAlert.show();
    }

    private void updateView(){
        title.setText(service.getTitle());
        description.setText(service.getDescription());
        price.setText(service.getFee().toString());
        address.setText(service.getAddress());
        Glide.with(getApplicationContext()).load(Constant.BASE_URL +
                "get_pic?id=" + service.getPictureId()).into(serviceImg);
    }

    private void modifyService(){
        if(!service.getTitle().equals(title.getText().toString())){
            modifyServiceDetail(token, serviceId, "title", title.getText().toString());
        }
        if(!service.getDescription().equals(description.getText().toString())){
            modifyServiceDetail(token, serviceId, "description", description.getText().toString());
        }
        if(!service.getAddress().equals(address.getText().toString())){
            modifyServiceDetail(token, serviceId, "address", address.getText().toString());
        }
        if(!service.getFee().toString().equals(price.getText().toString())){
            modifyServiceDetail(token, serviceId, "fee", price.getText().toString());
        }
    }

    @SuppressLint("CheckResult")
    private void getServiceDetail(){
        PublicMethodApi publicMethodApi = RetrofitClient.getInstance().getService(PublicMethodApi.class);
        publicMethodApi.getServiceDetail(serviceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<ServiceDetailData>>() {
                    @Override
                    public void onNext(HttpBaseBean<ServiceDetailData> serviceDetailDataHttpBaseBean) {
                        if(serviceDetailDataHttpBaseBean.getSuccess()){
                            service = serviceDetailDataHttpBaseBean.getData().getService();
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
    private void deleteService(String token, Long serviceId){
        ProviderApi providerApi = RetrofitClient.getInstance().getService(ProviderApi.class);
        providerApi.deleteService(token, serviceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<Object>>() {
                    @Override
                    public void onNext(HttpBaseBean<Object> objectHttpBaseBean) {
                        if(objectHttpBaseBean.getSuccess()){
                            Toast.makeText(getApplicationContext(), "Delete successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), objectHttpBaseBean.getMessage(), Toast.LENGTH_SHORT).show();
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
    private void modifyServiceDetail(String token, Long serviceId, String modifyItem, String modifyContent){
        ProviderApi providerApi = RetrofitClient.getInstance().getService(ProviderApi.class);
        providerApi.modifyService(token, serviceId, modifyItem, modifyContent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<ServiceDetailData>>() {
                    @Override
                    public void onNext(HttpBaseBean<ServiceDetailData> serviceDetailDataHttpBaseBean) {
                        if(serviceDetailDataHttpBaseBean.getSuccess()){
                            service = serviceDetailDataHttpBaseBean.getData().getService();
                        }else {

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