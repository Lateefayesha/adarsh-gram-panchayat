package com.appynitty.ghantagaditracker.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.appynitty.ghantagaditracker.R;
import com.appynitty.ghantagaditracker.adapter.MapInfoWindowAdapter;
import com.appynitty.ghantagaditracker.controller.SyncServer;
import com.appynitty.ghantagaditracker.pojo.ActiveUserListPojo;
import com.appynitty.ghantagaditracker.pojo.AreaListPojo;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TrackerActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMapLoadedCallback, GoogleMap.OnInfoWindowClickListener {

    private LatLngBounds bounds;
    private GoogleMap mGmap;
    private AutoCompleteTextView searchViewAutoComplete;
    private Context context;
    private int ghataGadiCount;
    private LatLng currlatLng;

    private List<ActiveUserListPojo> activeUserListPojos;
    private List<AreaListPojo> areaListPojos;

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
//        setContentView(R.layout.layout_drawer);
        setContentView(R.layout.activity_tracker);

        context = TrackerActivity.this;

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle(getResources().getString(R.string.title_activity_ghanta_gadi_tracker));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
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
        //    private Spinner areaListSpinner;
        SearchView searchView = findViewById(R.id.search_area);
        ImageView imageViewSearchMag = (ImageView) searchView.findViewById(R.id.search_mag_icon);
        imageViewSearchMag.setImageResource(R.drawable.icn_search_ghanta_gadi);
        searchViewAutoComplete = (AutoCompleteTextView) searchView.findViewById(R.id.search_src_text);
        searchViewAutoComplete.setDropDownBackgroundResource(R.color.colorWhite);
        searchViewAutoComplete.setThreshold(0);
        ghataGadiCount = 0;
        currlatLng = null;
    }

    private void registerEvents() {

        searchViewAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String searchArea = adapterView.getItemAtPosition(i).toString().trim();
                searchViewAutoComplete.setText(searchArea);
                searchViewAutoComplete.setSelection(searchArea.length());
                searchViewAutoComplete.clearFocus();
                fetchActiveUserList(searchArea);
            }
        });

        searchViewAutoComplete.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (searchViewAutoComplete.getText().length() == 0)
                        fetchActiveUserList(null);
                    else
                        fetchActiveUserList(searchViewAutoComplete.getText().toString().trim());

                    searchViewAutoComplete.clearFocus();
                    return true;
                }
                return false;
            }
        });

    }

    private void initData() {
        String LatLng = Prefs.getString(AUtils.LOCATION, "");
        if (!LatLng.equals("")) {
            String[] split = LatLng.split(",");
            String lat = split[0];
            String lng = split[1];
            currlatLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        }

        fetchAreaList(false);
        initSpinner();
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
            fetchAreaList(false);
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

    @Override
    public void onInfoWindowClick(Marker marker) {
        ActiveUserListPojo pojo = (ActiveUserListPojo) marker.getTag();
        String mobileNo = "tel:" + Objects.requireNonNull(pojo).getUserMobile();
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(mobileNo)));
    }

    private void plotOnmap() {

        try {
            mGmap.setOnMapLoadedCallback(this);
            mGmap.setOnInfoWindowClickListener(this);
            mGmap.setInfoWindowAdapter(new MapInfoWindowAdapter(this));
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
            for (ActiveUserListPojo pojo : activeUserListPojos) {
                if (!AUtils.isNull(pojo)) {

                    latLng = new LatLng(Double.parseDouble(pojo.getLatitude()), Double.parseDouble(pojo.getLongitude()));
                }

                Marker marker = mGmap.addMarker(new MarkerOptions()
                        .position(Objects.requireNonNull(latLng))
                        .icon(
                                BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                        .decodeResource(getResources(), R.drawable.ic_marker))
                        )
                        .title(pojo.getVehcileNumber())
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

    private void initSpinner() {
        ArrayList<String> spinnerList = new ArrayList<>();

        if (!AUtils.isNull(areaListPojos)) {
            Type type = new TypeToken<List<AreaListPojo>>() {
            }.getType();
            areaListPojos = new Gson().fromJson(
                    Prefs.getString(AUtils.PREFS.SBA_AREA_LIST, null),
                    type);

            for (AreaListPojo pojo : areaListPojos) {
                spinnerList.add(pojo.getArea());
            }

            ArrayAdapter adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, spinnerList);

            searchViewAutoComplete.setAdapter(adapter);

        } else {
            fetchAreaList(true);
        }
    }

    private void fetchActiveUserList(@Nullable final String areaName) {

        new MyAsyncTask(context, true, new MyAsyncTask.AsynTaskListener() {
            String fetchPref = null;
            Boolean haveData = false;

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {
                if (!AUtils.isNull(areaName)) {
                    haveData = syncServer.getActiveUserAreaWise(areaName);
                    fetchPref = AUtils.PREFS.SBA_USER_LIST;
                } else {
                    haveData = syncServer.getAllActiveUsers();
                    fetchPref = AUtils.PREFS.SBA_ALL_USER_LIST;
                }
            }

            @Override
            public void onFinished() {
                if (haveData) {
                    Type type = new TypeToken<List<ActiveUserListPojo>>() {
                    }.getType();
                    activeUserListPojos = new Gson().fromJson(
                            Prefs.getString(fetchPref, null),
                            type);

                    if (!AUtils.isNull(activeUserListPojos) && !activeUserListPojos.isEmpty()) {
                        ghataGadiCount = activeUserListPojos.size();
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

    private void fetchAreaList(Boolean isProgress) {

        new MyAsyncTask(context, isProgress, new MyAsyncTask.AsynTaskListener() {
            boolean haveData = false;

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {
                haveData = syncServer.getAreaList();
            }

            @Override
            public void onFinished() {
                if (haveData) {

                    Type type = new TypeToken<List<AreaListPojo>>() {
                    }.getType();
                    areaListPojos = new Gson().fromJson(
                            Prefs.getString(AUtils.PREFS.SBA_AREA_LIST, null),
                            type);

                    if (!AUtils.isNull(areaListPojos) && !areaListPojos.isEmpty()) {
                        initSpinner();
                    }
//                    else{
//                        Toasty.error(context, getString(R.string.serverError)).show();
//                    }

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
