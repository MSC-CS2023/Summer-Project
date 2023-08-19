package com.example.myapplication.network;

import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;

public class Constant {
    public static final String BASE_URL = "http://172.28.89.62:8080/";
//    public static final String BASE_URL = "http://192.168.162.152:8080/";
    public static final Integer MAX_IMAGE_SIZE = 2 * 1024 * 1024;

    public static final RequestOptions avatarOptions = new RequestOptions()
            .placeholder(R.drawable.default_avatar)
            .override(64,64);

    public static final RequestOptions pictureOptions = new RequestOptions()
            .placeholder(R.drawable.img_sample1);
}
