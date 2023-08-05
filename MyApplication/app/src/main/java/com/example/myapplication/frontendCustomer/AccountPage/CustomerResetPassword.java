package com.example.myapplication.frontendCustomer.AccountPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.R;

public class CustomerResetPassword extends AppCompatActivity implements View.OnClickListener {

    EditText oldPassword;
    EditText newPassword;
    ImageButton submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_reset_password);

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
        submit = findViewById(R.id.btnSubmit);
        submit.setOnClickListener(this);
        oldPassword = findViewById(R.id.txtNewUsername);

        newPassword = findViewById(R.id.txtNewUsername);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit){
            Toast.makeText(this, "submit click", Toast.LENGTH_SHORT).show();
        }
    }
}