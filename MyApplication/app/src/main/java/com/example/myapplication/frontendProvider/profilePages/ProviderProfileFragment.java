package com.example.myapplication.frontendProvider.profilePages;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.User;
import com.example.myapplication.Bean.Httpdata.data.SelfDetailData;
import com.example.myapplication.Bean.Httpdata.data.TimeStampData;
import com.example.myapplication.network.Constant;
import com.example.myapplication.R;
import com.example.myapplication.network.ProviderApi;
import com.example.myapplication.network.RetrofitClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProviderProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class ProviderProfileFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TITLE = "param1";
    private String token;
    // TODO: Rename and change types of parameters
    private String mTitle;
    private View rootView;
    private ImageButton wallet;
    private ImageButton timetable;
    private ImageButton map;
    private ImageButton setting;

    CircleImageView avatar;

    TextView username;
    Bitmap bitmap;

    public ProviderProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ProviderProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProviderProfileFragment newInstance(String param1) {
        ProviderProfileFragment fragment = new ProviderProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_provider_profile, container, false);
        }
        SharedPreferences sp = getContext().getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        this.token = sp.getString("token", "");


        initView();
        getProviderDetail(token);
        return rootView;
    }

    public void onStart() {

        super.onStart();
        getProviderDetail(token);
    }

    private void initView() {
        TextView title = rootView.findViewById(R.id.title);
        title.setText(mTitle);

        wallet = rootView.findViewById(R.id.btn_wallet);
        wallet.setOnClickListener(this);
        timetable = rootView.findViewById(R.id.btn_timetable);
        timetable.setOnClickListener(this);
        map = rootView.findViewById(R.id.btn_map);
        map.setOnClickListener(this);
        setting = rootView.findViewById(R.id.btn_setting);
        setting.setOnClickListener(this);

        username = rootView.findViewById(R.id.txt_username);
        avatar = rootView.findViewById(R.id.img_avatar);
        avatar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_wallet) {
            Intent intentToWallet = new Intent(getContext(), ProviderWalletActivity.class);
            startActivity(intentToWallet);
        } else if(view.getId() == R.id.btn_timetable) {
            Intent intentToTimetable = new Intent(getContext(), ProviderTimetableActivity.class);
            startActivity(intentToTimetable);
        } else if(view.getId() == R.id.btn_map) {
            Intent intentToMap = new Intent(getContext(), ProviderMapActivity.class);
            startActivity(intentToMap);
        } else if(view.getId() == R.id.btn_setting) {
            Intent intentToSetting = new Intent(getContext(), ProviderSettingActivity.class);
            startActivity(intentToSetting);
        }else if(view.getId() == R.id.img_avatar){
            openGallery();
        }
    }

    @SuppressLint("CheckResult")
    private void getProviderDetail(String token){
        ProviderApi providerApi= RetrofitClient.getInstance().getService(ProviderApi.class);
        providerApi.getProviderDetail(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<SelfDetailData>>() {
                    @Override
                    public void onNext(HttpBaseBean<SelfDetailData> selfDetailDataHttpBaseBean) {
                        if(selfDetailDataHttpBaseBean.getSuccess()){
                            try {
                                updateView(selfDetailDataHttpBaseBean.getData().getUser());
                            }catch (NullPointerException ignored){
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onComplete() {}
                });
    }

    private void updateView(User user){
        username.setText(user.getUsername());
        Glide.with(getContext()).load(Constant.BASE_URL +
                "public/service_provider/avatar?id=" + user.getId().toString())
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
            // 在这里，您可以将 bitmapFile 上传到服务器或云存储
            // 这通常需要使用网络请求库执行上传操作
            MultipartBody.Part part = MultipartBody.Part.createFormData("avatar", "Avatar.jpg",
                    RequestBody.create(MediaType.parse("application/octet-stream"), bitmapFile));
            ProviderApi providerApi = RetrofitClient.getInstance().getService(ProviderApi.class);
            providerApi.updateProviderAvatar(token, part)
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