package com.example.myapplication.frontendCustomer.AccountPage.Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.Adapter.OrderCardAdapterAll;
import com.example.myapplication.Adapter.ServiceCardAdapter;
import com.example.myapplication.Bean.AdapterData.OrderCard;
import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.Order;
import com.example.myapplication.Bean.Httpdata.data.OrderListData;
import com.example.myapplication.network.Constant;
import com.example.myapplication.R;
import com.example.myapplication.network.CustomerApi;
import com.example.myapplication.network.RetrofitClient;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class CustomerOrderPage extends AppCompatActivity {

    static final Integer DEFAULT_SHOW_NUMBER = 5;
    private static final int ALL_TAB = 1;
    private static final int UNCONFIRMED_TAB = 2;
    private static final int PROCESSING_TAB = 3;
    private static final int FINISHED_TAB = 4;
    private static final int REJECTED_TAB = 5;
    private static final int CANCELED_TAB = 6;

    private String token;
    Integer currentShowPosition;
    private int currentTab;
    List<OrderCard> orderCards = new ArrayList<>();

    SwipeRefreshLayout swipeRefreshLayout;

    OrderCardAdapterAll orderCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_page);

        SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        this.token = sp.getString("token", "");
        currentShowPosition = 0;
        currentTab = ALL_TAB;

        setToolBar();

        initialTopBar();

        swipeDown();

        createDemo();
        updateOrderData(token, currentShowPosition, DEFAULT_SHOW_NUMBER);
    }

    private void createDemo(){
        orderCards = new ArrayList<>();
        orderCards.add(new OrderCard(122313343L, "name1", "200", "link", "Unconfirmed"));
        orderCards.add(new OrderCard(121133L, "name1213", "200", "link", "Processing"));
        orderCards.add(new OrderCard( 12212313L, "name3", "200", "link", "Finished"));
        orderCards.add(new OrderCard( 12212313L, "name2", "200", "link", "Canceled"));
        updateViewByList(orderCards);
    }

    private void swipeDown() {
        swipeRefreshLayout = findViewById(R.id.swipeOrderPage);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //add refresh action here
                createDemo();

                currentShowPosition = 0;
                updateOrderData(token, currentShowPosition, DEFAULT_SHOW_NUMBER);
                //stop refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // back to previous page
            }
        });
    }

    void initialTopBar(){
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        TabLayout.Tab tab1 = tabLayout.newTab();
        tab1.setText("All");
        tabLayout.addTab(tab1);

        TabLayout.Tab tab2 = tabLayout.newTab();
        tab2.setText("Unconfirmed");
        tabLayout.addTab(tab2);

        TabLayout.Tab tab3 = tabLayout.newTab();
        tab3.setText("Processing");
        tabLayout.addTab(tab3);

        TabLayout.Tab tab4 = tabLayout.newTab();
        tab4.setText("Finished");
        tabLayout.addTab(tab4);

        TabLayout.Tab tab5 = tabLayout.newTab();
        tab5.setText("Canceled");
        tabLayout.addTab(tab5);

        TabLayout.Tab tab6 = tabLayout.newTab();
        tab6.setText("Rejected");
        tabLayout.addTab(tab6);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position){
                    case 0: //
                        currentTab = ALL_TAB;
                        Toast.makeText(CustomerOrderPage.this, "select all", Toast.LENGTH_SHORT).show();
                        break;
                    case 1: // unconfirmed
                        currentTab = UNCONFIRMED_TAB;
                        Toast.makeText(CustomerOrderPage.this, "select unconfirmed", Toast.LENGTH_SHORT).show();
                        break;
                    case 2: // processing
                        currentTab = PROCESSING_TAB;
                        Toast.makeText(CustomerOrderPage.this, "select processing", Toast.LENGTH_SHORT).show();
                        break;
                    case 3: // Finish
                        currentTab = FINISHED_TAB;
                        Toast.makeText(CustomerOrderPage.this, "select Finished", Toast.LENGTH_SHORT).show();
                        break;
                    case 4: // Cancel
                        currentTab = CANCELED_TAB;
                        Toast.makeText(CustomerOrderPage.this, "select Canceled", Toast.LENGTH_SHORT).show();
                        break;
                    case 5: // Rejected
//                        currentTab = CANCELED_TAB;
                        Toast.makeText(CustomerOrderPage.this, "select Rejected", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                createDemo();

                currentShowPosition = 0;
                updateOrderData(token, currentShowPosition, DEFAULT_SHOW_NUMBER);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                createDemo();

                currentShowPosition = 0;
                updateOrderData(token, currentShowPosition, DEFAULT_SHOW_NUMBER);
            }
        });
    }

    private void updateViewByList(List<OrderCard> orderCards) {
        //RecyclerView down here
        RecyclerView recyclerView = findViewById(R.id.orderCardRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // Create an Adapter and set it to the ListView
        orderCardAdapter = new OrderCardAdapterAll(orderCards, getApplicationContext());

        orderCardAdapter.setOnItemClickListener(new ServiceCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (orderCards.get(position).getOrderSate().toLowerCase()){
                    case "unconfirmed":
                        startActivity(new Intent(getApplicationContext(), CustomerOrderPageUnconfirmed.class)
                                .putExtra("orderId", orderCards.get(position).getOrderId()));
                        break;
                    case "processing":
                        startActivity(new Intent(getApplicationContext(), CustomerOrderPageProcessing.class)
                                .putExtra("orderId", orderCards.get(position).getOrderId()));
                        break;
                    case "finished":
                        startActivity(new Intent(getApplicationContext(), CustomerOrderPageFinished.class)
                                .putExtra("orderId", orderCards.get(position).getOrderId()));
                        break;
                    case "canceled":
                        startActivity(new Intent(getApplicationContext(), CustomerOrderPageCancel.class)
                                .putExtra("orderId", orderCards.get(position).getOrderId()));
                        break;
                    case "rejected":
                        startActivity(new Intent(getApplicationContext(), CustomerOrderPageRejected.class)
                                .putExtra("orderId", orderCards.get(position).getOrderId()));
                        break;
                    default:
                        break;
                }
            }
        });

        recyclerView.setAdapter(orderCardAdapter);

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
                    Toast.makeText(CustomerOrderPage.this, "load more", Toast.LENGTH_SHORT).show();
                    orderCards.add(new OrderCard(121133L, "name1213",
                            "~"+ SystemClock.currentThreadTimeMillis(),
                            "link", "Processing"));
                    orderCards.add(new OrderCard(121133L, "name1213",
                            "~"+ SystemClock.currentThreadTimeMillis(),
                            "link", "Finished"));
                    currentShowPosition += DEFAULT_SHOW_NUMBER;
                    updateOrderData(token, currentShowPosition, DEFAULT_SHOW_NUMBER);
                    orderCardAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void updateOrderData(String token, Integer start, Integer number){
        switch (currentTab){
            case ALL_TAB:
                getOrders(token, start, number);
                break;
            case UNCONFIRMED_TAB:
                getUnconfirmedOrders(token, start, number);
                break;
            case PROCESSING_TAB:
                getProcessingOrders(token, start, number);
                break;
            case FINISHED_TAB:
                getFinishedOrders(token, start, number);
                break;
            case REJECTED_TAB:
                getRejectedOrders(token, start, number);
                break;
            case CANCELED_TAB:
                getCanceledOrders(token, start, number);
            default:
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void getOrders(String token, Integer start, Integer number){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.getCustomerOrders(token, start, number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<OrderListData>>() {
                    @Override
                    public void onNext(HttpBaseBean<OrderListData> orderListDataHttpBaseBean) {
                        if(orderListDataHttpBaseBean.getSuccess()){
                            try{
                                if(start == 0){
                                    orderCards = getOrderCardList(orderListDataHttpBaseBean.getData().getBookingOrders());
                                    updateViewByList(orderCards);
                                }else{
                                    orderCards.addAll(getOrderCardList(orderListDataHttpBaseBean.getData().getBookingOrders()));
                                    orderCardAdapter.notifyDataSetChanged();
                                }
                            }catch (Exception ignored){}
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "Network error! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {}
                });
    }

    @SuppressLint("CheckResult")
    private void getUnconfirmedOrders(String token, Integer start, Integer number){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.getCustomerUnconfirmedOrders(token, start, number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<OrderListData>>() {
                    @Override
                    public void onNext(HttpBaseBean<OrderListData> orderListDataHttpBaseBean) {
                        if(orderListDataHttpBaseBean.getSuccess()){
                            try{
                                if(start == 0){
                                    orderCards = getOrderCardList(orderListDataHttpBaseBean.getData().getBookingOrders());
                                    updateViewByList(orderCards);
                                }else{
                                    orderCards.addAll(getOrderCardList(orderListDataHttpBaseBean.getData().getBookingOrders()));
                                    orderCardAdapter.notifyDataSetChanged();
                                }
                            }catch (Exception ignored){}
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "Network error! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {}
                });
    }

    @SuppressLint("CheckResult")
    private void getRejectedOrders(String token, Integer start, Integer number){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.getCustomerRejectedOrders(token, start, number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<OrderListData>>() {
                    @Override
                    public void onNext(HttpBaseBean<OrderListData> orderListDataHttpBaseBean) {
                        if(orderListDataHttpBaseBean.getSuccess()){
                            try{
                                if(start == 0){
                                    orderCards = getOrderCardList(orderListDataHttpBaseBean.getData().getBookingOrders());
                                    updateViewByList(orderCards);
                                }else{
                                    orderCards.addAll(getOrderCardList(orderListDataHttpBaseBean.getData().getBookingOrders()));
                                    orderCardAdapter.notifyDataSetChanged();
                                }
                            }catch (Exception ignored){}
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "Network error! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {}
                });
    }
    @SuppressLint("CheckResult")
    private void getCanceledOrders(String token, Integer start, Integer number){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.getCustomerCanceledOrders(token, start, number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<OrderListData>>() {
                    @Override
                    public void onNext(HttpBaseBean<OrderListData> orderListDataHttpBaseBean) {
                        if(orderListDataHttpBaseBean.getSuccess()){
                            try{
                                if(start == 0){
                                    orderCards = getOrderCardList(orderListDataHttpBaseBean.getData().getBookingOrders());
                                    updateViewByList(orderCards);
                                }else{
                                    orderCards.addAll(getOrderCardList(orderListDataHttpBaseBean.getData().getBookingOrders()));
                                    orderCardAdapter.notifyDataSetChanged();
                                }
                            }catch (Exception ignored){}
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "Network error! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {}
                });
    }
    @SuppressLint("CheckResult")
    private void getFinishedOrders(String token, Integer start, Integer number){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.getCustomerFinishedOrders(token, start, number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<OrderListData>>() {
                    @Override
                    public void onNext(HttpBaseBean<OrderListData> orderListDataHttpBaseBean) {
                        if(orderListDataHttpBaseBean.getSuccess()){
                            try{
                                if(start == 0){
                                    orderCards = getOrderCardList(orderListDataHttpBaseBean.getData().getBookingOrders());
                                    updateViewByList(orderCards);
                                }else{
                                    orderCards.addAll(getOrderCardList(orderListDataHttpBaseBean.getData().getBookingOrders()));
                                    orderCardAdapter.notifyDataSetChanged();
                                }
                            }catch (Exception ignored){}
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "Network error! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {}
                });
    }
    @SuppressLint("CheckResult")
    private void getProcessingOrders(String token, Integer start, Integer number){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.getCustomerProcessingOrders(token, start, number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<OrderListData>>() {
                    @Override
                    public void onNext(HttpBaseBean<OrderListData> orderListDataHttpBaseBean) {
                        if(orderListDataHttpBaseBean.getSuccess()){
                            try{
                                if(start == 0){
                                    orderCards = getOrderCardList(orderListDataHttpBaseBean.getData().getBookingOrders());
                                    updateViewByList(orderCards);
                                }else{
                                    orderCards.addAll(getOrderCardList(orderListDataHttpBaseBean.getData().getBookingOrders()));
                                    orderCardAdapter.notifyDataSetChanged();
                                }
                            }catch (Exception ignored){}
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "Network error! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {}
                });
    }


    private List<OrderCard> getOrderCardList(List<Order> orders){
        List<OrderCard> orderCardList = new ArrayList<>();
        OrderCard orderCard;
        String state;
        for(Order order : orders){
            state = "";
            String link = Constant.BASE_URL + "get_pic?id=" + order.getServiceShort().getPictureId();
            orderCard = new OrderCard(order.getId(), order.getServiceShort().getTitle(),
                    order.getServiceShort().getFee().toString(), link, state);
            orderCardList.add(orderCard);
        }
        return orderCardList;
    }

}