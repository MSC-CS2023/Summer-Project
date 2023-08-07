package com.example.myapplication.frontendCustomer.HomePage;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Bean.AdapterData.ServiceCard;
import com.example.myapplication.Adapter.ServiceCardAdapter;
import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.ServiceShort;
import com.example.myapplication.Bean.Httpdata.data.ServiceShortListData;
import com.example.myapplication.Constant;
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

    private RecyclerView recyclerView;

    private static final Integer DEFAULT_RECOMMEND_NUMBER = 5;
    private String token;


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


        //5 top icons action here

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

//        randomlyRecommend(null, rootView);

        // Create a demo data list
        List<ServiceCard> demoDataList = new ArrayList<>();

        ServiceCard serviceCard1 = new ServiceCard("Eric", "Repair Air conditioner", "100", "available tomorrow");
        ServiceCard serviceCard2 = new ServiceCard("Alice", "Clean gutter", "150", "available today");
        ServiceCard serviceCard3 = new ServiceCard("Alice", "Clean gutter", "160", "available today");
        ServiceCard serviceCard4 = new ServiceCard("Alice", "Clean gutter", "150", "available today");
        ServiceCard serviceCard5 = new ServiceCard("Alice", "Clean gutter", "150", "available today");
        ServiceCard serviceCard6 = new ServiceCard("Alice", "Clean gutter", "150", "available today");

        demoDataList.add(serviceCard1);
        demoDataList.add(serviceCard2);
        demoDataList.add(serviceCard3);
        demoDataList.add(serviceCard4);
        demoDataList.add(serviceCard5);
        demoDataList.add(serviceCard6);

        updateViewByList(demoDataList, rootView);


        return rootView;
    }


    private void resetButton() {
        buttonCleaning.setImageResource(R.drawable.btn_cleaning);
        buttonMaintain.setImageResource(R.drawable.btn_maintain);
        buttonLaundry.setImageResource(R.drawable.btn_laundry);
        buttonLandscape.setImageResource(R.drawable.btn_landscape);
        buttonOthers.setImageResource(R.drawable.btn_more);
    }

    //Http request and change view after getting response.
    @SuppressLint("CheckResult")
    private void randomlyRecommend(String recommendType, View view) {
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.randomlyRecommend(this.token, DEFAULT_RECOMMEND_NUMBER, recommendType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<ServiceShortListData>>() {
                    @Override
                    public void onNext(HttpBaseBean<ServiceShortListData> serviceShortListDataHttpBaseBean) {
                        if (serviceShortListDataHttpBaseBean.getSuccess()) {
                            List<ServiceCard> serviceCards = getServiceCardList(
                                    serviceShortListDataHttpBaseBean.getData().getServices());
                            updateViewByList(serviceCards, view);
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
            String Link = Constant.BASE_URL + "public/service_provider/avatar?id=" + serviceShort.getProviderId().toString();
            serviceCard = new ServiceCard(serviceShort.getId().toString(), serviceShort.getFee().toString(),
                    serviceShort.getTitle(), Link);
            serviceCards.add(serviceCard);
        }
        return serviceCards;
    }

    //Use adapter data list to update view.
    private void updateViewByList(List<ServiceCard> serviceCards, View view) {
        //RecyclerView down here
        RecyclerView recyclerView = view.findViewById(R.id.homepageRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        // Create an Adapter and set it to the ListView
        ServiceCardAdapter serviceCardAdapter = new ServiceCardAdapter(serviceCards);
        recyclerView.setAdapter(serviceCardAdapter);
    }

}