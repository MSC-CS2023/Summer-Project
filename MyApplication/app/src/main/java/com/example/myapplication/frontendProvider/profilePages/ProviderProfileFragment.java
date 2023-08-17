package com.example.myapplication.frontendProvider.profilePages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.User;
import com.example.myapplication.Bean.Httpdata.data.SelfDetailData;
import com.example.myapplication.network.Constant;
import com.example.myapplication.R;
import com.example.myapplication.network.ProviderApi;
import com.example.myapplication.network.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

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

    ImageView avatar;
    TextView username;

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
                            updateView(selfDetailDataHttpBaseBean.getData().getUser());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void updateView(User user){
        username.setText(user.getUsername());
        Glide.with(getContext()).load(Constant.BASE_URL +
                "public/service_provider/avatar?id=" + user.getId().toString()).into(avatar);
    }

}