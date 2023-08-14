package com.example.myapplication.frontendCustomer.SearchPage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import com.example.myapplication.network.PublicMethodApi;
import com.example.myapplication.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;


public class CustomerSearchPageFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;

    Integer currentShowPosition;
    static final Integer DEFAULT_SHOW_NUMBER = 5;
    private String sortType;
    private Boolean isDescending;


    List<ServiceCard> dataList;

    private ImageButton btnSearch;
    private EditText keyword;

    public CustomerSearchPageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_customer_search_page, container, false);
        currentShowPosition = 0;

        keyword = rootView.findViewById(R.id.txtCustomerSearchBar);
        btnSearch = rootView.findViewById(R.id.btnCustomerSearchButton);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "click search button", Toast.LENGTH_SHORT).show();
            }
        });

        spinnerForSort(rootView);

        spinnerForDistance(rootView);

        swipeDown(rootView);

        return rootView;
    }

    private void swipeDown(View rootView) {
        swipeRefreshLayout = rootView.findViewById(R.id.swipeSearchPage);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                createDemoData(rootView);
                Toast.makeText(getContext(), "refresh action", Toast.LENGTH_SHORT).show();
//                search(rootView);
                //stop refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void createDemoData(View rootView) {
        List<ServiceCard> demoDataList = new ArrayList<>();

        ServiceCard serviceCard1 = new ServiceCard("Eric", "Repair Air conditioner", "100", "available tomorrow",
                "balabala", "picSrc",213L);
        ServiceCard serviceCard2 = new ServiceCard("Alice", "Clean gutter", "150", "available today",
                "balabala", "picSrc",213L);
        ServiceCard serviceCard3 = new ServiceCard("Alice", "Clean gutter", "160", "available today",
                "balabala", "picSrc",213L);

        demoDataList.add(serviceCard1);
        demoDataList.add(serviceCard2);
        demoDataList.add(serviceCard3);

        dataList = demoDataList;

        updateViewByList(dataList, rootView);
    }

    private void spinnerForDistance(View rootView) {
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
    }

    private void spinnerForSort(View rootView) {
        Spinner sort = rootView.findViewById(R.id.sort);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),R.array.sort, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sort.setAdapter(adapter1);
        sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                if (index == 0){
                    sortType = "fee";
                    isDescending = false;
                    Toast.makeText(getContext(), "select Price Ascending", Toast.LENGTH_SHORT).show();
                } else if (index == 1) {
                    sortType = "fee";
                    isDescending = true;
                    Toast.makeText(getContext(), "select Price Descending", Toast.LENGTH_SHORT).show();
                }else if (index == 2) {
                    sortType = "time";
                    isDescending = false;
                    Toast.makeText(getContext(), "select Time Ascending", Toast.LENGTH_SHORT).show();
                }else if (index == 3) {
                    sortType = "time";
                    isDescending = true;
                    Toast.makeText(getContext(), "select Time Descending", Toast.LENGTH_SHORT).show();
                }else if (index == 4) {
                    sortType = "alphabet";
                    isDescending = false;
                    Toast.makeText(getContext(), "select Comprehensive", Toast.LENGTH_SHORT).show();
                }else if (index == 5) {
                    Toast.makeText(getContext(), "select By Rating", Toast.LENGTH_SHORT).show();
                }else if (index == 6) {
                    Toast.makeText(getContext(), "select Credit", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @SuppressLint("CheckResult")
    private void searchByKeyword(String keyword, String sortType, Boolean isDescending, View view, Integer start, Integer number){
        PublicMethodApi publicMethodApi = RetrofitClient.getInstance().getService(PublicMethodApi.class);
        publicMethodApi.search(keyword, sortType, isDescending, start, number, false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<ServiceShortListData>>() {
                    @Override
                    public void onNext(HttpBaseBean<ServiceShortListData> serviceShortListDataHttpBaseBean) {
                        if(serviceShortListDataHttpBaseBean.getSuccess()){
                            if(start == 0){
                                dataList = getServiceCardList(
                                        serviceShortListDataHttpBaseBean.getData().getServices());
                                updateViewByList(dataList, view);
                            }else {
                                dataList.addAll(getServiceCardList(
                                        serviceShortListDataHttpBaseBean.getData().getServices()));
                            }
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
            String avatarLink = Constant.BASE_URL + "public/service_provider/avatar?id=" + serviceShort.getProviderId().toString();
            String pictureLink = Constant.BASE_URL + "get_pic?id=" + serviceShort.getPictureId();
            serviceCard = new ServiceCard(serviceShort.getUsername(), serviceShort.getFee().toString(),
                    serviceShort.getTitle(), avatarLink, serviceShort.getDescription(),
                    pictureLink, serviceShort.getId());
            serviceCards.add(serviceCard);
        }
        return serviceCards;
    }


    private void updateViewByList(List<ServiceCard> serviceCards, View view) {
        //RecyclerView down here
        RecyclerView recyclerView = view.findViewById(R.id.searchPageRecyclerView);
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
//                    searchByKeyword(keyword.getText().toString(), sortType, isDescending, view, currentShowPosition, DEFAULT_SHOW_NUMBER);
                    serviceCardAdapter.notifyDataSetChanged();
                }
            }
        });

    }


    public void search(View view) {
        currentShowPosition = 0;
        searchByKeyword(keyword.getText().toString(), sortType, isDescending, view, currentShowPosition, DEFAULT_SHOW_NUMBER);
    }


}