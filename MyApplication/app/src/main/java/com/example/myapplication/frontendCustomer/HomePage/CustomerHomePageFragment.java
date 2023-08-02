package com.example.myapplication.frontendCustomer.HomePage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.myapplication.R;
import com.example.myapplication.frontendCustomer.CustomerServiceDetailPage;

import java.util.ArrayList;
import java.util.List;

public class CustomerHomePageFragment extends Fragment {






    public CustomerHomePageFragment() {

    }

    private List<ImageButton> buttonList = new ArrayList<>();

    ImageButton button1;
    ImageButton button2;
    ImageButton button3;
    ImageButton button4;
    ImageButton button5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_customer_home_page, container, false);

        View ServiceDetail = rootView.findViewById(R.id.service1);

        button1 = rootView.findViewById(R.id.cleaning);
        button2 = rootView.findViewById(R.id.maintain);
        button3 = rootView.findViewById(R.id.laundry);
        button4 = rootView.findViewById(R.id.landscape);
        button5 = rootView.findViewById(R.id.others);

        buttonList.add(button1);
        buttonList.add(button2);
        buttonList.add(button3);
        buttonList.add(button4);
        buttonList.add(button5);

        ServiceDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == ServiceDetail.getId()){
                    startActivity(new Intent(getContext(), CustomerServiceDetailPage.class));
                }
            }
        });

        for (ImageButton Button : buttonList) {
            Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.getId() == Button.getId()){
                        if (Button.getId() == button1.getId()){
                            resetButton();
                            button1.setImageResource(R.drawable.btn_cleaning_aft);
                        } else if (Button.getId() == button2.getId()) {
                            resetButton();
                            button2.setImageResource(R.drawable.btn_maintain_aft);
                        }else if (Button.getId() == button3.getId()) {
                            resetButton();
                            button3.setImageResource(R.drawable.btn_laundry_aft);
                        }else if (Button.getId() == button4.getId()) {
                            resetButton();
                            button4.setImageResource(R.drawable.btn_landscape_aft);
                        }else if (Button.getId() == button5.getId()) {
                            resetButton();
                            button5.setImageResource(R.drawable.btn_more_aft);
                        }
                    }
                }
            });
        }



        return rootView;
    }



    public void handleButtonClick(ImageButton button){

        resetButton();
        button.setImageResource(R.drawable.btn_laundry);

    }


    private void resetButton() {
        button1.setImageResource(R.drawable.btn_cleaning);
        button2.setImageResource(R.drawable.btn_maintain);
        button3.setImageResource(R.drawable.btn_laundry);
        button4.setImageResource(R.drawable.btn_landscape);
        button5.setImageResource(R.drawable.btn_more);
    }

}