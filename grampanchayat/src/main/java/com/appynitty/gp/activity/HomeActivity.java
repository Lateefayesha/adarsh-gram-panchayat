package com.appynitty.gp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.appynitty.gp.R;
import com.appynitty.gp.dialogs.LanguageChangeAlertDialog;
import com.appynitty.gp.fragment.MenuFragment;
import com.appynitty.gp.services.LocationMonitoringService;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.SaveFcmIdAsyncTask;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.utils.LocaleHelper;

/**
 * Created by MiTHUN on 5/2/18.
 */

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    boolean doubleBackToExitPressedOnce;
    private Toolbar toolbar;
    private FragmentManager mFragmentManager;
    private Fragment mFragment = null;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        new SaveFcmIdAsyncTask(this).execute();
        initComponants();
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

    }

    @Override
    protected void attachBaseContext(Context base) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            super.attachBaseContext(LocaleHelper.onAttach(base));
        }else{
            super.attachBaseContext(base);
        }
    }

    public void initComponants() {
        ganrateId();
        registerEvents();
        initActionBar();
    }

    private void ganrateId() {

        mFragmentManager = getSupportFragmentManager();
        Log.e(TAG,this.getResources().getString(R.string.our_gram_panchayat));
        Log.e(TAG,Prefs.getString(AUtils.LANGUAGE_ID, ""));
        loadMenuFragment();
    }

    private void loadMenuFragment() {

        Prefs.putBoolean(AUtils.FCM_NOTI, getIntent().getBooleanExtra(AUtils.FCM_NOTI, false));

        mFragment = new MenuFragment();

        mFragmentManager.popBackStack(null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
        mFragmentManager.beginTransaction()
                .replace(R.id.content_frame, mFragment).commit();
    }

    private void initActionBar() {
        toolbar = findViewById(R.id.toolbar);
//        if (!AUtils.isNullString(QuickUtils.prefs.getString(AUtils.GP_NAME, ""))) {
//
//            setTitle(QuickUtils.prefs.getString(AUtils.GP_NAME, ""));
//        } else {
//
//            setTitle(getResources().getString(R.string.app_name_tab));
//        }

        setSupportActionBar(toolbar);
    }

    public void setTitleIcon(int resId) {
        toolbar.setNavigationIcon(resId);
    }

    public void setTitleActionBar(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    public void setSubtitleActionBar(CharSequence title) {
        getSupportActionBar().setSubtitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (android.R.id.home == item.getItemId()) {
            if (Prefs.getInt(AUtils.FRAGMENT_COUNT, 1) == 0) {
                onBackPressed();
            }
            return true;
        } else if (R.id.action_language == item.getItemId()) {

            changeLanguage();
//            changeLanguageOnClick();
            return true;

        } else if (R.id.action_rate == item.getItemId()) {
            AUtils.rateApp(HomeActivity.this);
            return true;

        } else if (R.id.action_share_app == item.getItemId()) {
            AUtils.shareThisApp(HomeActivity.this, "");
            return true;

        } else if (R.id.action_about_appynitty == item.getItemId()) {
            startActivity(new Intent(HomeActivity.this, AboutAppynittyActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeLanguage(){
        LanguageChangeAlertDialog adapter = new LanguageChangeAlertDialog(this);
        adapter.create();
        adapter.setLanguageSelectListener(new LanguageChangeAlertDialog.LanguageSelectListener() {
            @Override
            public void onLanguageSelect(String selectedLanguage) {
                changeLanguage(selectedLanguage);
                ((Activity)HomeActivity.this).recreate();
            }
        });
    }

    private void changeLanguageOnClick() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        alert.setTitle("Choose Language / भाषा निवडा");
        alert.setTitle("Choose Language / भाषा का चयन करें");

        alert.setPositiveButton("English", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                changeLanguage(AUtils.LanguageConstants.ENGLISH);
                ((Activity)HomeActivity.this).recreate();
            }
        });

//        alert.setNegativeButton("मराठी", new DialogInterface.OnClickListener() {
        alert.setNegativeButton("हिंदी", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                changeLanguage(AUtils.LanguageConstants.MARATHI);
                ((Activity)HomeActivity.this).recreate();
            }
        });
        alert.show();
    }

    public void changeLanguage(String type) {

        AUtils.changeLanguage(this, type);

        initActionBar();

//        MyFragemtV4 myFragemtV4 = (MyFragemtV4) mFragmentManager.findFragmentById(R.id.content_frame);
//        myFragemtV4.updateDataForLanguage();
    }

    private void registerEvents() {

    }

    private void loadFragment() {
        if (mFragment != null) {
//            mFragmentManager.popBackStack(null,
//                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            mFragmentManager.beginTransaction()
//                    .replace(R.id.content_frame, mFragment).commit();
            mFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.content_frame, mFragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
//        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
//            // drawer is open
//            mDrawerLayout.closeDrawers();
//
//        } else {
//        if (getFragmentManager().getBackStackEntryCount() > 0) {
//            // popping backstack
//            getFragmentManager().popBackStack();
//        } else {
//            // nothing on backstack, calling super
////                super.onBackPressed();
//            doubleClickBackPressToExit();
//        }
//        }
        super.onBackPressed();
//        stopLocationUpdates();
    }

//    private void doubleClickBackPressToExit() {
//
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//
//        // Does the user really want to exit?
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, getString(R.string.press_back_again), Toast.LENGTH_LONG).show();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce = false;
//            }
//        }, 2000);
//    }

    @Override
    protected void onResume() {
        super.onResume();

        //Start location sharing service to app server.........
        Intent intent = new Intent(this, LocationMonitoringService.class);
        startService(intent);
    }

    @Override
    public void onDestroy() {

        //Stop location sharing service to app server.........
        stopService(new Intent(this, LocationMonitoringService.class));
        //Ends................................................

        super.onDestroy();
    }

    @Override
    public void recreate() {
        super.recreate();
        AUtils.isRecreate = true;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(AUtils.isRecreate)
            AUtils.isRecreate = false;
    }
}