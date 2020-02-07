package com.appynitty.ghantagaditracker.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appynitty.ghantagaditracker.R;
import com.appynitty.ghantagaditracker.adapter.MapInfoCityPeeWindowAdapter;
import com.appynitty.ghantagaditracker.adapter.MapInfoWindowAdapter;
import com.appynitty.ghantagaditracker.controller.SyncServer;
import com.appynitty.ghantagaditracker.pojo.ActiveUserListPojo;
import com.appynitty.ghantagaditracker.pojo.CityPeeListPojo;
import com.appynitty.ghantagaditracker.utils.AUtils;
import com.appynitty.ghantagaditracker.utils.MyAsyncTask;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.utils.LocaleHelper;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

public class CityPeeOnMapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMapLoadedCallback {

    private LatLngBounds bounds;
    private GoogleMap mGmap;
    private Context context;
    private int ghataGadiCount;
    private LatLng currlatLng;

    private List<CityPeeListPojo> cityPeeListPojos;

    @Override
    protected void attachBaseContext(Context base) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            super.attachBaseContext(LocaleHelper.onAttach(base, AUtils.DEFAULT_LANGUAGE_NAME));
        } else {
            super.attachBaseContext(base);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_pee_on_map);

        context = CityPeeOnMapActivity.this;

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle(getResources().getString(R.string.title_activity_city_pee_map));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initComponents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refresh_menu, menu);
        return true;
    }

    private void initComponents() {
        generateId();
        registerEvents();
        initData();
    }

    private void generateId() {
        ghataGadiCount = 0;
        currlatLng = null;
    }

    private void registerEvents() {

    }

    private void initData() {
        String LatLng = Prefs.getString(AUtils.LOCATION, "");
        if (!LatLng.equals("")) {
            String[] split = LatLng.split(",");
            String lat = split[0];
            String lng = split[1];
            currlatLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGmap = googleMap;

        if (!AUtils.isNull(mGmap)) {
            fetchActiveUserList(null);
        } else {
            AUtils.error(context, "Fatal Error, Unable to load Map");
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh_map) {
            fetchActiveUserList(null);
        } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapLoaded() {
        if (!AUtils.isNull(bounds)) {
            CameraUpdate update = (ghataGadiCount == 1) ? CameraUpdateFactory.newLatLngZoom(bounds.getCenter(), 18f) : CameraUpdateFactory.newLatLngBounds(bounds, 150);
//        mGmap.moveCamera(update);// LatLag,Zoom value
            mGmap.animateCamera(update);
        } else {
            AUtils.error(context, "Fatal Error, Unable to load Map");
        }
    }


    private void plotOnmap() {

        try {
            mGmap.setOnMapLoadedCallback(this);
            mGmap.setInfoWindowAdapter(new MapInfoCityPeeWindowAdapter(this));
            mGmap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    marker.showInfoWindow();
                    return true;
                }
            });

            LatLng latLng = null;
            mGmap.clear();
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (CityPeeListPojo pojo : cityPeeListPojos) {
                if (!AUtils.isNull(pojo)) {

                    latLng = new LatLng(Double.parseDouble(pojo.getLat()), Double.parseDouble(pojo.getLong()));
                }

                Marker marker = mGmap.addMarker(new MarkerOptions()
                        .position(Objects.requireNonNull(latLng))
                        .icon(
                                BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                        .decodeResource(getResources(), R.drawable.ic_marker))
                        )
                        .title(pojo.getSauchalayID())
                        .snippet(pojo.getAddress()));

                marker.setTag(pojo);
                builder.include(latLng);

            }

            bounds = builder.build();

            mGmap.setMapType(GoogleMap.MAP_TYPE_NORMAL); // set the map type

            mGmap.getUiSettings().setZoomControlsEnabled(true);
            mGmap.getUiSettings().setMapToolbarEnabled(false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void noMarkerView() {
        mGmap.clear();
        if (!AUtils.isNull(currlatLng)) {
            mGmap.animateCamera(CameraUpdateFactory.newLatLngZoom(currlatLng, 16f));
        } else
            mGmap.animateCamera(CameraUpdateFactory.zoomTo(15f));
    }


    private void fetchActiveUserList(@Nullable final String areaName) {

        new MyAsyncTask(context, true, new MyAsyncTask.AsynTaskListener() {
            String fetchPref = null;
            Boolean haveData = false;

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {
                haveData = syncServer.getAllCityPee();
                fetchPref = AUtils.PREFS.SBA_ALL_USER_LIST;
            }

            @Override
            public void onFinished() {
                if (haveData) {
                    Type type = new TypeToken<List<CityPeeListPojo>>() {
                    }.getType();
                    cityPeeListPojos = new Gson().fromJson(
                            Prefs.getString(fetchPref, null),
                            type);

                    if (!AUtils.isNull(cityPeeListPojos) && !cityPeeListPojos.isEmpty()) {
                        ghataGadiCount = cityPeeListPojos.size();
                        plotOnmap();
                    } else {
                        noMarkerView();
                        AUtils.info(context, getString(R.string.no_ghanta_gadi));
                    }

                } else {
                    AUtils.error(context, getString(R.string.something_error));
                }
            }
        }).execute();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        initComponents();
    }
}
