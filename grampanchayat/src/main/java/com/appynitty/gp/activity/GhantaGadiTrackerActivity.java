package com.appynitty.gp.activity;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.appynitty.gp.R;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.LocaleHelper;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Objects;

import quickutils.core.QuickUtils;

/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
public class GhantaGadiTrackerActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMapLoadedCallback, GoogleMap.OnInfoWindowClickListener {

    private static final String TAG = "GhantaGadiTracker";
    private LatLngBounds bounds;
    private GoogleMap mGmap;

    @Override
    protected void attachBaseContext(Context base) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            super.attachBaseContext(LocaleHelper.onAttach(base));
        }else{
            super.attachBaseContext(base);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_ghanta_gadi_tracker);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.title_activity_ghanta_gadi_tracker));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try{
            mGmap = googleMap;
            mGmap.setOnMapLoadedCallback(this);
            mGmap.setOnInfoWindowClickListener(this);

            LatLng latLng = null;
            String latiLngi = QuickUtils.prefs.getString(AUtils.APP_LOCATION, "");

            ArrayList<String> arr = new ArrayList<>();

            arr.add("20.50,78.50");
            arr.add("20.90,78.90");
            arr.add("21.00,79.00");
            arr.add("21.50,79.50");
            arr.add("21.90,79.90");

            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            for (String latlng : arr){
                if(!latlng.equals("")){
                    String[] split = latlng.split(",");
                    String lat = split[0];
                    String lng = split[1];

                    latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                }else{
                    latLng = new LatLng(Double.parseDouble("21.00"), Double.parseDouble("79.00"));
                }


                mGmap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .icon(
                            BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                .decodeResource(getResources(),R.drawable.ic_truck_location))
                        )
                        .title(latlng)
                        .snippet("This is for testing purpose"));

                builder.include(latLng);

            }

            bounds = builder.build();

            mGmap.setMapType(GoogleMap.MAP_TYPE_NORMAL); // set the map type

            mGmap.getUiSettings().setZoomControlsEnabled(true);
            mGmap.getUiSettings().setMapToolbarEnabled(false);

        }catch (Exception e){
            e.printStackTrace();
        }

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        AUtils.changeLanguage(this, Integer.parseInt(QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID)));
        super.onDestroy();
    }

    @Override
    public void onMapLoaded() {
        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, 100);
//        mGmap.moveCamera(update);// LatLag,Zoom value
        mGmap.animateCamera(update);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.d(TAG, "onInfoWindowClick: ");
    }
}
