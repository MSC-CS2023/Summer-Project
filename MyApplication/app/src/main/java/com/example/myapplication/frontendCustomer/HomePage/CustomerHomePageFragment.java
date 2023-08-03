package com.example.myapplication.frontendCustomer.HomePage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.frontendCustomer.ItemData;
import com.example.myapplication.frontendCustomer.MyAdapter;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CustomerHomePageFragment extends Fragment {






    public CustomerHomePageFragment() {

    }

    List<ImageButton> buttonList = new ArrayList<>();

    ImageButton button1;
    ImageButton button2;
    ImageButton button3;
    ImageButton button4;
    ImageButton button5;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_customer_home_page, container, false);

//        View ServiceDetail = rootView.findViewById(R.id.service);



//        ServiceDetail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (view.getId() == ServiceDetail.getId()){
//                    startActivity(new Intent(getContext(), CustomerServiceDetailPage.class));
//                }
//            }
//        });



        //5 top icons action here

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


        for (ImageButton Button : buttonList) {
            Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.getId() == Button.getId()){
                        if (Button.getId() == button1.getId()){
                            resetButton();
                            button1.setImageResource(R.drawable.btn_cleaning_aft);
                            // change data imported to listView
                        } else if (Button.getId() == button2.getId()) {
                            resetButton();
                            button2.setImageResource(R.drawable.btn_maintain_aft);
                            // change data imported to listView
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


        //listView down here

        ListView listView = rootView.findViewById(R.id.homepageListView);
        // Create a demo data list
        List<ItemData> demoDataList = new ArrayList<>();

        ItemData itemData1 = new ItemData(R.drawable.btn_avatar1,"Eric","some info balabalabala",R.drawable.btn_emptyheart,R.drawable.img_sample1,"Repair Air conditioner","100","available tomorrow");
        ItemData itemData2 = new ItemData(R.drawable.btn_avatar2,"Alice","some info balabalabala",R.drawable.btn_redheart,R.drawable.img_sample2,"Clean gutter","150","available today");

        demoDataList.add(itemData1);
        demoDataList.add(itemData2);

        // Create an Adapter and set it to the ListView
        MyAdapter myAdapter = new MyAdapter(demoDataList,getContext());
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), "xxxxxx", Toast.LENGTH_SHORT).show();
            }
        });



        return rootView;
    }



    private void resetButton() {
        button1.setImageResource(R.drawable.btn_cleaning);
        button2.setImageResource(R.drawable.btn_maintain);
        button3.setImageResource(R.drawable.btn_laundry);
        button4.setImageResource(R.drawable.btn_landscape);
        button5.setImageResource(R.drawable.btn_more);
    }

}