package com.appynitty.ghantagaditracker.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
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
import com.mithsoft.lib.componants.Toasty;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import quickutils.core.QuickUtils;

public class TrackerActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMapLoadedCallback, GoogleMap.OnInfoWindowClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "GhantaGadiTracker";
    private LatLngBounds bounds;
    private GoogleMap mGmap;
    private AutoCompleteTextView searchViewAutoComplete;
    private Context context;
    private int ghataGadiCount;
    private LatLng currlatLng;
    private Toolbar toolbar;

    private List<ActiveUserListPojo> activeUserListPojos;
    private List<AreaListPojo> areaListPojos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_drawer);
//        setContentView(R.layout.activity_tracker);

        context = TrackerActivity.this;

        toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle(getResources().getString(R.string.title_activity_ghanta_gadi_tracker));
        setSupportActionBar(toolbar);

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

    private void initComponents(){
        generateId();
        registerEvents();
        initData();
    }

    private void generateId(){
        //    private Spinner areaListSpinner;
        SearchView searchView = findViewById(R.id.search_area);
        ImageView imageViewSearchMag = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        imageViewSearchMag.setImageResource(R.drawable.icn_search_ghanta_gadi);
        searchViewAutoComplete = (AutoCompleteTextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchViewAutoComplete.setDropDownBackgroundResource(R.color.white);
        searchViewAutoComplete.setThreshold(0);
        ghataGadiCount = 0;
        currlatLng = null;

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void registerEvents(){

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
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    if(searchViewAutoComplete.getText().length() == 0)
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

    private void initData(){
        String LatLng = QuickUtils.prefs.getString(AUtils.LOCATION, "");
        if(!LatLng.equals("")) {
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

        if(!AUtils.isNull(mGmap)){
            fetchActiveUserList(null);
        }else{
            Toasty.error(context, "Fatal Error, Unable to load Map").show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.refresh_map){
            fetchActiveUserList(null);
            fetchAreaList(false);
        }else if(item.getItemId() == android.R.id.home){
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
        if(!AUtils.isNull(bounds)){
            CameraUpdate update = (ghataGadiCount == 1)? CameraUpdateFactory.newLatLngZoom(bounds.getCenter(), 18f) : CameraUpdateFactory.newLatLngBounds(bounds, 150);
//        mGmap.moveCamera(update);// LatLag,Zoom value
            mGmap.animateCamera(update);
        }else{
            Toasty.error(context, "Fatal Error, Unable to load Map").show();
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        ActiveUserListPojo pojo = (ActiveUserListPojo) marker.getTag();
        String mobileNo = "tel:"+Objects.requireNonNull(pojo).getUserMobile();
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(mobileNo)));
    }

    private void plotOnmap(){

        try{
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
            for (ActiveUserListPojo pojo : activeUserListPojos){
                if(!AUtils.isNull(pojo)){

                    latLng = new LatLng(Double.parseDouble(pojo.getLatitude()), Double.parseDouble(pojo.getLongitude()));
                }

                Marker marker = mGmap.addMarker(new MarkerOptions()
                        .position(Objects.requireNonNull(latLng))
                        .icon(
                                BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                        .decodeResource(getResources(),R.drawable.ic_marker))
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

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void noMarkerView(){
        mGmap.clear();
        if(!AUtils.isNull(currlatLng)){
            mGmap.animateCamera(CameraUpdateFactory.newLatLngZoom(currlatLng, 16f));
        }else
            mGmap.animateCamera(CameraUpdateFactory.zoomTo(15f));
    }

    private void initSpinner() {
        ArrayList<String> spinnerList = new ArrayList<>();

        if(!AUtils.isNull(areaListPojos)){
            Type type = new TypeToken<List<AreaListPojo>>(){
            }.getType();
            areaListPojos = new Gson().fromJson(
                    QuickUtils.prefs.getString(AUtils.PREFS.SBA_AREA_LIST, null),
                    type);

            for(AreaListPojo pojo: areaListPojos){
                spinnerList.add(pojo.getArea());
            }

            ArrayAdapter adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, spinnerList);

            searchViewAutoComplete.setAdapter(adapter);

        }else{
            fetchAreaList(true);
        }
    }

    private void fetchActiveUserList(@Nullable final String areaName){

        new MyAsyncTask(context, true, new MyAsyncTask.AsynTaskListener() {
            String fetchPref = null;
            Boolean haveData = false;
            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {
                if(!AUtils.isNull(areaName)){
                    haveData = syncServer.getActiveUserAreaWise(areaName);
                    fetchPref = AUtils.PREFS.SBA_USER_LIST;
                }else{
                    haveData = syncServer.getAllActiveUsers();
                    fetchPref = AUtils.PREFS.SBA_ALL_USER_LIST;
                }
            }

            @Override
            public void onFinished() {
                if(haveData){
                    Type type = new TypeToken<List<ActiveUserListPojo>>(){
                    }.getType();
                    activeUserListPojos = new Gson().fromJson(
                            QuickUtils.prefs.getString(fetchPref, null),
                            type);

                    if(!AUtils.isNull(activeUserListPojos) && !activeUserListPojos.isEmpty()){
                        ghataGadiCount = activeUserListPojos.size();
                        plotOnmap();
                    }else{
                        noMarkerView();
                        Toasty.info(context, getString(R.string.no_ghanta_gadi)).show();
                    }

                }else{
                    Toasty.error(context, getString(R.string.something_error)).show();
                }
            }
        }).execute();
    }

    private void fetchAreaList(Boolean isProgress){

        new MyAsyncTask(context, isProgress, new MyAsyncTask.AsynTaskListener() {
            boolean haveData = false;
            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {
                haveData = syncServer.getAreaList();
            }

            @Override
            public void onFinished() {
                if(haveData){

                    Type type = new TypeToken<List<AreaListPojo>>(){
                    }.getType();
                    areaListPojos = new Gson().fromJson(
                            QuickUtils.prefs.getString(AUtils.PREFS.SBA_AREA_LIST, null),
                            type);

                    if(!AUtils.isNull(areaListPojos) && !areaListPojos.isEmpty()){
                        initSpinner();
                    }else{
                        Toasty.error(context, getString(R.string.serverError)).show();
                    }

                }else{
                    Toasty.error(context, getString(R.string.something_error)).show();
                }
            }
        }).execute();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.nav_complaint_cleaning){
            startActivity(new Intent(context, CleaningComplaintActivity.class));
        }else if(item.getItemId() == R.id.nav_complaint_status){
            startActivity(new Intent(context, ComplaintStatusActivity.class));
        }else if(item.getItemId() == R.id.nav_logout){
            startActivity(new Intent(context, RegistrationActivity.class));
            ((Activity)context).finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}