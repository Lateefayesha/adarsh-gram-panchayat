package com.appynitty.gp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.appynitty.gp.R;
import com.appynitty.gp.utils.AUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import quickutils.core.QuickUtils;

/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
public class MapsMarkerActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps_marker);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.

        LatLng latLng = null;

        if (!AUtils.isNullString(QuickUtils.prefs.getString(AUtils.APP_LOCATION, ""))) {

            String[] split = QuickUtils.prefs.getString(AUtils.APP_LOCATION, "").split(",");
            String lat = split[0];
            String log = split[1];

            if (!AUtils.isNullString(lat) && !AUtils.isNullString(log)) {

                double lat_D = Double.parseDouble(lat);
                double log_D = Double.parseDouble(log);

                latLng = new LatLng(lat_D, log_D);
            }

        }

        googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(QuickUtils.prefs.getString(AUtils.GP_NAME_MAR, ""))
                .snippet(QuickUtils.prefs.getString(AUtils.GP_DETAILS, "")));
//                .icon(icon));//title to the marker

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));// LatLag,Zoom value
//        googleMap.animateCamera(CameraUpdateFactory.zoomBy(15));// set the zoom in map value
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN); // set the map type

//        googleMap.getUiSettings().setZoomControlsEnabled(false);

    }
}
