package com.example.myapplication.frontendProvider.profilePages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ProviderWalletActivity extends AppCompatActivity {

    ImageButton withdraw;
    List<TransactionCard> transactionCards = new ArrayList<>();

    SwipeRefreshLayout swipeRefreshLayout;

    TextView balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_wallet);

        withdraw = findViewById(R.id.btnWithdraw);
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInputDialog();
            }
        });
        balance = findViewById(R.id.balanceAmount);

        setToolBar();

        swipeDown();

        //test
//        transactionCards.add(new TransactionCard("Alice", "receive", "08/09/2023", "200"));
//        transactionCards.add(new TransactionCard("Pete", "pay", "05/09/2023", "300"));
//        transactionCards.add(new TransactionCard("May", "receive", "08/06/2023", "1000"));

        updateView(transactionCards);

    }

    private void swipeDown() {
        swipeRefreshLayout = findViewById(R.id.swipeTransaction);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //add refresh action here
                Toast.makeText(ProviderWalletActivity.this, "refresh action", Toast.LENGTH_SHORT).show();
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
        builder.setTitle("Input Data");

        // Inflate the layout for the dialog
        View view = getLayoutInflater().inflate(R.layout.balance_withdraw_alter, null);
        final EditText inputEditText = view.findViewById(R.id.inputAmount);
        builder.setView(view);

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String inputData = inputEditText.getText().toString();
                // update to database
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
                    Toast.makeText(ProviderWalletActivity.this, "Click the first one", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    Toast.makeText(ProviderWalletActivity.this, "Click the 2nd one", Toast.LENGTH_SHORT).show();
                }else if (position == 2) {
                    Toast.makeText(ProviderWalletActivity.this, "Click the 3rd one", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ProviderWalletActivity.this, "load more", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}