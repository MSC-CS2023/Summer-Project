package com.example.myapplication.frontendCustomer.AccountPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.R;

public class CustomerResetEmail extends AppCompatActivity implements View.OnClickListener {


    EditText newEmail;
    ImageButton save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_reset_email);

        setToolBar();

        initializeViews();
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

    void initializeViews(){
        save = findViewById(R.id.btnSave);
        save.setOnClickListener(this);
        newEmail = findViewById(R.id.txtNewEmail);
    }

    public void onClick(View view) {

        if (view.getId() == R.id.btnSave){
            Toast.makeText(this, "submit click", Toast.LENGTH_SHORT).show();
        }

    }

}