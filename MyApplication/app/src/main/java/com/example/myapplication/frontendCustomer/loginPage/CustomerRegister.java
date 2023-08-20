package com.example.myapplication.frontendCustomer.loginPage;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.data.LoginData;
import com.example.myapplication.Bean.Httpdata.data.TimeStampData;
import com.example.myapplication.frontendCustomer.CustomerMainActivity;
import com.example.myapplication.R;
import com.example.myapplication.network.Constant;
import com.example.myapplication.network.CustomerApi;
import com.example.myapplication.network.ProviderApi;
import com.example.myapplication.network.PublicMethodApi;
import com.example.myapplication.network.RetrofitClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CustomerRegister extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnRegisterCustomer;
    EditText txtRegisterCustomerName;
    EditText txtRegisterCustomerNumber;
    EditText txtRegisterCustomerEmail;
    EditText txtRegisterCustomerPassword1;
    EditText txtRegisterCustomerPassword2;
    EditText txtRegisterCustomerAddress;

    CircleImageView avatar;
    Bitmap bitmap;


    //No About me now.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);
        bitmap = null;

        btnRegisterCustomer = findViewById(R.id.btnRegisterCustomer);
        btnRegisterCustomer.setOnClickListener(this);
        avatar = findViewById(R.id.customerAvatar);
        avatar.setOnClickListener(this);

        txtRegisterCustomerName = findViewById(R.id.txtRegisterCustomerName);
        txtRegisterCustomerNumber = findViewById(R.id.txtRegisterCustomerNumber);
        txtRegisterCustomerEmail = findViewById(R.id.txtRegisterCustomerEmail);
        txtRegisterCustomerPassword1 = findViewById(R.id.txtRegisterCustomerPassword1);
        txtRegisterCustomerPassword2 = findViewById(R.id.txtRegisterCustomerPassword2);
        txtRegisterCustomerAddress = findViewById(R.id.txtRegisterCustomerAddress);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnRegisterCustomer){
            if(checkRegisterDetail()){
                customerRegister(
                        txtRegisterCustomerEmail.getText().toString(), txtRegisterCustomerName.getText().toString(),
                        txtRegisterCustomerPassword1.getText().toString(), txtRegisterCustomerAddress.getText().toString(),
                        txtRegisterCustomerNumber.getText().toString());
            }
        }else if(view.getId() == R.id.customerAvatar){
            openGallery();
        }
    }

    //Maybe we need more limitation about the register details.
    private Boolean checkRegisterDetail(){
        if(txtRegisterCustomerName.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Username is mandatory!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(txtRegisterCustomerEmail.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Email is mandatory!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(txtRegisterCustomerPassword1.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Password is mandatory!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!txtRegisterCustomerPassword1.getText().toString()
                .equals(txtRegisterCustomerPassword2.getText().toString())){
            Toast.makeText(getApplicationContext(), "Entered passwords differ!", Toast.LENGTH_SHORT).show();
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
                Glide.with(this).load(bitmap).into(avatar);
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

    @SuppressLint("CheckResult")
    private void customerRegister(String email, String username, String password, String address, String tel){
        //Optional part for http request.
        address = address.equals("")? null : address;
        tel = tel.equals("")? null : tel;

        PublicMethodApi httpApi = RetrofitClient.getInstance().getService(PublicMethodApi.class);
        httpApi.customerRegister(email, username, password, address, tel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<LoginData>>() {
                    @Override
                    public void onNext(HttpBaseBean<LoginData> loginDataHttpBaseBean) {
                        if(loginDataHttpBaseBean.getSuccess()){
                                SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
                                sp.edit().putBoolean("isLoggedIn", true).apply();
                                sp.edit().putString("userType", "customer").apply();
                                sp.edit().putString("token", loginDataHttpBaseBean.getData().getToken()).apply();
                                sp.edit().putLong("exp", loginDataHttpBaseBean.getData().getExp()).apply();
                                    updateAvatar(loginDataHttpBaseBean.getData().getToken());
                                Toast.makeText(getApplicationContext(),
                                        loginDataHttpBaseBean.getData().getUser().getUsername() + "sign up successfully",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(CustomerRegister.this, CustomerMainActivity.class));
                                finishAffinity();
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    loginDataHttpBaseBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "Network error!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {}
                });
    }

    @SuppressLint("CheckResult")
    private void updateAvatar(String token){
        if(bitmap == null){return;}
        FileOutputStream fos = null;
        File bitmapFile = null;
        try {
            bitmapFile = File.createTempFile("bitmap", ".jpg", getCacheDir());
            fos = new FileOutputStream(bitmapFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            fos.flush();
            fos.close();
            MultipartBody.Part part = MultipartBody.Part.createFormData("avatar", "Avatar.jpg",
                    RequestBody.create(MediaType.parse("application/octet-stream"), bitmapFile));
            CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
            customerApi.updateCustomerAvatar(token, part)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ResourceSubscriber<HttpBaseBean<TimeStampData>>() {
                        @Override
                        public void onNext(HttpBaseBean<TimeStampData> timeStampDataHttpBaseBean) {
                            if(timeStampDataHttpBaseBean.getSuccess()){
                                Log.i(TAG,"SUC! ");
                            }else {
                                Log.i(TAG,"FAL! ");
                            }
                        }

                        @Override
                        public void onError(Throwable t) {
                            Log.i(TAG,"Network error! " + t.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}