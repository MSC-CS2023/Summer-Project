package com.example.myapplication.frontendCustomer.HomePage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import android.widget.Toast;

import com.example.myapplication.Bean.AdapterData.ServiceCard;
import com.example.myapplication.Adapter.ServiceCardAdapter;
import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.ServiceShort;
import com.example.myapplication.Bean.Httpdata.data.ServiceShortListData;
import com.example.myapplication.network.Constant;
import com.example.myapplication.R;
import com.example.myapplication.network.CustomerApi;
import com.example.myapplication.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class CustomerHomePageFragment extends Fragment {


    private static final Integer DEFAULT_RECOMMEND_NUMBER = 6;
    private static final Integer ALL_TAB = 0;
    private static final Integer CLEANING_TAB = 1;
    private static final Integer MAINTAIN_TAB = 2;
    private static final Integer LAUNDRY_TAB = 3;
    private static final Integer LANDSCAPE_TAB = 4;
    private String token;
    private Integer currentTab;

    List<ServiceCard> dataList;

    SwipeRefreshLayout swipeRefreshLayout;


    List<ImageButton> buttonList = new ArrayList<>();
    ImageButton buttonCleaning;
    ImageButton buttonMaintain;
    ImageButton buttonLaundry;
    ImageButton buttonLandscape;
    ImageButton buttonOthers;


    public CustomerHomePageFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_customer_home_page, container, false);
        SharedPreferences sp = getContext().getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        this.token = sp.getString("token", "");
        currentTab = ALL_TAB;

        //5 top icons action here
        topIconAction(rootView);

        // Create a demo data list
        createDemoDate(rootView);
//        randomlyRecommend(this.token, rootView, false);


        //set swipe refresh
        swipeDown(rootView);

        return rootView;
    }

    private void swipeDown(View rootView) {
        swipeRefreshLayout = rootView.findViewById(R.id.swipeHomePage);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //add refresh action here
                createDemoDate(rootView);
                Toast.makeText(getContext(), "refresh action", Toast.LENGTH_SHORT).show();
//                randomlyRecommend(token, rootView, false);
                //stop refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void topIconAction(View rootView) {
        buttonCleaning = rootView.findViewById(R.id.cleaning);
        buttonMaintain = rootView.findViewById(R.id.maintain);
        buttonLaundry = rootView.findViewById(R.id.laundry);
        buttonLandscape = rootView.findViewById(R.id.landscape);
        buttonOthers = rootView.findViewById(R.id.others);

        buttonList.add(buttonCleaning);
        buttonList.add(buttonMaintain);
        buttonList.add(buttonLaundry);
        buttonList.add(buttonLandscape);
        buttonList.add(buttonOthers);


        for (ImageButton Button : buttonList) {
            Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.getId() == Button.getId()) {
                        if (Button.getId() == buttonCleaning.getId()) {
                            resetButton();
                            buttonCleaning.setImageResource(R.drawable.btn_cleaning_aft);
                            //Update listview with cleaning recommend.
//                            randomlyRecommend("cleaning", rootView);
                        } else if (Button.getId() == buttonMaintain.getId()) {
                            resetButton();
                            buttonMaintain.setImageResource(R.drawable.btn_maintain_aft);
//                            randomlyRecommend("maintain", rootView);
                        } else if (Button.getId() == buttonLaundry.getId()) {
                            resetButton();
                            buttonLaundry.setImageResource(R.drawable.btn_laundry_aft);
//                            randomlyRecommend("laundry", rootView);
                        } else if (Button.getId() == buttonLandscape.getId()) {
                            resetButton();
                            buttonLandscape.setImageResource(R.drawable.btn_landscape_aft);
//                            randomlyRecommend("landscape", rootView);
                        } else if (Button.getId() == buttonOthers.getId()) {
                            resetButton();
                            buttonOthers.setImageResource(R.drawable.btn_more_aft);
                        }
                    }
                }
            });
        }
    }

    private void createDemoDate(View rootView) {
        List<ServiceCard> serviceCards = new ArrayList<>();
        dataList = serviceCards;

        ServiceCard serviceCard1 = new ServiceCard("Eric", "100","Repair Air conditioner",
                "available tomorrow", "balabala", "picSrc", 213L);
        ServiceCard serviceCard2 = new ServiceCard("Alice", "150","Clean gutter",
                "available today", "balabala", "picSrc", 213L);
        ServiceCard serviceCard3 = new ServiceCard("Alice", "150","Clean gutter",
                "available today", "balabala", "picSrc",213L);
        ServiceCard serviceCard4 = new ServiceCard("Alice", "150","Clean gutter",
                "available today", "balabala", "picSrc",213L);

        dataList.add(serviceCard1);
        dataList.add(serviceCard2);
        dataList.add(serviceCard3);
        dataList.add(serviceCard4);

        updateViewByList(dataList, rootView);
    }

    private void resetButton() {
        buttonCleaning.setImageResource(R.drawable.btn_cleaning);
        buttonMaintain.setImageResource(R.drawable.btn_maintain);
        buttonLaundry.setImageResource(R.drawable.btn_laundry);
        buttonLandscape.setImageResource(R.drawable.btn_landscape);
        buttonOthers.setImageResource(R.drawable.btn_more);
    }

    private void updateViewByList(List<ServiceCard> serviceCards, View view) {
        //RecyclerView down here
        RecyclerView recyclerView = view.findViewById(R.id.homepageRecyclerView);
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
//                    randomlyRecommend(token, view, true);
                    serviceCardAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    //Http request and change view after getting response.
    @SuppressLint("CheckResult")
    private void randomlyRecommend(String token, View view, Boolean isAdd) {
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.randomlyRecommend(token, DEFAULT_RECOMMEND_NUMBER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<ServiceShortListData>>() {
                    @Override
                    public void onNext(HttpBaseBean<ServiceShortListData> serviceShortListDataHttpBaseBean) {
                        if (serviceShortListDataHttpBaseBean.getSuccess()) {
                            if(isAdd){
                                dataList.addAll(getServiceCardList(
                                        serviceShortListDataHttpBaseBean.getData().getServices()));
                            }else{
                                dataList = getServiceCardList(
                                        serviceShortListDataHttpBaseBean.getData().getServices());
                                updateViewByList(dataList, view);
                            }
                        } else {
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

    //Convert http response data suitable for adapter data.
    private List<ServiceCard> getServiceCardList(List<ServiceShort> serviceShorts) {
        List<ServiceCard> serviceCards = new ArrayList<>();
        ServiceCard serviceCard;
        for (ServiceShort serviceShort : serviceShorts) {
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

    private Bitmap getRoundedBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int radius = Math.min(width, height) / 2;

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        android.graphics.Canvas canvas = new android.graphics.Canvas(output);

        android.graphics.Path path = new android.graphics.Path();
        path.addCircle(width / 2, height / 2, radius, android.graphics.Path.Direction.CW);

        canvas.clipPath(path);
        canvas.drawBitmap(bitmap, 0, 0, null);

        return output;
    }
}