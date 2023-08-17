package com.example.myapplication.frontendCustomer.CollectionPage;

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
import android.widget.Toast;

import com.example.myapplication.Adapter.ServiceCardAdapter;
import com.example.myapplication.Bean.AdapterData.ServiceCard;
import com.example.myapplication.Bean.Httpdata.Favourite;
import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.ServiceShort;
import com.example.myapplication.Bean.Httpdata.data.FavouriteListData;
import com.example.myapplication.network.Constant;
import com.example.myapplication.R;
import com.example.myapplication.frontendCustomer.HomePage.CustomerServiceDetailPage;
import com.example.myapplication.network.CustomerApi;
import com.example.myapplication.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;


public class CustomerCollectionFragment extends Fragment {
    String token;
    Integer currentShowPosition;
    static final Integer DEFAULT_SHOW_NUMBER = 5;
    List<ServiceCard> dataList;

    SwipeRefreshLayout swipeRefreshLayout;


    public CustomerCollectionFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_customer_collection, container, false);

        SharedPreferences sp = getContext().getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        this.token = sp.getString("token", "");
        currentShowPosition = 0;

        createDemoData(rootView);
//        getCustomerFavourites(this.token, rootView, currentShowPosition, DEFAULT_SHOW_NUMBER);

        swipeDown(rootView);

        return rootView;
    }


    private void swipeDown(View rootView) {
        swipeRefreshLayout = rootView.findViewById(R.id.swipeCollectionPage);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                createDemoData(rootView);
                Toast.makeText(getContext(), "refresh action", Toast.LENGTH_SHORT).show();
//                currentShowPosition = 0;
//                getCustomerFavourites(token, rootView, currentShowPosition, DEFAULT_SHOW_NUMBER);
                //stop refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void createDemoData(View rootView) {
        List<ServiceCard> demoDataList = new ArrayList<>();
        ServiceCard serviceCard1 = new ServiceCard("Eric", "100","Repair Air conditioner",  "available tomorrow",
                "balabala", "picSrc",213L);
        ServiceCard serviceCard2 = new ServiceCard("Alice", "150","Clean gutter",  "available today",
                "balabala", "picSrc",213L);
        ServiceCard serviceCard3 = new ServiceCard("Alice", "150","Clean gutter",  "available today",
                "balabala", "picSrc",213L);
        ServiceCard serviceCard4 = new ServiceCard("Alice", "150","Clean gutter", "available today",
                "balabala", "picSrc",213L);

        demoDataList.add(serviceCard1);
        demoDataList.add(serviceCard2);
        demoDataList.add(serviceCard3);
        demoDataList.add(serviceCard4);

        dataList = demoDataList;

        updateViewByList(dataList, rootView);

    }

    @SuppressLint("CheckResult")
    private void getCustomerFavourites(String token, View view, Integer start, Integer number){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.getCustomerFavourites(token, start, number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<FavouriteListData>>() {
                    @Override
                    public void onNext(HttpBaseBean<FavouriteListData> favouriteListDataHttpBaseBean) {
                        if(favouriteListDataHttpBaseBean.getSuccess()){
                            if(start == 0){
                                dataList = getServiceCardList(
                                        favouriteListDataHttpBaseBean.getData().getFavourites());
                                updateViewByList(dataList, view);
                            }else{
                                dataList.addAll(getServiceCardList(
                                        favouriteListDataHttpBaseBean.getData().getFavourites()));
                            }
                        }else{
                            //test
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

    private List<ServiceCard> getServiceCardList(List<Favourite> favourites) {
        List<ServiceCard> serviceCards = new ArrayList<>();
        ServiceCard serviceCard;
        ServiceShort serviceShort;
        for (Favourite favourite : favourites) {
            serviceShort = favourite.getServiceShort();
            String avatarLink = Constant.BASE_URL + "public/service_provider/avatar?id=" + serviceShort.getProviderId().toString();
            String pictureLink = Constant.BASE_URL + "get_pic?id=" + serviceShort.getPictureId();
            serviceCard = new ServiceCard(serviceShort.getUsername(), serviceShort.getFee().toString(),
                    serviceShort.getTitle(), avatarLink, serviceShort.getDescription(),
                    pictureLink, serviceShort.getId());
            serviceCards.add(serviceCard);
        }
        return serviceCards;
    }

    //Use adapter data list to update view.
    private void updateViewByList(List<ServiceCard> serviceCards, View view) {
        //RecyclerView down here
        RecyclerView recyclerView = view.findViewById(R.id.collectionRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        // Create an Adapter and set it to the ListView
        ServiceCardAdapter serviceCardAdapter = new ServiceCardAdapter(serviceCards, getContext());

        serviceCardAdapter.setOnItemClickListener(new ServiceCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(getContext(), CustomerServiceDetailPage.class)
                        .putExtra("serviceId", dataList.get(position).getServiceId()));
                Toast.makeText(getContext(), "click" + position, Toast.LENGTH_SHORT).show();
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
                    dataList.add(new ServiceCard("Eric", "" + SystemClock.currentThreadTimeMillis(),
                            "Repair Air conditioner", "available tomorrow",
                            "balabala", "picSrc", 213L));
                    Toast.makeText(getContext(), "load more", Toast.LENGTH_SHORT).show();
//                    currentShowPosition += DEFAULT_SHOW_NUMBER;
//                    getCustomerFavourites(token, view, currentShowPosition, DEFAULT_SHOW_NUMBER);
                    serviceCardAdapter.notifyDataSetChanged();
                }
            }
        });

    }

}