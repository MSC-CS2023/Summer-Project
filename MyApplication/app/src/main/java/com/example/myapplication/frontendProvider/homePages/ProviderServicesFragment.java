package com.example.myapplication.frontendProvider.homePages;


import android.content.Intent;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.ServiceShort;
import com.example.myapplication.Bean.Httpdata.data.ServiceShortListData;
import com.example.myapplication.network.Constant;
import com.example.myapplication.R;
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
    private List<ProviderServiceCardData> data = new ArrayList<>();
    private String token;
    Integer currentShowPosition;
    static final Integer DEFAULT_SHOW_NUMBER = 5;

    // TODO: Rename and change types of parameters
    private String mText;
    private View rootView;
    SwipeRefreshLayout swipeRefreshLayout;

    ProviderServicesAdaptor providerServicesAdaptor;

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
        currentShowPosition = 0;

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
        //Click on something
        setClick();

        createDemoData();

        getProviderService(token, currentShowPosition, DEFAULT_SHOW_NUMBER);
        swipeDown();

    }

    private void createDemoData() {
        data = new ArrayList<>();
        data.add(new ProviderServiceCardData("Cleaning", "Some short description...",
                "1020", "img_sample1",123L));
        data.add(new ProviderServiceCardData("Maintenance", "Some short description.....",
                "200", "img_sample2",123L));
        data.add(new ProviderServiceCardData("Laundry", "Some short description......",
                "300", "img_sample1",123L));
        setAdapter();
    }

    private void swipeDown() {
        swipeRefreshLayout = rootView.findViewById(R.id.swipeCollectionPage);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                createDemoData();
                Toast.makeText(getContext(), "refresh action", Toast.LENGTH_SHORT).show();
                currentShowPosition = 0;
                getProviderService(token, currentShowPosition, DEFAULT_SHOW_NUMBER);
                //stop refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void setAdapter() {
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_provider_services);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        providerServicesAdaptor = new ProviderServicesAdaptor(data, getContext());
        recyclerView.setAdapter(providerServicesAdaptor);

        providerServicesAdaptor.setRecyclerItemClickListener(new ProviderServicesAdaptor.OnRecyclerItemClickListener() {
            @Override
            public void onRecyclerItemClick(int position) {
                startActivity(new Intent(getContext(), ProviderServiceDetailActivity.class)
                        .putExtra("serviceId", data.get(position).getServiceId()));
                Toast.makeText(getContext(), "click" + position, Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                // Determine whether to slide to the bottom and perform loading more operations
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
//                    data.add(new ProviderServiceCardData("Laundry", "Some short description......",
//                            "300" + SystemClock.currentThreadTimeMillis(), "img_sample1", 123L));
//                    Toast.makeText(getContext(), "load more", Toast.LENGTH_SHORT).show();
                    currentShowPosition += DEFAULT_SHOW_NUMBER;
                    getProviderService(token, currentShowPosition, DEFAULT_SHOW_NUMBER);
                    providerServicesAdaptor.notifyDataSetChanged();
                }
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
    private void getProviderService(String token, Integer start, Integer number){
        ProviderApi providerApi = RetrofitClient.getInstance().getService(ProviderApi.class);
        providerApi.getProviderServices(token, start, number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<ServiceShortListData>>() {
                    @Override
                    public void onNext(HttpBaseBean<ServiceShortListData> serviceShortListDataHttpBaseBean) {
                        if(serviceShortListDataHttpBaseBean.getSuccess()){
                            try{
                                if(start == 0){
                                    data = getServiceCards(serviceShortListDataHttpBaseBean.getData().getServices());
                                    setAdapter();
                                }else{
                                    data.addAll(getServiceCards(serviceShortListDataHttpBaseBean.getData().getServices()));
                                    providerServicesAdaptor.notifyDataSetChanged();
                                }
                            }catch (NullPointerException ignored){}
                        }else{}
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
            String pictureLink = Constant.BASE_URL + "get_pic?id=" + serviceShort.getPictureId();
            providerServiceCardData = new ProviderServiceCardData(
                    serviceShort.getTitle(), serviceShort.getDescription(),
                    serviceShort.getFee().toString(), pictureLink, serviceShort.getId());
            providerServiceCardDataList.add(providerServiceCardData);
        }
        return providerServiceCardDataList;
    }

}