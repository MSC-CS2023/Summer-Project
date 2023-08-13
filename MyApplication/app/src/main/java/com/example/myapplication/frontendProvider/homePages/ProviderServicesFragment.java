package com.example.myapplication.frontendProvider.homePages;


import android.content.Intent;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.ServiceShort;
import com.example.myapplication.Bean.Httpdata.data.ServiceShortListData;
import com.example.myapplication.R;
import com.example.myapplication.frontendProvider.loginPages.ProviderLogin;
import com.example.myapplication.network.ProviderApi;
import com.example.myapplication.network.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProviderServicesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProviderServicesFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TEXT = "param1";

    // TODO: Rename and change types of parameters
    private String mText;
    private View rootView;
    private String token;

    private List<ProviderServiceCardData> data = new ArrayList<>();

    public ProviderServicesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProviderServicesFragment newInstance(String param1) {
        ProviderServicesFragment fragment = new ProviderServicesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mText = getArguments().getString(ARG_TEXT);
        }
        SharedPreferences sp = getContext().getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        this.token = sp.getString("token", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//         Inflate the layout for this fragment
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_provider_services, container, false);
        }
        initView();
        return rootView;
    }

    private void initView() {
        TextView title = rootView.findViewById(R.id.title);
        title.setText(mText);

        //Set data and adaptor and item click event
        data.add(new ProviderServiceCardData("Cleaning", "Some short description...",
                "100", "img_sample1"));
        data.add(new ProviderServiceCardData("Maintenance", "Some short description.....",
                "200", "img_sample2"));
        data.add(new ProviderServiceCardData("Laundry", "Some short description......",
                "300", "img_sample1"));
        setAdapter();
//        getProviderService(this.token);

        //Click on something
        setClick();

    }


    private void setAdapter() {
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_provider_services);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ProviderServicesAdaptor providerServicesAdaptor = new ProviderServicesAdaptor(data, getContext());
        recyclerView.setAdapter(providerServicesAdaptor);

        providerServicesAdaptor.setRecyclerItemClickListener(new ProviderServicesAdaptor.OnRecyclerItemClickListener() {
            @Override
            public void onRecyclerItemClick(int position) {
                Toast.makeText(getContext(),"Item" + position + "clicked.", Toast.LENGTH_SHORT).show();

                //在此处从fragment传数据给详情的activity
                Intent intentToDetail = new Intent(getContext(), ProviderServiceDetailActivity.class);
                startActivity(intentToDetail);
            }
        });
    }


    private void setClick() {

        //Click on fab to add new service
        FloatingActionButton fab = rootView.findViewById(R.id.fab_new_service);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Create new service", Toast.LENGTH_SHORT).show();
                Intent intentAddService = new Intent(getContext(), ProviderCreateServiceActivity.class);
                startActivity(intentAddService);
            }
        });

        //Click on search button to search services
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
                    case 0:
                        Toast.makeText(getContext(), "All clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getContext(), "Cleaning clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getContext(), "Maintenance clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getContext(), "Laundry clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(getContext(), "Landscaping clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(getContext(), "Others clicked", Toast.LENGTH_SHORT).show();
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
    private void getProviderService(String token){
        ProviderApi providerApi = RetrofitClient.getInstance().getService(ProviderApi.class);
        providerApi.getProviderServices(token, 0, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<ServiceShortListData>>() {
                    @Override
                    public void onNext(HttpBaseBean<ServiceShortListData> serviceShortListDataHttpBaseBean) {
                        if(serviceShortListDataHttpBaseBean.getSuccess()){
                            data = getServiceCards(serviceShortListDataHttpBaseBean.getData().getServices());
                            setAdapter();
                        }else{

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

    private List<ProviderServiceCardData> getServiceCards(List<ServiceShort> serviceShorts){
        List<ProviderServiceCardData> providerServiceCardDataList = new ArrayList<>();
        ProviderServiceCardData providerServiceCardData;
        for(ServiceShort serviceShort : serviceShorts){
            providerServiceCardData = new ProviderServiceCardData(
                    serviceShort.getTitle(), serviceShort.getDescription(),
                    serviceShort.getFee().toString(), "img_sample1");
            providerServiceCardDataList.add(providerServiceCardData);
        }
        return providerServiceCardDataList;
    }

}