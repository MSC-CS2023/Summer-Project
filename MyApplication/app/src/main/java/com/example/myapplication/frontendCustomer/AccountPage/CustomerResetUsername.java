package com.example.myapplication.frontendCustomer.AccountPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.R;

public class CustomerResetUsername extends AppCompatActivity implements View.OnClickListener {


    EditText oldName;
    EditText newName;
    ImageButton submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_reset_username);

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
        oldName = findViewById(R.id.txtNewUsername);

        newName = findViewById(R.id.txtNewUsername);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnSubmit){
            Toast.makeText(this, "submit click", Toast.LENGTH_SHORT).show();
        }

    }
}