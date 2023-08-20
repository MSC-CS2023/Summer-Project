package com.example.myapplication.frontendCustomer.HomePage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.Bean.AdapterData.ServiceCard;
import com.example.myapplication.Adapter.ServiceCardAdapter;
import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.ServiceShort;
import com.example.myapplication.Bean.Httpdata.data.ServiceShortListData;
import com.example.myapplication.network.Constant;
import com.example.myapplication.R;
import com.example.myapplication.network.CustomerApi;
import com.example.myapplication.network.PublicMethodApi;
import com.example.myapplication.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class CustomerHomePageFragment extends Fragment {
    private static final Integer DEFAULT_RECOMMEND_NUMBER = 10;
    private static final int ALL_TAB = 0;
    private static final int CLEANING_TAB = 1;
    private static final int MAINTAIN_TAB = 2;
    private static final int LAUNDRY_TAB = 3;
    private static final int LANDSCAPE_TAB = 4;

    private Integer currentShowPosition;
    private String token;
    private Integer currentTab;

    List<ServiceCard> dataList = new ArrayList<>();

    List<String> idList = new ArrayList<>();


    List<ImageButton> buttonList = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    ImageButton buttonCleaning;
    ImageButton buttonMaintain;
    ImageButton buttonLaundry;
    ImageButton buttonLandscape;
    ImageButton buttonAll;

    View rootView;
    ServiceCardAdapter serviceCardAdapter;


    public CustomerHomePageFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_customer_home_page, container, false);

        SharedPreferences sp = getContext().getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        this.token = sp.getString("token", "");
        currentShowPosition = 0;
        currentTab = ALL_TAB;

        //5 top icons action here
        topIconAction();

        //set swipe refresh
        swipeDown();

        // Create a demo data list
