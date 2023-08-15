package com.example.myapplication.frontendCustomer.AccountPage.Order;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.myapplication.R;

public class CustomerOrderPageUnconfirmed extends AppCompatActivity {

    ImageButton confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_page_unconfirmed);

        confirm = findViewById(R.id.btn_CustomerConfirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentAlert();
            }
        });

    }

    private void paymentAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerOrderPageUnconfirmed.this);

        builder.setTitle("Are you sure to pay ?")
                .setMessage("You need to pay" )
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //update money
//                        createCustomerOrder(token, serviceId, System.currentTimeMillis() + 36000000,
//                                System.currentTimeMillis() + 39600000);
                    }
                })
                .setNegativeButton("Cancel", null);

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

}