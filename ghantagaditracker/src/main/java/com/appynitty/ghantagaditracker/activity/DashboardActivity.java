package com.appynitty.ghantagaditracker.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.gamemenulibrary.activities.MenuActivity;
import com.appynitty.ghantagaditracker.R;
import com.appynitty.ghantagaditracker.adapter.InflateLocalMenu;
import com.appynitty.ghantagaditracker.adapter.LogoutAdapterClass;
import com.appynitty.ghantagaditracker.controller.Notification;
import com.appynitty.ghantagaditracker.pojo.LocalMenuPojo;
import com.appynitty.ghantagaditracker.pojo.RegistrationDetailsPojo;
import com.appynitty.ghantagaditracker.utils.AUtils;
import com.appynitty.ghantagaditracker.utils.DatabaseHelper;
import com.appynitty.ghantagaditracker.utils.SaveFcmIdAsyncTask;
import com.google.android.material.navigation.NavigationView;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.custom_component.MyProgressDialog;
import com.riaylibrary.utils.LocaleHelper;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_CODE_SETTING = 1023;

    private InflateLocalMenu localMenu;
    private RecyclerView localMenuRecycler;
    private Context mContext;
    private NavigationView navigationView;
    private LogoutAdapterClass logoutAdapter;
    private MyProgressDialog progressDialog;

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

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        localMenu = new InflateLocalMenu(mContext);
        localMenuRecycler = findViewById(R.id.menu_recycler);
        localMenuRecycler.setLayoutManager(new GridLayoutManager(mContext, 2));

        logoutAdapter = new LogoutAdapterClass();
        progressDialog = new MyProgressDialog(mContext, R.drawable.progress_bar, false);

    }

    private void registerEvents() {
        localMenu.setMenuClickListener(new InflateLocalMenu.LocalMenuClickListener() {
            @Override
            public void onLocalMenuItemClick(LocalMenuPojo menuPojo) {
                openMenuPages(menuPojo.getMenuId());
            }
        });

        logoutAdapter.setLogoutAdapterListner(new LogoutAdapterClass.LogoutAdapterListner() {
            @Override
            public void onSuccessCallback(RegistrationDetailsPojo pojo) {
                progressDialog.dismiss();
                if (!AUtils.isNull(pojo)) {
                    if (pojo.getStatus().equals(AUtils.STATUS_SUCCESS)) {
                        logoutRequestComplete();
                    } else if (Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_NAME).equals(AUtils.LanguageConstants.ENGLISH))
                        AUtils.info(mContext, pojo.getMessage());
                    else
                        AUtils.info(mContext, pojo.getMessageMar());

                } else
                    AUtils.error(mContext, getResources().getString(R.string.something_error));
            }

            @Override
            public void onFailureCallback() {
                progressDialog.dismiss();
                AUtils.error(mContext, getResources().getString(R.string.something_error));
            }

            @Override
            public void onErrorCallback() {
                progressDialog.dismiss();
                AUtils.error(mContext, getResources().getString(R.string.serverError));
            }
        });
    }

    private void initData() {
        createMenuItem();

        Intent intent = getIntent();
        if (intent.hasExtra(AUtils.FCM_NOTI) && intent.getBooleanExtra(AUtils.FCM_NOTI, false)) {
            String notiType = intent.getStringExtra(AUtils.FCM_NOTI_TYPE);
            intent.removeExtra(AUtils.FCM_NOTI);
            intent.removeExtra(AUtils.FCM_NOTI_TYPE);
            openNotificationActivity(notiType);
        }

        saveFCM();
    }

    private void createMenuItem() {

        Menu menu = navigationView.getMenu();
        MenuItem menuItem = null;

        List<LocalMenuPojo> pojoList = new ArrayList<>();

        if (menu.findItem(R.id.nav_ghanta_gadi_tracker).isVisible()) {
            pojoList.add(new LocalMenuPojo(R.id.nav_ghanta_gadi_tracker,
                    getResources().getString(R.string.title_activity_ghanta_gadi_tracker),
                    AUtils.Colour.Green, ContextCompat.getDrawable(mContext, R.drawable.ic_tracker)));
        }

        if (menu.findItem(R.id.nav_complaint_cleaning).isVisible()) {
            pojoList.add(new LocalMenuPojo(R.id.nav_complaint_cleaning,
                    getResources().getString(R.string.title_activity_cleaning_complaint),
                    AUtils.Colour.Yellow, ContextCompat.getDrawable(mContext, R.drawable.ic_cleaning_complaint)));
        }

        if (menu.findItem(R.id.nav_complaint_status).isVisible()) {
            pojoList.add(new LocalMenuPojo(R.id.nav_complaint_status,
                    getResources().getString(R.string.title_activity_complaint_status),
                    AUtils.Colour.DarkGray, ContextCompat.getDrawable(mContext, R.drawable.ic_my_grievance)));
        }

        menuItem = menu.findItem(R.id.nav_collection_history);
        if (!Prefs.getString(AUtils.PREFS.REFERENCE_ID, "").isEmpty() && menuItem.isVisible()) {
            menuItem.setVisible(true);
            pojoList.add(new LocalMenuPojo(R.id.nav_collection_history,
                    getResources().getString(R.string.title_activity_collection_history),
                    AUtils.Colour.Blue, ContextCompat.getDrawable(mContext, R.drawable.ic_history)));
        }
        menuItem = null;

        if (menu.findItem(R.id.nav_league).isVisible()) {
            pojoList.add(new LocalMenuPojo(R.id.nav_league,
                    getResources().getString(R.string.title_activity_league_questions),
                    AUtils.Colour.Red, ContextCompat.getDrawable(mContext, R.drawable.ic_ss_league)));
        }

        if (menu.findItem(R.id.nav_city_pee).isVisible()) {
            pojoList.add(new LocalMenuPojo(R.id.nav_city_pee,
                    getResources().getString(R.string.title_activity_city_pee),
                    AUtils.Colour.Pink, ContextCompat.getDrawable(mContext, R.drawable.ic_ct_pt)));
        }

        MenuItem infotainment = menu.findItem(R.id.nav_infotainment);
        if (infotainment.isVisible()) {
            pojoList.add(new LocalMenuPojo(infotainment.getItemId(),
                    (String) infotainment.getTitle(),
                    AUtils.Colour.Orange, ContextCompat.getDrawable(mContext, R.drawable.ic_infotainment)));
        }


        if (menu.findItem(R.id.nav_notification).isVisible()) {
            pojoList.add(new LocalMenuPojo(R.id.nav_notification,
                    getResources().getString(R.string.title_activity_notification_list),
                    AUtils.Colour.SkyBlue, ContextCompat.getDrawable(mContext, R.drawable.ic_notification_svg)));
        }

        inflateMenuGrid(pojoList);
    }

    private void inflateMenuGrid(List<LocalMenuPojo> pojoList) {
        localMenu.setMenuPojoList(pojoList);
        localMenuRecycler.setAdapter(localMenu);
    }

    private void openNotificationActivity(@Nullable String type) {
        if (type != null) {
            switch (type) {
                case Notification.TYPE_GG:
                    if (!Prefs.getString(AUtils.PREFS.REFERENCE_ID, "").isEmpty())
                        startActivity(new Intent(mContext, CollectionHistoryActivity.class));
                    else
                        startNotificationActivity();
                    break;
                case Notification.TYPE_GP:
                    startActivity(new Intent(mContext, ComplaintStatusActivity.class));
                    break;
                case Notification.TYPE_GGB:
                    startNotificationActivity();
                    break;
            }
        } else
            startNotificationActivity();
    }

    private void startNotificationActivity() {
        startActivity(new Intent(mContext, NotificationListActivity.class));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        openMenuPages(item.getItemId());
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openMenuPages(int itemId) {
        if (itemId == R.id.nav_ghanta_gadi_tracker)
            startActivity(new Intent(mContext, TrackerActivity.class));
        else if (itemId == R.id.nav_complaint_cleaning) {
            startActivity(new Intent(mContext, CleaningComplaintActivity.class));
        } else if (itemId == R.id.nav_complaint_status) {
            startActivity(new Intent(mContext, ComplaintStatusActivity.class));
        } else if (itemId == R.id.nav_logout) {
            performLogout();
        } else if (itemId == R.id.nav_setting) {
            Intent intent = new Intent(mContext, SettingActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SETTING);
        } else if (itemId == R.id.nav_notification)
            openNotificationActivity(null);
        else if (itemId == R.id.nav_league)
            startActivity(new Intent(mContext, LeagueQuestionsActivity.class));
        else if (itemId == R.id.nav_city_pee)
            startActivity(new Intent(mContext, CityPeeActivity.class));
        else if (itemId == R.id.nav_collection_history)
            startActivity(new Intent(mContext, CollectionHistoryActivity.class));
        else if (itemId == R.id.nav_infotainment)
            startActivity(new Intent(mContext, MenuActivity.class));

    }

    private void performLogout() {
        if (!Prefs.getString(AUtils.PREFS.REFERENCE_ID, "").isEmpty()) {
            progressDialog.show();
            logoutAdapter.callLogoutApi();
        } else
            logoutRequestComplete();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SETTING && resultCode == RESULT_OK) {
            ((Activity) mContext).recreate();
        }
    }

    private void logoutRequestComplete() {
        Prefs.remove(AUtils.PREFS.IS_USER_LOGIN);
        Prefs.remove(AUtils.PREFS.REFERENCE_ID);
        Prefs.remove(AUtils.PREFS.USER_MOBILE_NUMBER);
        DatabaseHelper db = new DatabaseHelper(mContext);
        db.deleteAllNotification();
        startActivity(new Intent(mContext, RegistrationActivity.class));
        ((Activity) mContext).finish();
    }

    private void saveFCM() {
        new SaveFcmIdAsyncTask(mContext).execute();
    }

    @Override
    public void recreate() {
        super.recreate();
        startActivity(new Intent(mContext, mContext.getClass()));
        ((Activity) mContext).finish();
    }

}
