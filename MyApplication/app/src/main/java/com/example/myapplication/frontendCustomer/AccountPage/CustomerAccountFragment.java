package com.example.myapplication.frontendCustomer.AccountPage;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.User;
import com.example.myapplication.Bean.Httpdata.data.SelfDetailData;
import com.example.myapplication.Bean.Httpdata.data.TimeStampData;
import com.example.myapplication.frontendCustomer.AccountPage.Setting.CustomerSettingPage;
import com.example.myapplication.network.Constant;
import com.example.myapplication.R;
import com.example.myapplication.frontendCustomer.AccountPage.Order.CustomerOrderPage;
import com.example.myapplication.network.CustomerApi;
import com.example.myapplication.network.ProviderApi;
import com.example.myapplication.network.RetrofitClient;

import org.checkerframework.checker.units.qual.C;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class CustomerAccountFragment extends Fragment implements View.OnClickListener {

    private String token;

    ImageButton setting;
    ImageButton order;
    ImageButton wallet;
    ImageButton timetable;

    ImageButton chatBot;
    CircleImageView avatar;
    TextView username;
    TextView address;
    Bitmap bitmap;

    public CustomerAccountFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_account, container, false);

        SharedPreferences sp = getContext().getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        this.token = sp.getString("token", "");

        initialView(rootView);
        getCustomerDetail(this.token);

        return rootView;
    }

    public void onStart() {

        super.onStart();
        getCustomerDetail(token);
    }

    private void initialView(View rootView){
        setting = rootView.findViewById(R.id.setting);
        order = rootView.findViewById(R.id.order);
        timetable =rootView.findViewById(R.id.timetable);
        wallet = rootView.findViewById(R.id.wallet);
        chatBot = rootView.findViewById(R.id.btnChatBot);
        avatar = rootView.findViewById(R.id.accountAvatar);
        username = rootView.findViewById(R.id.accountUsername);
        address = rootView.findViewById(R.id.address);

        setting.setOnClickListener(this);
        order.setOnClickListener(this);
        timetable.setOnClickListener(this);
        wallet.setOnClickListener(this);
        chatBot.setOnClickListener(this);
        avatar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.setting){startActivity(new Intent(getContext(), CustomerSettingPage.class));
        } else if (view.getId() == R.id.order) {
            startActivity(new Intent(getContext(), CustomerOrderPage.class));
        } else if (view.getId() == R.id.wallet) {
            startActivity(new Intent(getContext(), CustomerWalletPage.class));
        } else if (view.getId() == R.id.timetable) {
            startActivity(new Intent(getContext(), CustomerTimetablePage.class));
        }else if (view.getId() == R.id.accountAvatar) {
//            startActivity(new Intent(getContext(), PersonalDetailPage.class));
            openGallery();
        }else if (view.getId() == R.id.btnChatBot) {
            startActivity(new Intent(getContext(), CustomerChatGPTpage.class));
        }
    }

    @SuppressLint("CheckResult")
    private void getCustomerDetail(String token){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.getCustomerDetail(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<SelfDetailData>>() {
                    @Override
                    public void onNext(HttpBaseBean<SelfDetailData> selfDetailDataHttpBaseBean) {
                        if(selfDetailDataHttpBaseBean.getSuccess()){
                            try {
                                updateView(selfDetailDataHttpBaseBean.getData().getUser());
                            }catch (NullPointerException ignored){}
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getContext(),
                                "Network error! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {}
                });
    }

    private void updateView(User user){
        username.setText(user.getUsername());
        address.setText(user.getAddress());
        Glide.with(getContext()).load(Constant.BASE_URL +
                "public/customer/avatar?id=" + user.getId().toString())
                .apply(Constant.avatarOptions)
                .into(avatar);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    bitmap = compressImage(uri);
                } catch (IOException ignored) {}
                Glide.with(this).load(bitmap).into(avatar);
                updateAvatar();
            }
        }
    }

    private Bitmap compressImage(Uri imageUri) throws IOException {
        ContentResolver contentResolver = getContext().getContentResolver();
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
    private void updateAvatar(){
        if(bitmap == null){
            return;
        }
        FileOutputStream fos = null;
        File bitmapFile = null;
        try {
            bitmapFile = File.createTempFile("bitmap", ".jpg", getActivity().getCacheDir());
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