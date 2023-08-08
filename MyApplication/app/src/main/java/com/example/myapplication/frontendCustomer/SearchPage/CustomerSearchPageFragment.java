package com.example.myapplication.frontendCustomer.SearchPage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.Adapter.ServiceCardAdapter;
import com.example.myapplication.Bean.AdapterData.ServiceCard;
import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.ServiceShort;
import com.example.myapplication.Bean.Httpdata.data.ServiceShortListData;
import com.example.myapplication.Constant;
import com.example.myapplication.R;
import com.example.myapplication.frontendCustomer.HomePage.CustomerServiceDetailPage;
import com.example.myapplication.network.CustomerApi;
import com.example.myapplication.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;


public class CustomerSearchPageFragment extends Fragment {

    private RecyclerView recyclerView;

    private String token;

    private String sortType;
    private String isDescending;

    private ImageButton btnSearch;

    public CustomerSearchPageFragment() {
        this.sortType = "fee";
        this.isDescending = "true";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_customer_search_page, container, false);


        //spinner for sort
        Spinner sort = rootView.findViewById(R.id.sort);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),R.array.sort, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sort.setAdapter(adapter1);

        sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                if (index == 0){
                    Toast.makeText(getContext(), "select Comprehensive", Toast.LENGTH_SHORT).show();
                } else if (index == 1) {
                    Toast.makeText(getContext(), "select By Rating", Toast.LENGTH_SHORT).show();
                }else if (index == 2) {
                    Toast.makeText(getContext(), "select Price Ascending", Toast.LENGTH_SHORT).show();
                }else if (index == 3) {
                    Toast.makeText(getContext(), "select Price Descending", Toast.LENGTH_SHORT).show();
                }else if (index == 4) {
                    Toast.makeText(getContext(), "select Credit", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


//   spinner for distance
        Spinner distance = rootView.findViewById(R.id.distance);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),R.array.distance, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        distance.setAdapter(adapter2);

        distance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                if (index == 0){
                    Toast.makeText(getContext(), "select less than 1 mile", Toast.LENGTH_SHORT).show();
                } else if (index == 1) {
                    Toast.makeText(getContext(), "select 1 mile - 3 miles", Toast.LENGTH_SHORT).show();
                }else if (index == 2) {
                    Toast.makeText(getContext(), "select 3 miles - 5 miles", Toast.LENGTH_SHORT).show();
                }else if (index == 3) {
                    Toast.makeText(getContext(), "select 5 miles+ ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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


    @SuppressLint("CheckResult")
    private void searchByKeyword(String token, String keyword, String sortType, String isDescending, View view){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.search(token, keyword, sortType, isDescending, 0, 10, "false")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<ServiceShortListData>>() {
                    @Override
                    public void onNext(HttpBaseBean<ServiceShortListData> serviceShortListDataHttpBaseBean) {
                        if(serviceShortListDataHttpBaseBean.getSuccess()){
                            List<ServiceCard> serviceCards = getServiceCardList(
                                    serviceShortListDataHttpBaseBean.getData().getServices());

//                            updateViewByList(serviceCards, view);
                        }else {
                            //test
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

    private List<ServiceCard> getServiceCardList(List<ServiceShort> serviceShorts){
        List<ServiceCard> serviceCards = new ArrayList<>();
        ServiceCard serviceCard;
        for(ServiceShort serviceShort : serviceShorts){
            String Link = Constant.BASE_URL + "public/service_provider/avatar?id=" + serviceShort.getProviderId().toString();
            serviceCard = new ServiceCard(serviceShort.getId().toString(), serviceShort.getFee().toString(),
                    serviceShort.getTitle(), Link);
            serviceCards.add(serviceCard);
        }
        return serviceCards;
    }


    private void updateViewByList(List<ServiceCard> serviceCards, View view) {
        //RecyclerView down here
        RecyclerView recyclerView = view.findViewById(R.id.homepageRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        // Create an Adapter and set it to the ListView
        ServiceCardAdapter serviceCardAdapter = new ServiceCardAdapter(serviceCards);

        serviceCardAdapter.setOnItemClickListener(new ServiceCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == 0){
                    startActivity(new Intent(getContext(), CustomerServiceDetailPage.class));
                } else if (position == 1) {
                    // 还没想好怎么把position和数据库里面的id绑定起来，现在这样只能根据index来确定点击了哪一个
                }
            }
        });

        recyclerView.setAdapter(serviceCardAdapter);
    }

    //Use adapter data list to update view.


//    private void updateViewByList(List<ServiceCard> serviceCards, View view){
//        //listView down here
//        RecyclerView recyclerView = view.findViewById(R.id.homepageRecyclerView);
//        // Create an Adapter and set it to the ListView
//        ServiceCardAdapter serviceCardAdapter = new ServiceCardAdapter(serviceCards,new );
//        recyclerView.setAdapter(serviceCardAdapter);
//
//
//    }

}