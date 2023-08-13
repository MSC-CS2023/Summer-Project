package com.example.myapplication.frontendProvider.homePages;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class ProviderServiceDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private ImageButton edit;
    private ImageButton delete;
    private ImageButton save;
    private ImageButton cancel;
    private TextView title;
    private TextView description;
    private TextView price;
    private TextView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_service_detail);

        initView();
    }

    private void initView() {
        edit = findViewById(R.id.btn_edit);
        edit.setOnClickListener(this);
        delete = findViewById(R.id.btn_delete);
        delete.setOnClickListener(this);

        //还需要获取各个可更改文本的view控件填入对应的data
        title = findViewById(R.id.detail_title);
        description = findViewById(R.id.detail_description);
        price = findViewById(R.id.detail_price);
        address = findViewById(R.id.detail_address);

        //click on back button
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_edit) {
            setContentView(R.layout.activity_provider_service_detail_editable);
            initEditView();
        } else if(view.getId() == R.id.btn_delete) {
            deleteAlert();
        } else if(view.getId() == R.id.btn_save) {
            //保存更改，发送请求
            Toast.makeText(this, "save clicked", Toast.LENGTH_SHORT).show();
            finish();
        } else if(view.getId() == R.id.btn_cancel) {
            setContentView(R.layout.activity_provider_service_detail);
            initView();
        }
    }

    private void initEditView() {
        save = findViewById(R.id.btn_save);
        save.setOnClickListener(this);
        cancel = findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(this);

        //还需要获取各个可更改文本的editView控件进行设置(编辑时还显示原来的标题等信息是用的hint,这个主要改hint应该是）
        title = findViewById(R.id.edtxt_title);
        description = findViewById(R.id.edtxt_description);
        price = findViewById(R.id.edtxt_price);
        address = findViewById(R.id.edtxt_address);

        //click on back button
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void deleteAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete service!")
                .setMessage("Are you sure to DELETE this service?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //发送请求删除服务
                        Toast.makeText(getApplicationContext(), "delete", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton("Cancel", null);

        AlertDialog deleteAlert = builder.create();
        deleteAlert.setOnShowListener(new DialogInterface.OnShowListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton = deleteAlert.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = deleteAlert.getButton(AlertDialog.BUTTON_NEGATIVE);

                positiveButton.setTextColor(R.color.black);
                negativeButton.setTextColor(R.color.black);
            }
        });

        deleteAlert.show();
    }
}