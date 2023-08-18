package com.example.myapplication.frontendProvider.orderPages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.example.myapplication.Bean.Httpdata.Order;
import com.example.myapplication.Bean.Httpdata.data.OrderListData;
import com.example.myapplication.network.Constant;
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
    private static final Integer DEFAULT_SHOW_NUMBER = 5;
    private static final int ALL_TAB = 1;
    private static final int UNCONFIRMED_TAB = 2;
    private static final int PROCESSING_TAB = 3;
    private static final int FINISHED_TAB = 4;
    private static final int REJECTED_TAB = 5;
    private static final int CANCELED_TAB = 6;

    // TODO: Rename and change types of parameters
    private String mTitle;
    private String token;
    private int currentTab;
    private Integer currentShowPosition;
    private List<ProviderOrderCardData> data = new ArrayList<>();

    private View rootView;
    SwipeRefreshLayout swipeRefreshLayout;

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
        currentShowPosition = 0;
        currentTab = ALL_TAB;

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
        createDemoData();

        updateOrderData(token, currentShowPosition, DEFAULT_SHOW_NUMBER);
        swipeDown();
        //Click on something
        setClick();
    }

    private void createDemoData(){
        data = new ArrayList<>();
        data.add(new ProviderOrderCardData("Cleaning", 382789574L, "100",
                "img_sample2", "Unconfirmed", ""));
        data.add(new ProviderOrderCardData("Maintenance", 382723474L, "200",
                "img_sample1", "Finished", ""));
        data.add(new ProviderOrderCardData("Laundry", 384389574L, "300",
                "img_sample2", "Processing", ""));
        data.add(new ProviderOrderCardData("Laundry", 384389574L, "300",
                "img_sample2", "Rejected", ""));
        setAdaptor();
    }


    private void swipeDown() {
        swipeRefreshLayout = rootView.findViewById(R.id.swipeCollectionPage);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                createDemoData();
                Toast.makeText(getContext(), "refresh action", Toast.LENGTH_SHORT).show();
                currentShowPosition = 0;
                updateOrderData(token, currentShowPosition, DEFAULT_SHOW_NUMBER);
                //stop refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        });
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
                switch (data.get(position).getState().toLowerCase()){
                    case "unconfirmed":
                        startActivity(new Intent(getContext(), ProviderOrderDetailUnconfirmedActivity.class)
                                .putExtra("orderId", data.get(position).getOrderNum()));
                        break;
                    case "processing":
                        startActivity(new Intent(getContext(), ProviderOrderDetailProcessingActivity.class)
                                .putExtra("orderId", data.get(position).getOrderNum()));
                        break;
                    case "finished":
                        startActivity(new Intent(getContext(), ProviderOrderDetailFinishedActivity.class)
                                .putExtra("orderId", data.get(position).getOrderNum()));
                        break;
                    case "rejected":
                        startActivity(new Intent(getContext(), ProviderOrderDetailRejectedActivity.class)
                                .putExtra("orderId", data.get(position).getOrderNum()));
                        break;
                    case "canceled":
                        startActivity(new Intent(getContext(), ProviderOrderDetailCancelledActivity.class)
                                .putExtra("orderId", data.get(position).getOrderNum()));
                    default:
                        break;
                }
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
                    data.add(new ProviderOrderCardData("Cleaning", SystemClock.currentThreadTimeMillis(),
                            "300", "img_sample2", "Finished", ""));
                    Toast.makeText(getContext(), "load more", Toast.LENGTH_SHORT).show();
                    currentShowPosition += DEFAULT_SHOW_NUMBER;
                    updateOrderData(token, currentShowPosition, DEFAULT_SHOW_NUMBER);
                    providerOrdersAdaptor.notifyDataSetChanged();
                }
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
                    case 0:
                        currentTab = ALL_TAB;
                        Toast.makeText(getContext(), "All clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        currentTab = UNCONFIRMED_TAB;
                        Toast.makeText(getContext(), "Unconfirmed clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        currentTab = PROCESSING_TAB;
                        Toast.makeText(getContext(), "Processing clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        currentTab = FINISHED_TAB;
                        Toast.makeText(getContext(), "Finished clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        currentTab = REJECTED_TAB;
                        Toast.makeText(getContext(), "Rejected clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        currentTab = CANCELED_TAB;
                        Toast.makeText(getContext(), "Cancelled clicked", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                createDemoData();
                currentShowPosition = 0;
                updateOrderData(token, currentShowPosition, DEFAULT_SHOW_NUMBER);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void updateOrderData(String token, Integer start, Integer number){
        switch (currentTab){
            case ALL_TAB:
                getProviderOrder(token, start, number);
                break;
            case UNCONFIRMED_TAB:
                getProviderOrderByType(token, "is_confirmed", start, number, true, true);
                break;
            case PROCESSING_TAB:
                getProviderOrderByType(token, "is_finished", start, number, true, true);
                break;
            case FINISHED_TAB:
                getProviderOrderByType(token, "is_finished", start, number, true, false);
                break;
            case REJECTED_TAB:
                getProviderOrderByType(token, "is_rejected", start, number, true, false);
                break;
            case CANCELED_TAB:
            default:
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void getProviderOrder(String token, Integer start, Integer number){
        ProviderApi providerApi = RetrofitClient.getInstance().getService(ProviderApi.class);
        providerApi.getProviderOrders(token, start, number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<OrderListData>>() {
                    @Override
                    public void onNext(HttpBaseBean<OrderListData> orderListDataHttpBaseBean) {
                        if(orderListDataHttpBaseBean.getSuccess()){
                            try{
                                if(start == 0){
                                    data = getOrderList(orderListDataHttpBaseBean.getData().getBookingOrders());
                                    setAdaptor();
                                }else {
                                    data.addAll(getOrderList(orderListDataHttpBaseBean.getData().getBookingOrders()));
                                }
                            }catch (NullPointerException ignored){}
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
    private void getProviderOrderByType(String token, String keyword, Integer start,
                                        Integer number, Boolean isOr, Boolean isNot){
        ProviderApi providerApi = RetrofitClient.getInstance().getService(ProviderApi.class);
        providerApi.searchProviderOrder(token, keyword,isOr, start, number, isNot)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<OrderListData>>() {
                    @Override
                    public void onNext(HttpBaseBean<OrderListData> orderListDataHttpBaseBean) {
                        if(orderListDataHttpBaseBean.getSuccess()){
                            if(start == 0){
                                data = getOrderList(orderListDataHttpBaseBean.getData().getBookingOrders());
                                setAdaptor();
                            }else {
                                data.addAll(getOrderList(orderListDataHttpBaseBean.getData().getBookingOrders()));
                            }
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
        ProviderOrderCardData providerOrderCardData;
        for(Order order : orders){
            String link = Constant.BASE_URL + "get_pic?id=" + order.getServiceShort().getPictureId();
            providerOrderCardData = new ProviderOrderCardData(
                    order.getServiceShort().getTitle(), order.getId(),
                    order.getServiceShort().getFee().toString(), "img_sample2", order.getState(), link);
            providerOderCardList.add(providerOrderCardData);
        }
        return providerOderCardList;
    }

}