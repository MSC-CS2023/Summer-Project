package com.example.myapplication.frontendCustomer.AccountPage;

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
import com.example.myapplication.frontendCustomer.AccountPage.Order.CustomerOrderPage;
import com.example.myapplication.frontendCustomer.AccountPage.Setting.CustomerSettingPage;
import com.example.myapplication.network.CustomerApi;
import com.example.myapplication.network.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;


public class CustomerAccountFragment extends Fragment implements View.OnClickListener {

    private String token;

    ImageButton setting;
    ImageButton order;
    ImageButton wallet;
    ImageButton timetable;
    ImageView avatar;
    TextView username;

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

    private void initialView(View rootView){
        setting = rootView.findViewById(R.id.setting);
        order = rootView.findViewById(R.id.order);
        timetable =rootView.findViewById(R.id.timetable);
        wallet = rootView.findViewById(R.id.wallet);
        avatar = rootView.findViewById(R.id.accountAvatar);
        username = rootView.findViewById(R.id.accountUsername);

        setting.setOnClickListener(this);
        order.setOnClickListener(this);
        timetable.setOnClickListener(this);
        wallet.setOnClickListener(this);
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
            startActivity(new Intent(getContext(), PersonalDetailPage.class));
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
                            }catch (Exception ignored){}
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
                "public/customer/avatar?id=" + user.getId().toString()).into(avatar);
    }
}