package com.example.myapplication.frontendCustomer.AccountPage.Order;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.myapplication.R;

public class CustomerOrderPageFinished extends AppCompatActivity {

    ImageButton submit;
    private SeekBar ratingSeekBar;

    ImageButton message;

    int mark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_page_finished);

        //seekBar part

        ratingSeekBar = findViewById(R.id.ratingSeekBar);
        ratingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // update mark
                mark = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // operation when start drag the controls
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mark = seekBar.getProgress();
                Toast.makeText(CustomerOrderPageFinished.this, "you choose " + mark + " marks", Toast.LENGTH_SHORT).show();
            }
        });


        //submit button
        submit = findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //transfer mark to DB, you can get mark here
                Toast.makeText(CustomerOrderPageFinished.this, "you choose" + mark , Toast.LENGTH_SHORT).show();
            }
        });

        message = findViewById(R.id.btn_message);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustomerOrderPageFinished.this, "click message", Toast.LENGTH_SHORT).show();
            }
        });




    }
}