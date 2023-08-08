package com.example.myapplication.frontendProvider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapplication.R;
import com.example.myapplication.frontendProvider.ProviderProfileFragment.ProviderProfileFragment;
import com.example.myapplication.frontendProvider.homePages.ProviderServicesFragment;
import com.example.myapplication.frontendProvider.messagePages.ProviderMessageFragment;
import com.example.myapplication.frontendProvider.orderPages.ProviderOrdersFragment;

import java.util.ArrayList;

public class ProviderMain extends AppCompatActivity implements View.OnClickListener {

    ViewPager2 viewPager;
    private LinearLayout llHome, llOrders, llMessage, llProfile;
    private ImageView ivHome, ivOrders, ivMessage, ivProfile, ivCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_main);
        initPager();
        initNaviView();
    }

    // Initialize the viewPager
    private void initPager() {
        viewPager = findViewById(R.id.view_pager);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(ProviderServicesFragment.newInstance("My Services"));
        fragments.add(ProviderOrdersFragment.newInstance("Orders"));
        fragments.add(ProviderMessageFragment.newInstance("Message"));
        fragments.add(ProviderProfileFragment.newInstance("Profile"));
        ProviderMainViewPagerAdaptor pagerAdaptor = new ProviderMainViewPagerAdaptor(getSupportFragmentManager(), getLifecycle(), fragments);
        viewPager.setAdapter(pagerAdaptor);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    //Initialize the navigation bar
    private void initNaviView() {
        llHome = findViewById(R.id.navi_home);
        llHome.setOnClickListener(this);
        llOrders = findViewById(R.id.navi_orders);
        llOrders.setOnClickListener(this);
        llMessage = findViewById(R.id.navi_message);
        llMessage.setOnClickListener(this);
        llProfile = findViewById(R.id.navi_profile);
        llProfile.setOnClickListener(this);
        ivHome = findViewById(R.id.navi_iv_home);
        ivOrders = findViewById(R.id.navi_iv_orders);
        ivMessage = findViewById(R.id.navi_iv_message);
        ivProfile = findViewById(R.id.navi_iv_profile);

        ivHome.setSelected(true);
        ivCurrent = ivHome;
    }

    private void changeTab(int position) {
        ivCurrent.setSelected(false);

        if (position == R.id.navi_home || position == 0) {
            viewPager.setCurrentItem(0);
            ivHome.setSelected(true);
            ivCurrent = ivHome;
        } else if (position == R.id.navi_orders || position == 1) {
            viewPager.setCurrentItem(1);
            ivOrders.setSelected(true);
            ivCurrent = ivOrders;
        } else if (position == R.id.navi_message || position == 2) {
            viewPager.setCurrentItem(2);
            ivMessage.setSelected(true);
            ivCurrent = ivMessage;
        } else if (position == R.id.navi_profile || position == 3) {
            viewPager.setCurrentItem(3);
            ivProfile.setSelected(true);
            ivCurrent = ivProfile;
        }

        //不知道为啥switch不能用，只能用else if了，提示是position是int，而R.id不满足要求
//        switch (position) {
//            case R.id.navi_home:
//                viewPager.setCurrentItem(0);
//            case 0:
//                ivHome.setSelected(true);
//                ivCurrent = ivHome;
//                break;
//            case R.id.navi_orders:
//                viewPager.setCurrentItem(1);
//            case 1:
//                ivOrders.setSelected(true);
//                ivCurrent = ivOrders;
//                break;
//            case R.id.navi_message:
//                viewPager.setCurrentItem(2);
//            case 2:
//                ivMessage.setSelected(true);
//                ivCurrent = ivMessage;
//                break;
//            case R.id.navi_profile:
//                viewPager.setCurrentItem(3);
//            case 3:
//                ivProfile.setSelected(true);
//                ivCurrent = ivProfile;
//                break;
//            default:
//                break;
//        }
    }

    @Override
    public void onClick(View view) {
        changeTab(view.getId());
    }
}