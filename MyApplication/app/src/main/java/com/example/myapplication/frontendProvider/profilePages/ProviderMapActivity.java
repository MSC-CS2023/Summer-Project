package com.example.myapplication.frontendProvider.profilePages;

import android.Manifest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.example.myapplication.R;

//for map
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProviderMapActivity extends AppCompatActivity implements OnMapReadyCallback{

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int PERMISSION_REQUEST_CODE = 1234;
    private Toolbar toolbar;
    private List<String> customerAddresses = new ArrayList<>();
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_map);

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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setCompassEnabled(true);

        Geocoder geocoder = new Geocoder(this);

        //拉取所有processing状态下的订单来获取各个customer的地址
        createDemoAddresses();
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
                            .title( (i+1) + " Order in processing\n" + "Consumer name\n" + "Slot time"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        }

        float zoomLevel = 12.0f;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));

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
}
