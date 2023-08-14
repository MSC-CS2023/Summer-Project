package com.example.myapplication.frontendProvider.homePages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.manager.RequestTracker;
import com.bumptech.glide.request.target.BitmapThumbnailImageViewTarget;
import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.data.ServiceDetailData;
import com.example.myapplication.R;
import com.example.myapplication.network.ProviderApi;
import com.example.myapplication.network.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class ProviderCreateServiceActivity extends AppCompatActivity implements View.OnClickListener{
    private String token;

    private Toolbar toolbar;
    private ImageButton save;
    private EditText title;
    private EditText description;
    private EditText price;
    private EditText address;
    private ImageView image;
    private Button uploadImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_create_service);
        SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        this.token = sp.getString("token", "");

        initView();
    }

    private void initView() {

        save = findViewById(R.id.btn_save);
        save.setOnClickListener(this);
        title = findViewById(R.id.edtxt_title);
        description = findViewById(R.id.edtxt_description);
        price = findViewById(R.id.edtxt_price);
        address = findViewById(R.id.edtxt_address);
        uploadImage = findViewById(R.id.btn_upload_image);
        uploadImage.setOnClickListener(this);

        //这个image目前的visibility是gone,上传之后要改成visible
        image = findViewById(R.id.img_uploaded);

        //click on back button
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_save) {
//            createService(token, title.getText().toString(),
//                    description.getText().toString(), Double.parseDouble(price.getText().toString()));
            Toast.makeText(this, "Save clicked", Toast.LENGTH_SHORT).show();
            finish();
        } else if(view.getId() == R.id.btn_upload_image) {
            //调用安卓内置上传图片控件来上传图片，image这个控件要把visibility属性改成visible
            Toast.makeText(this, "Upload clicked", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("CheckResult")
    private void createService(String token, String title, String description, Double price){
        ProviderApi providerApi = RetrofitClient.getInstance().getService(ProviderApi.class);
        providerApi.addService(token, title, description, price)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<ServiceDetailData>>() {
                    @Override
                    public void onNext(HttpBaseBean<ServiceDetailData> serviceDetailDataHttpBaseBean) {
                        if(serviceDetailDataHttpBaseBean.getSuccess()){
                            Toast.makeText(getApplicationContext(),
                                    "Create Successfully! ", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(),
                                    serviceDetailDataHttpBaseBean.getMessage(), Toast.LENGTH_SHORT).show();
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