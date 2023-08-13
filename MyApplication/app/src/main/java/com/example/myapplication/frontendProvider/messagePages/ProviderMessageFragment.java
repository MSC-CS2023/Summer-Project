package com.example.myapplication.frontendProvider.messagePages;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.frontendProvider.homePages.ProviderServicesAdaptor;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProviderMessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProviderMessageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TITLE = "param1";

    // TODO: Rename and change types of parameters
    private String mTitle;

    private View rootView;
    private List<ProviderMessageCardData> data = new ArrayList<>();

    public ProviderMessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ProviderMessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProviderMessageFragment newInstance(String param1) {
        ProviderMessageFragment fragment = new ProviderMessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_provider_message, container, false);
        }
        initView();
        return rootView;
    }

    private void initView() {
        TextView title = rootView.findViewById(R.id.title);
        title.setText(mTitle);

        //set data and adaptor
        setAdaptor();

        //click on something
        setClick();
    }

    private void setAdaptor() {
        data.add(new ProviderMessageCardData("btn_avatar2", "Sarah",
                        "This is a latest message, may longer than the width of the screen."));
        data.add(new ProviderMessageCardData("btn_avatar1", "Ben", "Hello."));
        data.add(new ProviderMessageCardData("btn_avatar2", "May", "Okay."));

        RecyclerView recyclerView = rootView.findViewById(R.id.rv_provider_messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ProviderMessageAdaptor providerMessageAdaptor = new ProviderMessageAdaptor(data, getContext());
        recyclerView.setAdapter(providerMessageAdaptor);

        providerMessageAdaptor.setRecyclerItemClickListener(new ProviderServicesAdaptor.OnRecyclerItemClickListener() {
            @Override
            public void onRecyclerItemClick(int position) {
                Toast.makeText(getContext(),"Item" + position + "clicked.", Toast.LENGTH_SHORT).show();
                //需要从当前fragment传数据给消息详情页activity
                Intent intentToMessageDetail = new Intent(getContext(), ProviderMessageDetailActivity.class);
                startActivity(intentToMessageDetail);
            }
        });
    }

    private void setClick() {
    }
}