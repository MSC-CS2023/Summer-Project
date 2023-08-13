package com.example.myapplication.frontendProvider.profilePages;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProviderProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProviderProfileFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TITLE = "param1";

    // TODO: Rename and change types of parameters
    private String mTitle;
    private View rootView;
    private ImageButton wallet;
    private ImageButton timetable;
    private ImageButton map;
    private ImageButton setting;

    public ProviderProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ProviderProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProviderProfileFragment newInstance(String param1) {
        ProviderProfileFragment fragment = new ProviderProfileFragment();
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
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_provider_profile, container, false);
        }
        initView();
        return rootView;
    }

    private void initView() {
        TextView title = rootView.findViewById(R.id.title);
        title.setText(mTitle);

        wallet = rootView.findViewById(R.id.btn_wallet);
        wallet.setOnClickListener(this);
        timetable = rootView.findViewById(R.id.btn_timetable);
        timetable.setOnClickListener(this);
        map = rootView.findViewById(R.id.btn_map);
        map.setOnClickListener(this);
        setting = rootView.findViewById(R.id.btn_setting);
        setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_wallet) {
            Toast.makeText(getContext(), "Wallet clicked", Toast.LENGTH_SHORT).show();
        } else if(view.getId() == R.id.btn_timetable) {
            Intent intentToTimetable = new Intent(getContext(), ProviderTimetableActivity.class);
            startActivity(intentToTimetable);
        } else if(view.getId() == R.id.btn_map) {
            Intent intentToMap = new Intent(getContext(), ProviderMapActivity.class);
            startActivity(intentToMap);
        } else if(view.getId() == R.id.btn_setting) {
            Toast.makeText(getContext(), "Setting clicked", Toast.LENGTH_SHORT).show();
        }
    }
}