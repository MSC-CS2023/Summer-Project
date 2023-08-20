package com.example.myapplication.frontendCustomer.AccountPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.ServiceCardAdapter;
import com.example.myapplication.Adapter.TransactionsCard;
import com.example.myapplication.Bean.AdapterData.TransactionCard;
import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.data.BalanceData;
import com.example.myapplication.R;
import com.example.myapplication.network.CustomerApi;
import com.example.myapplication.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class CustomerWalletPage extends AppCompatActivity {

    private String token;
    ImageButton recharge;
    TextView balance;
    List<TransactionCard> transactionCards = new ArrayList<>();

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_wallet_page);

        SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        this.token = sp.getString("token", "");

        recharge = findViewById(R.id.btnRecharge);
        balance = findViewById(R.id.balanceAmount);

        recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInputDialog();
            }
        });

        setToolBar();

        swipeDown();

        getBalance();

        //test
//        transactionCards.add(new TransactionCard("Alice", "receive", "08/09/2023", "200"));
//        transactionCards.add(new TransactionCard("Pete", "pay", "05/09/2023", "300"));
//        transactionCards.add(new TransactionCard("May", "receive", "08/06/2023", "1000"));

//        updateView(transactionCards);

    }

    private void swipeDown() {
        swipeRefreshLayout = findViewById(R.id.swipeTransaction);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //add refresh action here
                Toast.makeText(CustomerWalletPage.this, "refresh action", Toast.LENGTH_SHORT).show();
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

    void openInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please enter the recharge amount:");

        // Inflate the layout for the dialog
        View view = getLayoutInflater().inflate(R.layout.balance_charge_alter, null);
        final EditText inputEditText = view.findViewById(R.id.inputBalance);
        builder.setView(view);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                rechargeBalance(Double.parseDouble(inputEditText.getText().toString()));
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                positiveButton.setTextColor(R.color.black);
                negativeButton.setTextColor(R.color.black);
            }
        });

        alertDialog.show();
    }

    private void updateView(List<TransactionCard> transactionCards) {
        //RecyclerView down here
        RecyclerView recyclerView = findViewById(R.id.transactionRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // Create an Adapter and set it to the ListView
        TransactionsCard transactionCardAdapter = new TransactionsCard(transactionCards);

        transactionCardAdapter.setOnItemClickListener(new ServiceCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == 0){
                    Toast.makeText(CustomerWalletPage.this, "Click the first one", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    Toast.makeText(CustomerWalletPage.this, "Click the 2nd one", Toast.LENGTH_SHORT).show();
                }else if (position == 2) {
                    Toast.makeText(CustomerWalletPage.this, "Click the 3rd one", Toast.LENGTH_SHORT).show();
                }
            }
        });

        recyclerView.setAdapter(transactionCardAdapter);

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
                    Toast.makeText(CustomerWalletPage.this, "load more", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @SuppressLint("CheckResult")
    private void getBalance(){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.getCustomerBalance(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<BalanceData>>() {
                    @Override
                    public void onNext(HttpBaseBean<BalanceData> balanceDataHttpBaseBean) {
                        if(balanceDataHttpBaseBean.getSuccess()){
                            try {
                                balance.setText(balanceDataHttpBaseBean.getData().getBalance().toString());
                            }catch (NullPointerException ignored){}
                        }
                    }
                    @Override
                    public void onError(Throwable t) {}
                    @Override
                    public void onComplete() {}
                });
    }

    @SuppressLint("CheckResult")
    private void rechargeBalance(Double money){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.customerDeposit(token, money)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<BalanceData>>() {
                    @Override
                    public void onNext(HttpBaseBean<BalanceData> balanceDataHttpBaseBean) {
                        if(balanceDataHttpBaseBean.getSuccess()){
                            try {
                                Toast.makeText(getApplicationContext(),
                                        "Recharge successfully!", Toast.LENGTH_SHORT).show();
                                balance.setText(balanceDataHttpBaseBean.getData().getBalance().toString());
                            }catch (NullPointerException ignored){}
                        }else {
                            Toast.makeText(getApplicationContext(), balanceDataHttpBaseBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(Throwable t) {}
                    @Override
                    public void onComplete() {}
                });
    }

    @SuppressLint("CheckResult")
    private void withdrawBalance(Double money){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.customerWithdraw(token, money)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<BalanceData>>() {
                    @Override
                    public void onNext(HttpBaseBean<BalanceData> balanceDataHttpBaseBean) {
                        if(balanceDataHttpBaseBean.getSuccess()){
                            try {
                                Toast.makeText(getApplicationContext(),
                                        "Withdraw successfully!", Toast.LENGTH_SHORT).show();
                                balance.setText(balanceDataHttpBaseBean.getData().getBalance().toString());
                            }catch (NullPointerException ignored){}
                        }
                    }
                    @Override
                    public void onError(Throwable t) {}
                    @Override
                    public void onComplete() {}
                });
    }

}