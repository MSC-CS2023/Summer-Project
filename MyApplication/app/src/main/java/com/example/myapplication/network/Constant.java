package com.example.myapplication.network;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;

public class Constant {

//    public static final String BASE_URL = "http://172.23.250.200:8080/";  //zbr
    public static final String BASE_URL = "http://192.168.162.152:8080/"; //wdx

    public static final Integer MAX_IMAGE_SIZE = 2 * 1024 * 1024;

    public static final RequestOptions avatarOptions = new RequestOptions()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.default_avatar);
//            .error(R.drawable.default_avatar_female1)
//            .fallback(R.drawable.default_avatar_female2);

    public static final RequestOptions pictureOptions = new RequestOptions()
            .placeholder(R.drawable.img_sample1);
}
