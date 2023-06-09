package com.example.flower;

import androidx.appcompat.app.AppCompatActivity;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {



    public static final int REQUEST_LOCATION_CODE = 99;
    private GoogleMap gm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        createMap();
    }
    private void createMap() {
        SupportMapFragment smf = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        smf.getMapAsync(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        createMap();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng hcm = new LatLng(10.762622, 106.660172);
        gm = googleMap;
        gm.addMarker(new MarkerOptions().position(hcm).title("Marker in HCM City"));
        CameraPosition cp = new CameraPosition.Builder().target(hcm).zoom(13).build();
        gm.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
    }
}
