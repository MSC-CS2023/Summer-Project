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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.Service;
import com.example.myapplication.Bean.Httpdata.data.ServiceDetailData;
import com.example.myapplication.network.Constant;
import com.example.myapplication.R;
import com.example.myapplication.network.ProviderApi;
import com.example.myapplication.network.PublicMethodApi;
import com.example.myapplication.network.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class ProviderServiceDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private String token;
    private Long serviceId;
    private Service service = new Service();

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

    private TextView serviceType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_service_detail);

        SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        this.token = sp.getString("token", "");
        this.serviceId = getIntent().getLongExtra("serviceId", 0);

        initView();
        getServiceDetail();
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
        serviceType = findViewById(R.id.service_type);

        //click on back button
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_edit) {
            setContentView(R.layout.activity_provider_service_detail_editable);
            initEditView();
            updateView();
        } else if(view.getId() == R.id.btn_delete) {
            deleteAlert();
        } else if(view.getId() == R.id.btn_save) {
            modifyService();
            setContentView(R.layout.activity_provider_service_detail);
            initView();
            getServiceDetail();
        } else if(view.getId() == R.id.btn_cancel) {
            setContentView(R.layout.activity_provider_service_detail);
            initView();
            updateView();
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
        serviceType = findViewById(R.id.service_type);

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
                        deleteService(token, serviceId);
                        Toast.makeText(getApplicationContext(), "Delete", Toast.LENGTH_SHORT).show();
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
        if(service.getFee() != null){
            price.setText(service.getFee().toString());
        }
        address.setText(service.getAddress());
        serviceType.setText(service.getTag());
        Glide.with(getApplicationContext()).load(Constant.BASE_URL +
                "get_pic?id=" + service.getPictureId())
                .apply(Constant.pictureOptions)
                .into(serviceImg);
    }

    private void modifyService(){
        if(service.getTitle() == null || !service.getTitle().equals(title.getText().toString())){
            modifyServiceDetail(token, serviceId, "title", title.getText().toString());
        }
        if(service.getDescription() == null || !service.getDescription().equals(description.getText().toString())){
            modifyServiceDetail(token, serviceId, "description", description.getText().toString());
        }
        if(service.getFee() == null || !service.getFee().toString().equals(price.getText().toString())){
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
                            try {
                                service = serviceDetailDataHttpBaseBean.getData().getService();
                                updateView();
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
                            try {
                                service = serviceDetailDataHttpBaseBean.getData().getService();
                            }catch (NullPointerException ignored){}
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