package com.example.myapplication.frontendProvider.orderPages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.CaseMap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.method.ReplacementTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.Order;
import com.example.myapplication.Bean.Httpdata.data.OrderListData;
import com.example.myapplication.R;
import com.example.myapplication.frontendProvider.homePages.ProviderServicesAdaptor;
import com.example.myapplication.network.ProviderApi;
import com.example.myapplication.network.RetrofitClient;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProviderOrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProviderOrdersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TEXT = "param1";

    // TODO: Rename and change types of parameters
    private String mTitle;

    private View rootView;
    private String token;

    private List<ProviderOrderCardData> data = new ArrayList<>();

    public ProviderOrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ProviderOrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProviderOrdersFragment newInstance(String param1) {
        ProviderOrdersFragment fragment = new ProviderOrdersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TEXT);
        }
        SharedPreferences sp = getContext().getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        this.token = sp.getString("token", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_provider_orders, container, false);
        }
        initView();

        return rootView;
    }

    private void initView() {
        TextView title = rootView.findViewById(R.id.title);
        title.setText(mTitle);

        //Set data and adaptor
        data.add(new ProviderOrderCardData("Cleaning", 382789574L, "100",
                "img_sample2", "Unpaid"));
        data.add(new ProviderOrderCardData("Maintenance", 382723474L, "200",
                "img_sample1", "Unconfirmed"));
        data.add(new ProviderOrderCardData("Laundry", 384389574L, "300",
                "img_sample2", "Processing"));
        setAdaptor();
//        getProviderOrder(this.token);

        //Click on something
        setClick();
    }

    private void setAdaptor() {
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_provider_orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ProviderOrdersAdaptor providerOrdersAdaptor = new ProviderOrdersAdaptor(data, getContext());
        recyclerView.setAdapter(providerOrdersAdaptor);

        //监听每一个item的点击
        providerOrdersAdaptor.setRecyclerItemClickListener(new ProviderServicesAdaptor.OnRecyclerItemClickListener() {
            @Override
            public void onRecyclerItemClick(int position) {
                Intent intentToUnconfirmedOrder = new Intent(getContext(), ProviderOrderDetailUnconfirmedActivity.class);
                Intent intentToProcessingOrder = new Intent(getContext(), ProviderOrderDetailProcessingActivity.class);
                Intent intentToRejectedOrder = new Intent(getContext(), ProviderOrderDetailRejectedActivity.class);
                Intent intentToFinishedOrder = new Intent(getContext(), ProviderOrderDetailFinishedActivity.class);

                //先判断订单是什么状态，然后跳转到对应的activity（区别布局内的按钮）
                startActivity(intentToUnconfirmedOrder);
            }
        });
    }

    private void setClick() {

        //Click on search button to search orders
        ImageButton searchButton =  rootView.findViewById(R.id.btn_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Search services", Toast.LENGTH_SHORT).show();
            }
        });

        //Click on different tabs
        TabLayout tabLayout = rootView.findViewById(R.id.filter_tab);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0: //all
                        Toast.makeText(getContext(), "All clicked", Toast.LENGTH_SHORT).show();
//                        getProviderOrder(token);
                        break;
                    case 1:
                        Toast.makeText(getContext(), "Unconfirmed clicked", Toast.LENGTH_SHORT).show();
//                        getProviderOrderByType(token, "is_confirmed");
                        break;
                    case 2:
                        Toast.makeText(getContext(), "Processing clicked", Toast.LENGTH_SHORT).show();
//                        getProviderOrderByType(token, "is_finished");
                        break;
                    case 3:
                        Toast.makeText(getContext(), "Rejected clicked", Toast.LENGTH_SHORT).show();
//                        getProviderOrderByType(token, "is_canceled");
                        break;
                    case 4:
                        Toast.makeText(getContext(), "Finished clicked", Toast.LENGTH_SHORT).show();
//                        getProviderOrderByType(token, "is_rejected");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @SuppressLint("CheckResult")
    private void getProviderOrder(String token){
        ProviderApi providerApi = RetrofitClient.getInstance().getService(ProviderApi.class);
        providerApi.getProviderOrders(token, 0, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<OrderListData>>() {
                    @Override
                    public void onNext(HttpBaseBean<OrderListData> orderListDataHttpBaseBean) {
                        if(orderListDataHttpBaseBean.getSuccess()){
                            data = getOrderList(orderListDataHttpBaseBean.getData().getBookingOrders());
                            setAdaptor();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getContext(),
                                "Network error! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("CheckResult")
    private void getProviderOrderByType(String token, String keyword){
        ProviderApi providerApi = RetrofitClient.getInstance().getService(ProviderApi.class);
        providerApi.searchProviderOrder(token, keyword,null, 0, 10, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<OrderListData>>() {
                    @Override
                    public void onNext(HttpBaseBean<OrderListData> orderListDataHttpBaseBean) {
                        if(orderListDataHttpBaseBean.getSuccess()){
                            data = getOrderList(orderListDataHttpBaseBean.getData().getBookingOrders());
                            setAdaptor();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getContext(),
                                "Network error! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private List<ProviderOrderCardData> getOrderList(List<Order> orders){
        List<ProviderOrderCardData> providerOderCardList = new ArrayList<>();
        String state;
        ProviderOrderCardData providerOrderCardData;
        for(Order order : orders){
            if(order.getIsCanceled()){
                state = "Canceled";
            }else if(order.getIsConfirmed()){
                state = "Confirmed";
            }else if(order.getIsFinished()){
                state = "Finished";
            }else if(order.getIsRejected()){
                state = "Rejected";
            }else{
                state = "null";
            }
            providerOrderCardData = new ProviderOrderCardData(
                    "Title to add", order.getId(),
                    "Price to add", "img_sample2", state);
            providerOderCardList.add(providerOrderCardData);
        }
        return providerOderCardList;
    }
}