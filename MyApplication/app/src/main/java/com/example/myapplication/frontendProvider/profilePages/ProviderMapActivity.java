package com.example.myapplication.frontendProvider.profilePages;

import android.Manifest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.Bean.Httpdata.HttpBaseBean;
import com.example.myapplication.Bean.Httpdata.Order;
import com.example.myapplication.Bean.Httpdata.data.OrderListData;
import com.example.myapplication.R;

//for map
import com.example.myapplication.network.ProviderApi;
import com.example.myapplication.network.RetrofitClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.checkerframework.checker.units.qual.C;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class ProviderMapActivity extends AppCompatActivity implements OnMapReadyCallback{

    private String token;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int PERMISSION_REQUEST_CODE = 1234;
    private Toolbar toolbar;
    private List<String> customerAddresses = new ArrayList<>();
    private List<String> message = new ArrayList<>();
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_map);

        SharedPreferences sp = getSharedPreferences("ConfigSp", Context.MODE_PRIVATE);
        this.token = sp.getString("token", "");


        // 检查并请求定位权限
        if (ContextCompat.checkSelfPermission(this, FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }

        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
        initMap();
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setCompassEnabled(true);
        ProviderApi providerApi = RetrofitClient.getInstance().getService(ProviderApi.class);
        providerApi.getProviderProcessingOrders(token, 0, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<HttpBaseBean<OrderListData>>() {
                    @Override
                    public void onNext(HttpBaseBean<OrderListData> orderListDataHttpBaseBean) {
                        if(orderListDataHttpBaseBean.getSuccess()){
                            customerAddresses = new ArrayList<>();
                            message = new ArrayList<>();
                            for(Order order : orderListDataHttpBaseBean.getData().getBookingOrders()){
                                customerAddresses.add(order.getAddress());
                                message.add(getTime(order.getStartTimestamp()));
                            }
                            Geocoder geocoder = new Geocoder(getApplicationContext());

                            for(int i = 0; i < customerAddresses.size(); i++) {
                                customerAddresses.get(i).concat(", United Kingdom");
                                try {
                                    List<Address> addresses = geocoder.getFromLocationName(customerAddresses.get(i), 1);
                                    if(addresses != null && !addresses.isEmpty()) {
                                        Address targetAddress = addresses.get(0);
                                        latLng = new com.google.android.gms.maps.model.LatLng(targetAddress.getLatitude(),
                                                targetAddress.getLongitude());
                                        googleMap.addMarker(new MarkerOptions()
                                                .position(latLng)
                                                .title(message.get(i)));
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                googleMap.setMyLocationEnabled(true);
                            }

                            float zoomLevel = 12.0f;
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "Network error! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
//        Geocoder geocoder = new Geocoder(this);
//
//        //拉取所有processing状态下的订单来获取各个customer的地址
//        createDemoAddresses();
//        for(int i = 0; i < customerAddresses.size(); i++) {
//            customerAddresses.get(i).concat(", United Kingdom");
//            try {
//                List<Address> addresses = geocoder.getFromLocationName(customerAddresses.get(i), 1);
//                if(addresses != null && !addresses.isEmpty()) {
//                    Address targetAddress = addresses.get(0);
//                    latLng = new com.google.android.gms.maps.model.LatLng(targetAddress.getLatitude(),
//                            targetAddress.getLongitude());
//                    googleMap.addMarker(new MarkerOptions()
//                            .position(latLng)
//                            .title( (i+1) + " Order in processing\n" + "Consumer name\n" + "Slot time"));
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            googleMap.setMyLocationEnabled(true);
//        }
//
//        float zoomLevel = 12.0f;
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));

    }

    private void createDemoAddresses() {
        customerAddresses.add("as3 1R2");
        customerAddresses.add("BS2 8HW");
        customerAddresses.add("BS1 1JQ");
        customerAddresses.add("BS1 3DW");
        customerAddresses.add("BS2 9XP");

//        LatLng bristol = new LatLng(51.455, -2.588);
//        googleMap.addMarker(new MarkerOptions()
//                .position(bristol)
//                .title("Marker in Bristol"));
//        float zoomLevel = 15.0f;
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bristol, zoomLevel));

//        String providerAddress = "Dean Street Works, Dean Street, Bristol, BS2 8SF";
    }

    private String getTime(Long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.setTimeInMillis(time);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return year + "/" + month + "/" + day + "/" + hour + ":" + (minute < 10 ? "0" + minute : minute);
    }
}
