package com.example.myapplication.frontendCustomer.AccountPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.Adapter.OrderPage.OrderCardAdapterAll;
import com.example.myapplication.Adapter.OrderPage.OrderCardAdapterProcessing;
import com.example.myapplication.Adapter.OrderPage.OrderCardAdapterRefund;
import com.example.myapplication.Adapter.OrderPage.OrderCardAdapterReview;
import com.example.myapplication.Adapter.OrderPage.OrderCardAdapterUnconfirmed;
import com.example.myapplication.Adapter.OrderPage.OrderCardAdapterUnpaid;
import com.example.myapplication.Adapter.ServiceCardAdapter;
import com.example.myapplication.Bean.AdapterData.OrderCard;
import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.Order;
import com.example.myapplication.Bean.Httpdata.data.OrderListData;
import com.example.myapplication.R;
import com.example.myapplication.network.CustomerApi;
import com.example.myapplication.network.RetrofitClient;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;
import kotlin.jvm.internal.PackageReference;

public class CustomerOrderPage extends AppCompatActivity {

    private String token;
    Integer currentShowPosition;
    static final Integer DEFAULT_SHOW_NUMBER = 5;

    List<OrderCard> orderCards = new ArrayList<>();

    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_page);

        SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        this.token = sp.getString("token", "");
        currentShowPosition = 0;

        setToolBar();

        initialTopBar();

        swipeDown();

        //test

        orderCards.add(new OrderCard("yes", "123", "ok", "200", 2));
        orderCards.add(new OrderCard("no", "12352", "ok", "200", 2));
        orderCards.add(new OrderCard("yes", "123", "ok", "200", 2));

        updateViewByList_all(orderCards);

    }

    private void swipeDown() {
        swipeRefreshLayout = findViewById(R.id.swipeOrderPage);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //add refresh action here
                Toast.makeText(CustomerOrderPage.this, "refresh action", Toast.LENGTH_SHORT).show();
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
        tab2.setText("Unpaid");
        tabLayout.addTab(tab2);

        TabLayout.Tab tab3 = tabLayout.newTab();
        tab3.setText("Unconfirmed");
        tabLayout.addTab(tab3);

        TabLayout.Tab tab4 = tabLayout.newTab();
        tab4.setText("Processing");
        tabLayout.addTab(tab4);

        TabLayout.Tab tab5 = tabLayout.newTab();
        tab5.setText("Review");
        tabLayout.addTab(tab5);

        TabLayout.Tab tab6 = tabLayout.newTab();
        tab6.setText("Refund");
        tabLayout.addTab(tab6);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position){
                    case 0: //all
                        Toast.makeText(CustomerOrderPage.this, "select all", Toast.LENGTH_SHORT).show();
                        updateViewByList_all(orderCards);
                        break;
                    case 1: // unpaid
                        Toast.makeText(CustomerOrderPage.this, "select unpaid", Toast.LENGTH_SHORT).show();
                        updateViewByList_unpaid(orderCards);
                        break;
                    case 2: // unconfirmed
                        Toast.makeText(CustomerOrderPage.this, "select unconfirmed", Toast.LENGTH_SHORT).show();
                        updateViewByList_unconfirmed(orderCards);
                        break;
                    case 3: // processing
                        Toast.makeText(CustomerOrderPage.this, "select processing", Toast.LENGTH_SHORT).show();
                        updateViewByList_processing(orderCards);
                        break;
                    case 4: // review
                        Toast.makeText(CustomerOrderPage.this, "select review", Toast.LENGTH_SHORT).show();
                        updateViewByList_review(orderCards);
                        break;
                    case 5: // refund
                        Toast.makeText(CustomerOrderPage.this, "select refund", Toast.LENGTH_SHORT).show();
                        updateViewByList_refund(orderCards);
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//  refresh
            }
        });


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
                            List<OrderCard> orderCards =
                                    getOrderCardList(orderListDataHttpBaseBean.getData().getBookingOrders());
                                updateViewByList_all(orderCards);
                        }else{
                            //test
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "Network error! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("CheckResult")
    private void getCertainOrders(String token, String key, Integer start, Integer number, Boolean isOr, Boolean isNot){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.searchCustomerOrder(token, key, isOr, start, number, isNot)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<OrderListData>>() {
                    @Override
                    public void onNext(HttpBaseBean<OrderListData> orderListDataHttpBaseBean) {
                        if(orderListDataHttpBaseBean.getSuccess()){

                        }else{
                            //test
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "Network error! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private List<OrderCard> getOrderCardList(List<Order> orders){
        return null;
    }

    private void updateViewByList_all(List<OrderCard> orderCards) {
        //RecyclerView down here
        RecyclerView recyclerView = findViewById(R.id.orderCardRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // Create an Adapter and set it to the ListView
        OrderCardAdapterAll orderCardAdapter = new OrderCardAdapterAll(orderCards);

        orderCardAdapter.setOnItemClickListener(new ServiceCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == 0){
                    Toast.makeText(CustomerOrderPage.this, "Click the first one", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    Toast.makeText(CustomerOrderPage.this, "Click the 2nd one", Toast.LENGTH_SHORT).show();
                }else if (position == 2) {
                    Toast.makeText(CustomerOrderPage.this, "Click the 3rd one", Toast.LENGTH_SHORT).show();
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
                }
            }
        });
    }

    private void updateViewByList_processing(List<OrderCard> orderCards) {
        //RecyclerView down here
        RecyclerView recyclerView = findViewById(R.id.orderCardRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // Create an Adapter and set it to the ListView
        OrderCardAdapterProcessing orderCardAdapter = new OrderCardAdapterProcessing(orderCards);

        orderCardAdapter.setOnItemClickListener(new ServiceCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == 0){
                    Toast.makeText(CustomerOrderPage.this, "Click the first one", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    Toast.makeText(CustomerOrderPage.this, "Click the 2nd one", Toast.LENGTH_SHORT).show();
                }else if (position == 2) {
                    Toast.makeText(CustomerOrderPage.this, "Click the 3rd one", Toast.LENGTH_SHORT).show();
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
                }
            }
        });

    }
    private void updateViewByList_unconfirmed(List<OrderCard> orderCards) {
        //RecyclerView down here
        RecyclerView recyclerView = findViewById(R.id.orderCardRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // Create an Adapter and set it to the ListView
        OrderCardAdapterUnconfirmed orderCardAdapter = new OrderCardAdapterUnconfirmed(orderCards);

        orderCardAdapter.setOnItemClickListener(new ServiceCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == 0){
                    Toast.makeText(CustomerOrderPage.this, "Click the first one", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    Toast.makeText(CustomerOrderPage.this, "Click the 2nd one", Toast.LENGTH_SHORT).show();
                }else if (position == 2) {
                    Toast.makeText(CustomerOrderPage.this, "Click the 3rd one", Toast.LENGTH_SHORT).show();
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
                }
            }
        });

    }
    private void updateViewByList_unpaid(List<OrderCard> orderCards) {
        //RecyclerView down here
        RecyclerView recyclerView = findViewById(R.id.orderCardRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // Create an Adapter and set it to the ListView
        OrderCardAdapterUnpaid orderCardAdapter = new OrderCardAdapterUnpaid(orderCards);

        orderCardAdapter.setOnItemClickListener(new ServiceCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == 0){
                    Toast.makeText(CustomerOrderPage.this, "Click the first one", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    Toast.makeText(CustomerOrderPage.this, "Click the 2nd one", Toast.LENGTH_SHORT).show();
                }else if (position == 2) {
                    Toast.makeText(CustomerOrderPage.this, "Click the 3rd one", Toast.LENGTH_SHORT).show();
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
                }
            }
        });

    }
    private void updateViewByList_review(List<OrderCard> orderCards) {
        //RecyclerView down here
        RecyclerView recyclerView = findViewById(R.id.orderCardRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // Create an Adapter and set it to the ListView
        OrderCardAdapterReview orderCardAdapter = new OrderCardAdapterReview(orderCards);

        orderCardAdapter.setOnItemClickListener(new ServiceCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == 0){
                    Toast.makeText(CustomerOrderPage.this, "Click the first one", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    Toast.makeText(CustomerOrderPage.this, "Click the 2nd one", Toast.LENGTH_SHORT).show();
                }else if (position == 2) {
                    Toast.makeText(CustomerOrderPage.this, "Click the 3rd one", Toast.LENGTH_SHORT).show();
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
                }
            }
        });

    }
    private void updateViewByList_refund(List<OrderCard> orderCards) {
        //RecyclerView down here
        RecyclerView recyclerView = findViewById(R.id.orderCardRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // Create an Adapter and set it to the ListView
        OrderCardAdapterRefund orderCardAdapter = new OrderCardAdapterRefund(orderCards);

        orderCardAdapter.setOnItemClickListener(new ServiceCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == 0){
                    Toast.makeText(CustomerOrderPage.this, "Click the first one", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    Toast.makeText(CustomerOrderPage.this, "Click the 2nd one", Toast.LENGTH_SHORT).show();
                }else if (position == 2) {
                    Toast.makeText(CustomerOrderPage.this, "Click the 3rd one", Toast.LENGTH_SHORT).show();
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
                }
            }
        });

    }


}