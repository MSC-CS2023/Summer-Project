package com.example.myapplication.frontendProvider.loginPages;

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
import com.example.myapplication.R;
import com.example.myapplication.frontendCustomer.CustomerMainActivity;
import com.example.myapplication.frontendCustomer.loginPage.CustomerRegister;
import com.example.myapplication.frontendProvider.ProviderMain;
import com.example.myapplication.network.Constant;
import com.example.myapplication.network.ProviderApi;
import com.example.myapplication.network.PublicMethodApi;
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
import retrofit2.http.Multipart;

public class ProviderRegister extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnRegisterProviderBack;
    ImageButton btnRegisterProviderUploadPortrait;
    ImageButton btnRegisterProvider;
    Bitmap bitmap;


    EditText txtRegisterProviderName;
    EditText txtRegisterProviderNumber;
    EditText txtRegisterProviderEmail;
    EditText txtRegisterProviderPassword1;
    EditText txtRegisterProviderPassword2;
    EditText txtRegisterProviderAddress;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_register);
        bitmap = null;

        btnRegisterProviderBack = findViewById(R.id.btn_back_login);
        btnRegisterProviderBack.setOnClickListener(this);
        btnRegisterProviderUploadPortrait = findViewById(R.id.btn_upload_portrait);
        btnRegisterProviderUploadPortrait.setOnClickListener(this);
        btnRegisterProvider = findViewById(R.id.btn_provider_register);
        btnRegisterProvider.setOnClickListener(this);

        txtRegisterProviderName = findViewById(R.id.txtRegisterProviderName);
        txtRegisterProviderNumber = findViewById(R.id.txtRegisterProviderNumber);
        txtRegisterProviderEmail = findViewById(R.id.txtRegisterProviderEmail);
        txtRegisterProviderPassword1 = findViewById(R.id.txtRegisterProviderPassword1);
        txtRegisterProviderPassword2 = findViewById(R.id.txtRegisterProviderPassword2);
        txtRegisterProviderAddress = findViewById(R.id.txtRegisterProviderAddress);

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_back_login) {
            Intent intentToProviderLoginPage = new Intent(ProviderRegister.this, ProviderLogin.class);
            startActivity(intentToProviderLoginPage);
            finish();
        } else if (view.getId() == R.id.btn_upload_portrait) {
            openGallery();
        } else if (view.getId() == R.id.btn_provider_register) {
            if(checkRegisterDetail()){
                providerRegister(
                        txtRegisterProviderEmail.getText().toString(), txtRegisterProviderName.getText().toString(),
                        txtRegisterProviderPassword1.getText().toString(), txtRegisterProviderAddress.getText().toString(),
                        txtRegisterProviderNumber.getText().toString());
            }
        }

    }

    private Boolean checkRegisterDetail(){
        if(txtRegisterProviderName.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Username is mandatory!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(txtRegisterProviderEmail.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Email is mandatory!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(txtRegisterProviderPassword1.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Password is mandatory!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!txtRegisterProviderPassword1.getText().toString()
                .equals(txtRegisterProviderPassword2.getText().toString())){
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
                } catch (IOException ignored) {
                }
                Glide.with(this).load(bitmap).into(btnRegisterProviderUploadPortrait);
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
    private void providerRegister(String email, String username, String password, String address, String tel){
        address = address.equals("")? null : address;
        tel = tel.equals("")? null : tel;

        PublicMethodApi publicMethodApi = RetrofitClient.getInstance().getService(PublicMethodApi.class);
        publicMethodApi.providerRegister(email, username, password, address, tel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<LoginData>>() {
                    @Override
                    public void onNext(HttpBaseBean<LoginData> loginDataHttpBaseBean) {
                        if(loginDataHttpBaseBean.getSuccess()){
                            try{
                                SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
                                sp.edit().putBoolean("isLoggedIn", true).apply();
                                sp.edit().putString("userType", "provider").apply();
                                sp.edit().putString("token", loginDataHttpBaseBean.getData().getToken()).apply();
                                sp.edit().putLong("exp", loginDataHttpBaseBean.getData().getExp()).apply();
                                updateAvatar(loginDataHttpBaseBean.getData().getToken(),
                                        loginDataHttpBaseBean.getData().getUser().getUsername());
                                Toast.makeText(getApplicationContext(),
                                        loginDataHttpBaseBean.getData().getUser().getUsername() + "sign up successfully",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ProviderRegister.this, CustomerMainActivity.class));
                                finishAffinity();
                            }catch (Exception ignored){}
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    loginDataHttpBaseBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }
                    @Override
                    public void onComplete() {}
                });

    }

    @SuppressLint("CheckResult")
    private void updateAvatar(String token, String username){
        if(bitmap == null){
            return;
        }
        File file = bitmapToFile(bitmap);
        MultipartBody.Part part = MultipartBody.Part.createFormData("avatar", username + "Avatar.jpg",
                RequestBody.create(MediaType.parse("application/octet-stream"), file));
        ProviderApi providerApi = RetrofitClient.getInstance().getService(ProviderApi.class);
        providerApi.updateProviderAvatar(token, part);
    }
}