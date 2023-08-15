package com.example.myapplication.frontendProvider.orderPages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class ProviderOrderDetailProcessingActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    TextView title;
    ImageView image;
    TextView orderNumber;
    TextView state;
    Button finish;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_order_detail_processing);

        initView();
    }

    private void initView() {
        title = findViewById(R.id.order_detail_title);
        image = findViewById(R.id.order_detail_image);
        orderNumber = findViewById(R.id.order_detail_number);
        state = findViewById(R.id.order_detail_state);
        finish = findViewById(R.id.btn_confirm);
        finish.setOnClickListener(this);
        cancel = findViewById(R.id.btn_finish);
        cancel.setOnClickListener(this);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_finish) {
            //改变订单状态to be done
            Toast.makeText(this, "Finish clicked", Toast.LENGTH_SHORT).show();
            finish();
        } else if(view.getId() == R.id.btn_cancel) {
            //改变订单状态to be done
            Toast.makeText(this, "Cancel clicked", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}