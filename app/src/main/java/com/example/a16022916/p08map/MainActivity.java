package com.example.a16022916.p08map;


import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    TextView tvDesc;
    Spinner spnLoc;
    private GoogleMap map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spnLoc = findViewById(R.id.spnLocation);
        tvDesc = findViewById(R.id.tvDesc);

        tvDesc.setText("ABC Pte Ltd\n\nWe now have 3 branches. Look below for the address and info");
        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                LatLng poi_Central = new LatLng(1.3168793190249464,103.83624329672955);
                LatLng poi_east = new LatLng(1.3450241892384858,103.9763189803233);
                LatLng poi_north = new LatLng(1.44224, 103.785733); //Lat : Lng


                Marker cp = map.addMarker(new MarkerOptions().position(poi_Central).title("Central").snippet("Block 3A, Orchard Ave 3, 134542 \n" +
                        "Operating hours: 11am-8pm\n" +
                        "Tel:67788652\n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));


                Marker ep = map.addMarker(new MarkerOptions().position(poi_east).title("East").snippet("Block " +
                        "555, Tampines Ave 3, 287788 \n" +
                        "Operating hours: 9am-5pm\n" +
                        "Tel:66776677\n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));


                Marker rp = map.addMarker(new
                        MarkerOptions()
                        .position(poi_north)
                        .title("HQ-North")
                        .snippet("Block 333, Admiralty Ave 3, 765654 Operating hours: 10am-5pm\n" +
                                "Tel:65433456\n")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));

                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        String title = marker.getTitle();
                        Toast.makeText(MainActivity.this, title, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Central,15));
//                map.moveCamera(CameraUpdateFactory.zoomIn());

                UiSettings ui = map.getUiSettings();
                ui.setZoomControlsEnabled(true);
                ui.setCompassEnabled(true);

                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

                if(permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                    return;
                }
            }
        });
        spnLoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (map != null) {
                    if (i == 0) {
                        LatLng poi_north = new LatLng(1.44224, 103.785733); //Lat : Lng
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_north, 15));
                    } else if (i == 1) {
                        LatLng poi_Central = new LatLng(1.3168793190249464, 103.83624329672955);
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Central, 15));
                    } else if (i == 2) {
                        LatLng poi_east = new LatLng(1.3450241892384858, 103.9763189803233);
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_east, 15));
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(MainActivity.this, "HELLOOO", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the read SMS
                    //  as if the btnRetrieve is clicked
                    int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

                    if(permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                        map.setMyLocationEnabled(true);
                    }

                } else {
                    // permission denied... notify user
                    Toast.makeText(MainActivity.this, "Permission not grant-ed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}
