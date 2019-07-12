package com.appynitty.ghantagaditracker.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.appynitty.ghantagaditracker.R;
import com.appynitty.ghantagaditracker.adapter.InflateLocalMenu;
import com.appynitty.ghantagaditracker.controller.Notification;
import com.appynitty.ghantagaditracker.pojo.LocalMenuPojo;
import com.appynitty.ghantagaditracker.utils.AUtils;
import com.google.android.gms.nearby.connection.Payload;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import quickutils.core.QuickUtils;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_CODE_SETTING = 1023;

    private InflateLocalMenu localMenu;
    private RecyclerView localMenuRecycler;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();
    }

    private void initComponent() {
        generateId();
        initData();
        registerEvents();
    }

    private void generateId() {
//        setContentView(R.layout.activity_dashboard);
        setContentView(R.layout.layout_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);

        mContext = DashboardActivity.this;

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        localMenu = new InflateLocalMenu(mContext);
        localMenuRecycler = findViewById(R.id.menu_recycler);
        localMenuRecycler.setLayoutManager(new GridLayoutManager(mContext, 2));

    }

    private void registerEvents() {
        localMenu.setMenuClickListener(new InflateLocalMenu.LocalMenuClickListener() {
            @Override
            public void onLocalMenuItemClick(LocalMenuPojo menuPojo) {
                openMenuPages(menuPojo.getMenuId());
            }
        });
    }

    private void initData() {
        createMenuItem();

        Intent intent = getIntent();
        if(intent.hasExtra(AUtils.FCM_NOTI) && intent.getBooleanExtra(AUtils.FCM_NOTI, false) ){
            String notiType = intent.getStringExtra(AUtils.FCM_NOTI_TYPE);
            intent.removeExtra(AUtils.FCM_NOTI);
            intent.removeExtra(AUtils.FCM_NOTI_TYPE);
            openNotificationActivity(notiType);
        }
    }

    private void createMenuItem() {
        List<LocalMenuPojo> pojoList = new ArrayList<>();

        pojoList.add(new LocalMenuPojo(R.id.nav_ghanta_gadi_tracker,
                getResources().getString(R.string.title_activity_ghanta_gadi_tracker),
                AUtils.Colour.Green));
        pojoList.add(new LocalMenuPojo(R.id.nav_complaint_cleaning,
                getResources().getString(R.string.title_activity_cleaning_complaint),
                AUtils.Colour.Yellow));

        pojoList.add(new LocalMenuPojo(R.id.nav_complaint_status,
                getResources().getString(R.string.title_activity_complaint_status),
                AUtils.Colour.Blue));
        pojoList.add(new LocalMenuPojo(R.id.nav_league,
                getResources().getString(R.string.title_activity_league_questions),
                AUtils.Colour.Red));

        pojoList.add(new LocalMenuPojo(R.id.nav_city_pee,
                getResources().getString(R.string.title_activity_city_pee),
                AUtils.Colour.Pink));
        pojoList.add(new LocalMenuPojo(R.id.nav_notification,
                getResources().getString(R.string.title_activity_notification_list),
                AUtils.Colour.SkyBlue));

        inflateMenuGrid(pojoList);
    }

    private void inflateMenuGrid(List<LocalMenuPojo> pojoList) {
        localMenu.setMenuPojoList(pojoList);
        localMenuRecycler.setAdapter(localMenu);
    }

    private void openNotificationActivity(String type){
        switch (type){
            case Notification.TYPE_GG:
                startActivity(new Intent(mContext, NotificationListActivity.class));
                break;
            case Notification.TYPE_GP:
                startActivity(new Intent(mContext, ComplaintStatusActivity.class));
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        openMenuPages(item.getItemId());
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openMenuPages(int itemId){
        if(itemId == R.id.nav_ghanta_gadi_tracker)
            startActivity(new Intent(mContext, TrackerActivity.class));
        else if(itemId == R.id.nav_complaint_cleaning){
            startActivity(new Intent(mContext, CleaningComplaintActivity.class));
        }else if(itemId == R.id.nav_complaint_status){
            startActivity(new Intent(mContext, ComplaintStatusActivity.class));
        }else if(itemId == R.id.nav_logout){
            QuickUtils.prefs.remove(AUtils.PREFS.IS_USER_LOGIN);
            QuickUtils.prefs.remove(AUtils.PREFS.REFERENCE_ID);
            startActivity(new Intent(mContext, RegistrationActivity.class));
            ((Activity)mContext).finish();
        }else if(itemId == R.id.nav_setting){
            Intent intent = new Intent(mContext, SettingActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SETTING);
        }else if(itemId == R.id.nav_notification)
            openNotificationActivity(Notification.TYPE_GG);
        else if(itemId == R.id.nav_league)
            startActivity(new Intent(mContext, LeagueQuestionsActivity.class));
        else if(itemId == R.id.nav_city_pee)
            startActivity(new Intent(mContext, CityPeeActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SETTING && resultCode == RESULT_OK){
            ((Activity)mContext).recreate();
        }
    }

    @Override
    public void recreate() {
        super.recreate();
        startActivity(new Intent(mContext, mContext.getClass()));
        ((Activity)mContext).finish();
    }
}
