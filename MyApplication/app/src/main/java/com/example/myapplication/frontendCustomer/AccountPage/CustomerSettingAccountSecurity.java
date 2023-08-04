package com.example.myapplication.frontendCustomer.AccountPage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;

public class CustomerSettingAccountSecurity extends AppCompatActivity implements View.OnClickListener {

//    ImageButton btnCustomerForgetPasswordSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_setting_account_seurity);

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // back to previous page
            }
        });
    }


    @Override
    public void onClick(View view) {
//        if (view.getId() == R.id.btnForgetPasswordSend){
//            Toast.makeText(this, "verify code sent", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(CustomerAccountSecurity.this, CustomerResetPassword.class));
//        }
    }

    public void setToolBar(){

    }

}

