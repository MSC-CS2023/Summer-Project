package com.example.myapplication.frontendCustomer.AccountPage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.data.ModifyDetailData;
import com.example.myapplication.R;
import com.example.myapplication.network.CustomerApi;
import com.example.myapplication.network.RetrofitClient;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class CustomerSettingAccountSecurity extends AppCompatActivity implements View.OnClickListener{

//    boolean isEditMode = false;
//    Button edit;
//    Button save;
//    LinearLayout acLayout;
    private String token;

    ImageButton AS_username;
    ImageButton AS_phone;
    ImageButton AS_email;
    ImageButton As_password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_setting_account_seurity);

        SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        this.token = sp.getString("token", "");

        setToolBar();

//        edit = findViewById(R.id.edit);
//        acLayout =findViewById(R.id.acLinearLayout);
//
//        edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switchMode();
//            }
//        });

        initializeButton();


    }

    private void initializeButton() {
        AS_username = findViewById(R.id.AS_username);
        AS_phone = findViewById(R.id.AS_phone);
        AS_email = findViewById(R.id.AS_email);
        As_password = findViewById(R.id.AS_password);

        AS_username.setOnClickListener(this);
        AS_phone.setOnClickListener(this);
        AS_email.setOnClickListener(this);
        As_password.setOnClickListener(this);

    }


//    void switchMode() {
//        isEditMode = !isEditMode;
//
//        if (!isEditMode){
//            edit.setText("Edit");
//            //update database should be here
//        }else {
//            edit.setText("Save");
//        }
//
//        for (int i = 0; i < acLayout.getChildCount(); i++) {
//            View child = acLayout.getChildAt(i);
//            if (child instanceof TextView && child != save && child != edit) {
//                TextView textView = (TextView) child;
//                if (isEditMode) {
//                    EditText editText = new EditText(this);
//                    editText.setLayoutParams(textView.getLayoutParams());
//                    editText.setText(textView.getText());
//                    editText.setBackground(textView.getBackground());
//                    float textSizeInPixels = textView.getTextSize();
//                    float textSizeInSp = textSizeInPixels / getResources().getDisplayMetrics().scaledDensity;
//                    editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeInSp);
//                    editText.setElevation(textView.getElevation());
//                    acLayout.removeView(textView);
//                    acLayout.addView(editText, i);
//                } else {
//                    EditText editText = (EditText) child;
//                    TextView newTextView = new TextView(this);
//                    newTextView.setLayoutParams(editText.getLayoutParams());
//                    newTextView.setText(editText.getText());
//                    float paddingDp = 15;  // 15dp
//                    int paddingPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, paddingDp, getResources().getDisplayMetrics());
//                    newTextView.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
//                    applyTextViewStyle(newTextView, editText); // Apply original style
//                    acLayout.removeView(editText);
//                    acLayout.addView(newTextView, i);
//                }
//                }
//            }
//        }


//    private void applyTextViewStyle(TextView target, TextView source) {
//        target.setBackground(source.getBackground());
//        target.setTextColor(source.getCurrentTextColor());
//        target.setTextSize(TypedValue.COMPLEX_UNIT_PX, source.getTextSize());
//        target.setElevation(source.getElevation());
//    }




    public void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // back to previous page
            }
        });
    }

    @SuppressLint("CheckResult")
    private void modifyPersonalDetail(String token, String modifiedItem, String modifiedContent){
        CustomerApi customerApi = RetrofitClient.getInstance().getService(CustomerApi.class);
        customerApi.modifyCustomerDetail(token, modifiedItem, modifiedContent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<ModifyDetailData>>() {
                    @Override
                    public void onNext(HttpBaseBean<ModifyDetailData> modifyDetailDataHttpBaseBean) {
                        if(modifyDetailDataHttpBaseBean.getSuccess()){
                            Toast.makeText(getApplicationContext(),
                                    "Account detail modified successfully.", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    modifyDetailDataHttpBaseBean.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.AS_username) {
            startActivity(new Intent(CustomerSettingAccountSecurity.this, CustomerResetUsername.class));
        } else if (v.getId() == R.id.AS_phone) {
            startActivity(new Intent(CustomerSettingAccountSecurity.this, CustomerResetPhone.class));
        }else if (v.getId() == R.id.AS_email) {
            startActivity(new Intent(CustomerSettingAccountSecurity.this, CustomerResetEmail.class));
        }else if (v.getId() == R.id.AS_password) {
            startActivity(new Intent(CustomerSettingAccountSecurity.this, CustomerResetPassword.class));
        }
    }
}

