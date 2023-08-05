package com.example.myapplication.frontendCustomer.AccountPage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Adapter.OrderCardAdapter;
import com.example.myapplication.Adapter.ServiceCardAdapter;
import com.example.myapplication.Bean.AdapterData.OrderCard;
import com.example.myapplication.Bean.AdapterData.ServiceCard;
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

public class CustomerOrderPage extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_page);

        initialTopBar();

        //test
        List<OrderCard> orderCards = new ArrayList<>();
        orderCards.add(new OrderCard("yes", "123", "ok", "200", "2"));
        orderCards.add(new OrderCard("no", "12352", "ok", "200", "2"));
        orderCards.add(new OrderCard("yes", "123", "ok", "200", "2"));

        updateViewByList(orderCards);

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
    }

    @SuppressLint("CheckResult")
    private void getOrders(String token){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.getCustomerOrders(token, null, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<OrderListData>>() {
                    @Override
                    public void onNext(HttpBaseBean<OrderListData> orderListDataHttpBaseBean) {
                        if(orderListDataHttpBaseBean.getSuccess()){
                            List<OrderCard> orderCards =
                                    getOrderCardList(orderListDataHttpBaseBean.getData().getBookingOrders());
                                updateViewByList(orderCards);
                        }else{
                            //test
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        //test
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private List<OrderCard> getOrderCardList(List<Order> orders){
        return null;
    }

    private void updateViewByList(List<OrderCard> orderCards){
        //listView down here
        ListView listView = findViewById(R.id.orderCardList);
        // Create an Adapter and set it to the ListView
        OrderCardAdapter orderCardAdapter = new OrderCardAdapter(orderCards, getApplicationContext());
        listView.setAdapter(orderCardAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "xxxxxx", Toast.LENGTH_SHORT).show();
            }
        });
    }

}