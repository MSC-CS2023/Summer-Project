package com.example.myapplication.frontendProvider.orderPages;

import android.icu.text.CaseMap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.frontendProvider.homePages.ProviderServicesAdaptor;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProviderOrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProviderOrdersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TEXT = "param1";

    // TODO: Rename and change types of parameters
    private String mTitle;

    private View rootView;

    private List<ProviderOderCardData> data = new ArrayList<>();

    public ProviderOrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ProviderOrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProviderOrdersFragment newInstance(String param1) {
        ProviderOrdersFragment fragment = new ProviderOrdersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_provider_orders, container, false);
        }
        initView();

        return rootView;
    }

    private void initView() {
        TextView title = rootView.findViewById(R.id.title);
        title.setText(mTitle);

        //Set data and adaptor
        setAdaptor();

        //Click on something
        setClick();
    }

    private void setAdaptor() {
        data.add(new ProviderOderCardData("Cleaning", 382789574L, 100D,
                "img_sample2", "Unpaid"));
        data.add(new ProviderOderCardData("Cleaning", 382723474L, 200D,
                "img_sample1", "Unconfirmed"));
        data.add(new ProviderOderCardData("Cleaning", 384389574L, 300D,
                "img_sample2", "Processing"));

        RecyclerView recyclerView = rootView.findViewById(R.id.rv_provider_orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ProviderOdersAdaptor providerOdersAdaptor = new ProviderOdersAdaptor(data, getContext());
        recyclerView.setAdapter(providerOdersAdaptor);

        providerOdersAdaptor.setRecyclerItemClickListener(new ProviderServicesAdaptor.OnRecyclerItemClickListener() {
            @Override
            public void onRecyclerItemClick(int position) {
                Toast.makeText(getContext(),"Item" + position + "clicked.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setClick() {

        //Click on search button to search orders
        ImageButton searchButton =  rootView.findViewById(R.id.btn_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Search services", Toast.LENGTH_SHORT).show();
            }
        });

        //Click on different tabs
        TabLayout tabLayout = rootView.findViewById(R.id.filter_tab);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0: //all
                        Toast.makeText(getContext(), "All clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getContext(), "Unpaid clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getContext(), "Unconfirmed clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getContext(), "Processing clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(getContext(), "Done clicked", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}