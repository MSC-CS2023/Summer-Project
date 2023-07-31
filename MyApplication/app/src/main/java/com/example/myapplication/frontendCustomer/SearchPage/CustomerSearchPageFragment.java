package com.example.myapplication.frontendCustomer.SearchPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.R;


public class CustomerSearchPageFragment extends Fragment {


    public CustomerSearchPageFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_customer_search_page, container, false);


        //spinner for sort
        Spinner sort = rootView.findViewById(R.id.sort);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),R.array.sort, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sort.setAdapter(adapter1);

        sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                if (index == 0){
                    Toast.makeText(getContext(), "select Comprehensive", Toast.LENGTH_SHORT).show();
                } else if (index == 1) {
                    Toast.makeText(getContext(), "select By Rating", Toast.LENGTH_SHORT).show();
                }else if (index == 2) {
                    Toast.makeText(getContext(), "select Price Ascending", Toast.LENGTH_SHORT).show();
                }else if (index == 3) {
                    Toast.makeText(getContext(), "select Price Descending", Toast.LENGTH_SHORT).show();
                }else if (index == 4) {
                    Toast.makeText(getContext(), "select Credit", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


//   spinner for distance
        Spinner distance = rootView.findViewById(R.id.distance);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),R.array.distance, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        distance.setAdapter(adapter2);

        distance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                if (index == 0){
                    Toast.makeText(getContext(), "select less than 1 mile", Toast.LENGTH_SHORT).show();
                } else if (index == 1) {
                    Toast.makeText(getContext(), "select 1 mile - 3 miles", Toast.LENGTH_SHORT).show();
                }else if (index == 2) {
                    Toast.makeText(getContext(), "select 3 miles - 5 miles", Toast.LENGTH_SHORT).show();
                }else if (index == 3) {
                    Toast.makeText(getContext(), "select 5 miles+ ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return rootView;
    }
}