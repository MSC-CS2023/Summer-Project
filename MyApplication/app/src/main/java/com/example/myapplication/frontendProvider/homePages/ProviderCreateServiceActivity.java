package com.example.myapplication.frontendProvider.homePages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.manager.RequestTracker;
import com.bumptech.glide.request.target.BitmapThumbnailImageViewTarget;
import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.data.ServiceDetailData;
import com.example.myapplication.R;
import com.example.myapplication.frontendProvider.loginPages.ProviderRegister;
import com.example.myapplication.network.Constant;
import com.example.myapplication.network.ProviderApi;
import com.example.myapplication.network.RetrofitClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProviderCreateServiceActivity extends AppCompatActivity implements View.OnClickListener{
    private String token;
    private String tag;
    private Bitmap bitmap;

    private Toolbar toolbar;
    private ImageButton save;
    private EditText title;
    private Spinner serviceType;
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
        tag = "Cleaning";
        bitmap = null;

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

        //服务类型的选择点击事件
        serviceType = findViewById(R.id.spinner);
        setSpinner();

        //这个image目前的visibility是gone,上传之后要改成visible
        image = findViewById(R.id.img_uploaded);

        //click on back button
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void setSpinner() {
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.service_type, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        serviceType.setAdapter(spinnerAdapter);

        serviceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if(position == 0) {
                    tag = "Cleaning";
                    Toast.makeText(ProviderCreateServiceActivity.this, "Service type cleaning selected", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    tag = "Maintenance";
                    Toast.makeText(ProviderCreateServiceActivity.this, "Service type maintenance selected", Toast.LENGTH_SHORT).show();
                } else if (position == 2) {
                    tag = "Laundry";
                    Toast.makeText(ProviderCreateServiceActivity.this, "Service type laundry selected", Toast.LENGTH_SHORT).show();
                } else if (position == 3) {
                    tag = "Landscaping";
                    Toast.makeText(ProviderCreateServiceActivity.this, "Service type landscaping selected", Toast.LENGTH_SHORT).show();
                } else if (position == 4) {
                    Toast.makeText(ProviderCreateServiceActivity.this, "Service type others selected", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_save) {
            if(checkServiceDetail()){
                createService(token, title.getText().toString(),
                        description.getText().toString(), Double.parseDouble(price.getText().toString()));
//                Toast.makeText(this, "Save clicked", Toast.LENGTH_SHORT).show();
//                finish();
            }
        } else if(view.getId() == R.id.btn_upload_image) {
            //调用安卓内置上传图片控件来上传图片，image这个控件要把visibility属性改成visible
            openGallery();
            Toast.makeText(this, "Upload clicked", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean checkServiceDetail(){
        if(title.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Title is mandatory!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(description.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Description is mandatory!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(price.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Price is mandatory!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    bitmap = compressImage(uri);
                } catch (IOException ignored) {}
                image.setVisibility(View.VISIBLE);
                Glide.with(this).load(bitmap).into(image);
            }
        }
    }

    private Bitmap compressImage(Uri imageUri) throws IOException {
        ContentResolver contentResolver = getContentResolver();
        InputStream inputStream = contentResolver.openInputStream(imageUri);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        inputStream.close();
        inputStream = contentResolver.openInputStream(imageUri);
        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;
        int scaleFactor = calculateScaleFactor(imageWidth, imageHeight);
        options.inSampleSize = scaleFactor;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
        inputStream.close();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
        while (outputStream.toByteArray().length > Constant.MAX_IMAGE_SIZE) {
            outputStream.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
        }
        return bitmap;
    }

    private int calculateScaleFactor(int width, int height) {
        int scaleFactor = 1;
        while ((width / scaleFactor) > 1200 || (height / scaleFactor) > 1200) {
            scaleFactor *= 2;
        }
        return scaleFactor;
    }

    private File bitmapToFile(Bitmap bitmap) {
        File imageFile = new File(getCacheDir(), "image_file_name.jpg");
        try (OutputStream outputStream = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }

    @SuppressLint("CheckResult")
    private void createService(String token, String title, String description, Double price){
        ProviderApi providerApi = RetrofitClient.getInstance().getService(ProviderApi.class);
        providerApi.addService(token, title, description, price, tag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<ServiceDetailData>>() {
                    @Override
                    public void onNext(HttpBaseBean<ServiceDetailData> serviceDetailDataHttpBaseBean) {
                        if(serviceDetailDataHttpBaseBean.getSuccess()){
                            try {
                                addPicture(token, serviceDetailDataHttpBaseBean.getData().getService().getId());
                                Toast.makeText(getApplicationContext(),
                                        "Create Successfully! ", Toast.LENGTH_SHORT).show();
                                finish();
                            }catch (NullPointerException ignored){}
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
                    public void onComplete() {}
                });
    }

    private void addPicture(String token, Long serviceId){
        if(bitmap == null){
            return;
        }
        File file = bitmapToFile(bitmap);
        MultipartBody.Part part = MultipartBody.Part.createFormData("picture", "Picture.jpg",
                RequestBody.create(MediaType.parse("application/octet-stream"), file));
        ProviderApi providerApi = RetrofitClient.getInstance().getService(ProviderApi.class);
        providerApi.addPictureToService(token, serviceId, part);
    }
}