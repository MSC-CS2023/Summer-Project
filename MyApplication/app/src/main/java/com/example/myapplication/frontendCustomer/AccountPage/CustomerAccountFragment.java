package com.example.myapplication.frontendCustomer.AccountPage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.myapplication.R;


public class CustomerAccountFragment extends Fragment implements View.OnClickListener {

    ImageButton setting;
    ImageButton order;
    ImageButton wallet;
    ImageButton timetable;




    public CustomerAccountFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_account, container, false);

        setting = rootView.findViewById(R.id.setting);
        order = rootView.findViewById(R.id.order);
        timetable =rootView.findViewById(R.id.timetable);
        wallet = rootView.findViewById(R.id.wallet);

        setting.setOnClickListener(this);
        order.setOnClickListener(this);
        timetable.setOnClickListener(this);
        wallet.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.setting){startActivity(new Intent(getContext(), CustomerSettingPage.class));
        } else if (view.getId() == R.id.order) {
            startActivity(new Intent(getContext(), CustomerOrderPage.class));
        } else if (view.getId() == R.id.wallet) {
            startActivity(new Intent(getContext(), CustomerWalletPage.class));
        } else if (view.getId() == R.id.timetable) {
            startActivity(new Intent(getContext(), CustomerTimetablePage.class));
        }

    }
}