//        createDemoData();
        updatePage();

        return rootView;
    }

    private void swipeDown() {
        swipeRefreshLayout = rootView.findViewById(R.id.swipeHomePage);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //refresh action
//                createDemoData();

                currentShowPosition = 0;
                updatePage();
                //stop refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void topIconAction() {
        buttonCleaning = rootView.findViewById(R.id.cleaning);
        buttonMaintain = rootView.findViewById(R.id.maintain);
        buttonLaundry = rootView.findViewById(R.id.laundry);
        buttonLandscape = rootView.findViewById(R.id.landscape);
        buttonAll = rootView.findViewById(R.id.others);

        buttonList.add(buttonCleaning);
        buttonList.add(buttonMaintain);
        buttonList.add(buttonLaundry);
        buttonList.add(buttonLandscape);
        buttonList.add(buttonAll);


        for (ImageButton Button : buttonList) {
            Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.getId() == Button.getId()) {
                        if (Button.getId() == buttonCleaning.getId()) {
                            resetButton();
                            buttonCleaning.setImageResource(R.drawable.btn_cleaning_aft);
                            //Update listview with cleaning recommend.
                            currentTab = CLEANING_TAB;
                        } else if (Button.getId() == buttonMaintain.getId()) {
                            resetButton();
                            buttonMaintain.setImageResource(R.drawable.btn_maintain_aft);
                            currentTab = MAINTAIN_TAB;
                        } else if (Button.getId() == buttonLaundry.getId()) {
                            resetButton();
                            buttonLaundry.setImageResource(R.drawable.btn_laundry_aft);
                            currentTab = LAUNDRY_TAB;
                        } else if (Button.getId() == buttonLandscape.getId()) {
                            resetButton();
                            buttonLandscape.setImageResource(R.drawable.btn_landscape_aft);
                            currentTab = LANDSCAPE_TAB;
                        } else if (Button.getId() == buttonAll.getId()) {
                            resetButton();
                            buttonAll.setImageResource(R.drawable.btn_more_aft);
                            currentTab = ALL_TAB;
                        }
                    }

//                    createDemoData();

                    currentShowPosition = 0;
                    updatePage();
                }
            });
        }
    }

    private void createDemoData() {
        dataList = new ArrayList<>();

        ServiceCard serviceCard1 = new ServiceCard("Eric", "100","Repair Air conditioner",
                "available tomorrow", "balabala", "picSrc", 213L, 4.0);
        ServiceCard serviceCard2 = new ServiceCard("Alice", "150","Clean gutter",
                "available today", "balabala", "picSrc", 213L, 4.0);
        ServiceCard serviceCard3 = new ServiceCard("Alice", "140","Clean gutter",
                "available today", "balabala", "picSrc",213L, 4.0);
        ServiceCard serviceCard4 = new ServiceCard("Alice", "120","Clean gutter",
                "available today", "balabala", "picSrc",213L, 4.0);

        dataList.add(serviceCard1);
        dataList.add(serviceCard2);
        dataList.add(serviceCard3);
        dataList.add(serviceCard4);

        updateViewByList(dataList);
    }

    private void resetButton() {
        buttonCleaning.setImageResource(R.drawable.btn_cleaning);
        buttonMaintain.setImageResource(R.drawable.btn_maintain);
        buttonLaundry.setImageResource(R.drawable.btn_laundry);
        buttonLandscape.setImageResource(R.drawable.btn_landscape);
        buttonAll.setImageResource(R.drawable.btn_more);
    }

    //Use adapter data list to update view.
    private void updateViewByList(List<ServiceCard> serviceCards) {
        //RecyclerView down here
        RecyclerView recyclerView = rootView.findViewById(R.id.homepageRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        // Create an Adapter and set it to the ListView
        serviceCardAdapter = new ServiceCardAdapter(serviceCards, getContext());
        serviceCardAdapter.setOnItemClickListener(new ServiceCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(getContext(), CustomerServiceDetailPage.class)
                        .putExtra("serviceId", dataList.get(position).getServiceId()));
            }
        });

        recyclerView.setAdapter(serviceCardAdapter);

        //Load more when the interface reaches the bottom
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
//                    dataList.add(new ServiceCard("Eric", "" + SystemClock.currentThreadTimeMillis(),
//                            "Repair Air conditioner", "available tomorrow",
//                            "balabala", "picSrc", 213L));
                    currentShowPosition += DEFAULT_RECOMMEND_NUMBER;
                    updatePage();
                    serviceCardAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void updatePage(){
        switch (currentTab){
            case ALL_TAB :
                randomlyRecommend(token, currentShowPosition);
                break;
            case CLEANING_TAB:
                getServiceByTag("cleaning", currentShowPosition);
                break;
            case MAINTAIN_TAB:
                getServiceByTag("maintenance", currentShowPosition);
                break;
            case LAUNDRY_TAB:
                getServiceByTag("laundry", currentShowPosition);
                break;
            case LANDSCAPE_TAB:
                getServiceByTag("landscaping", currentShowPosition);
                break;
            default:
                break;
        }
    }

    //Http request and change view after getting response.
    @SuppressLint("CheckResult")
    private void randomlyRecommend(String token, Integer start) {
        if(start == 0){
            idList = new ArrayList<>();
        }
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.randomlyRecommend(token, DEFAULT_RECOMMEND_NUMBER, idList.toArray(new String[0]))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<ServiceShortListData>>() {
                    @Override
                    public void onNext(HttpBaseBean<ServiceShortListData> serviceShortListDataHttpBaseBean) {
                        if (serviceShortListDataHttpBaseBean.getSuccess()) {
                            try{
                                for(ServiceShort serviceShort :
                                        serviceShortListDataHttpBaseBean.getData().getServices()){
                                    idList.add(serviceShort.getId().toString());
                                }
                                if(start == 0){
                                    dataList = getServiceCardList(
                                            serviceShortListDataHttpBaseBean.getData().getServices());
                                    updateViewByList(dataList);
                                }else{
                                    dataList.addAll(getServiceCardList(
                                            serviceShortListDataHttpBaseBean.getData().getServices()));
                                    serviceCardAdapter.notifyDataSetChanged();
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
                    public void onComplete() {}
                });
    }

    @SuppressLint("CheckResult")
    private void getServiceByTag(String tag, Integer start){
        PublicMethodApi publicMethodApi = RetrofitClient.getInstance().getService(PublicMethodApi.class);
        publicMethodApi.getServiceByTag(tag, start, DEFAULT_RECOMMEND_NUMBER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<ServiceShortListData>>() {
                    @Override
                    public void onNext(HttpBaseBean<ServiceShortListData> serviceShortListDataHttpBaseBean) {
                        if(serviceShortListDataHttpBaseBean.getSuccess()){
                            try{
                                if(start == 0){
                                    dataList = getServiceCardList(
                                            serviceShortListDataHttpBaseBean.getData().getServices());
                                    updateViewByList(dataList);
                                }else{
                                    dataList.addAll(getServiceCardList(
                                            serviceShortListDataHttpBaseBean.getData().getServices()));
                                    serviceCardAdapter.notifyDataSetChanged();
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
                    public void onComplete() {}
                });
    }

    //Convert http response data suitable for adapter data.
    private List<ServiceCard> getServiceCardList(List<ServiceShort> serviceShorts) {
        List<ServiceCard> serviceCards = new ArrayList<>();
        ServiceCard serviceCard;
        for (ServiceShort serviceShort : serviceShorts) {
            String avatarLink = Constant.BASE_URL + "public/service_provider/avatar?id=" + serviceShort.getProviderId().toString();
            String pictureLink = Constant.BASE_URL + "get_pic?id=" + serviceShort.getPictureId();
            serviceCard = new ServiceCard(serviceShort.getUsername(), serviceShort.getFee().toString(),
                    serviceShort.getTitle(), avatarLink, serviceShort.getDescription(),
                    pictureLink, serviceShort.getId(),serviceShort.getMark());
            serviceCards.add(serviceCard);
        }
        return serviceCards;
    }
}