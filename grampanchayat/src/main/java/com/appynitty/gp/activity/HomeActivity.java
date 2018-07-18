package com.appynitty.gp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.appynitty.gp.R;
import com.appynitty.gp.fragment.AboutAppynittyFragment;
import com.appynitty.gp.fragment.MenuFragment;
import com.appynitty.gp.services.LocationMonitoringService;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyFragemtV4;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 5/2/18.
 */

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    boolean doubleBackToExitPressedOnce;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FragmentManager mFragmentManager;
    private Fragment mFragment = null;
    private TextView navigationUserName;
    private TextView initialWordNavTextView;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        initComponants();
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // initialize the necessary libraries
//        initLocationLibery();

        //    startLocationButtonClick();
    }

    public void initComponants() {
        ganrateId();
//        initNavigationDrawer();
        registerEvents();
        initActionBar();
    }

    private void ganrateId() {

        mFragmentManager = getSupportFragmentManager();
        loadMenuFragment();
    }

    private void loadMenuFragment() {

        mFragment = new MenuFragment().newInstance();
        mFragmentManager.popBackStack(null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
        mFragmentManager.beginTransaction()
                .replace(R.id.content_frame, mFragment).commit();

    }

    private void initNavigationDrawer() {

        mDrawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);

        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header);
        initialWordNavTextView = headerLayout.findViewById(R.id.initialWordNavTextView);
        initialWordNavTextView.setText("M");
        navigationUserName = headerLayout.findViewById(R.id.navigationUserName);
        navigationUserName.setText("Mithun Halmare");
        TextView navigationUserId = headerLayout.findViewById(R.id.navigationUserId);
        navigationUserId.setText("Software Engineer");
    }

    private void initActionBar() {
        toolbar = findViewById(R.id.toolbar);
//        TextView title = findViewById(R.id.toolbar_title);
        setTitle(getResources().getString(R.string.app_name_tab));
        setSupportActionBar(toolbar);
//        title.setText(getResources().getString(R.string.app_name));
//        final ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_icn_profile);
//        actionBar.setDisplayHomeAsUpEnabled(true);
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
            if (QuickUtils.prefs.getInt(AUtils.FRAGMENT_COUNT, 1) == 0) {
                onBackPressed();
            }
            return true;
        } else if (R.id.action_language == item.getItemId()) {

            settingOnClick();
            return true;

        } else if (R.id.action_rate == item.getItemId()) {
            AUtils.rateApp(HomeActivity.this);
            return true;

        } else if (R.id.action_share_app == item.getItemId()) {
            AUtils.shareThisApp(HomeActivity.this, "");
            return true;

        } else if (R.id.action_about_appynitty == item.getItemId()) {
            mFragment = AboutAppynittyFragment.newInstance();
            loadFragment();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void settingOnClick() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Choose Language / भाषा निवडा");

        alert.setPositiveButton("English", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                changeLanguage(1);
                QuickUtils.prefs.save(AUtils.LANGUAGE_ID, "1");
            }
        });
        alert.setNegativeButton("मराठी", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                changeLanguage(2);
                QuickUtils.prefs.save(AUtils.LANGUAGE_ID, "2");
            }
        });
        alert.show();
    }

    public void changeLanguage(int type) {

        AUtils.changeLanguage(this, type);

        MyFragemtV4 myFragemtV4 = (MyFragemtV4) mFragmentManager.findFragmentById(R.id.content_frame);
        myFragemtV4.updateDataForLanguage();
    }

    private void registerEvents() {

//        navigationView.setNavigationItemSelectedListener(
//                new NavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(MenuItem menuItem) {
//
//                        mDrawerLayout.closeDrawers();
//                        mFragment = null;
//
//                        switch (menuItem.getItemId()) {
//                            case R.id.nav_the_person:
//                                mFragment = new ThePersonFragment().newInstance();
//                                break;
//                            case R.id.nav_videos:
//                                mFragment = new VideosFragment().newInstance();
//                                break;
//                            case R.id.nav_books:
//                                mFragment = new BooksFragment().newInstance();
//                                break;
//                            case R.id.nav_download:
//                                mFragment = new FreeDownloadFragment().newInstance();
//                                break;
////                            case R.id.nav_more_images:
////                                break;
////                            case R.id.nav_more_app:
////                                break;
//                        }
//                        loadFragment();
//                        return true;
//                    }
//                });
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

    private void doubleClickBackPressToExit() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        // Does the user really want to exit?
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.press_back_again), Toast.LENGTH_LONG).show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
//
//
//    // location updates interval - 10sec
//    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
//
//    // fastest updates interval - 5 sec
//    // location updates will be received if another app is requesting the locations
//    // than your app can handle
//    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
//
//    private static final int REQUEST_CHECK_SETTINGS = 100;
//
//
//    // bunch of location related apis
//    private FusedLocationProviderClient mFusedLocationClient;
//    private SettingsClient mSettingsClient;
//    private LocationRequest mLocationRequest;
//    private LocationSettingsRequest mLocationSettingsRequest;
//    private LocationCallback mLocationCallback;
//    private Location mCurrentLocation;
//
//
//    private void initLocationLibery() {
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        mSettingsClient = LocationServices.getSettingsClient(this);
//
//        mLocationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                super.onLocationResult(locationResult);
//                // location is received
//                mCurrentLocation = locationResult.getLastLocation();
//
//                updateLocationUI();
//            }
//        };
//
//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
//        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
//        builder.addLocationRequest(mLocationRequest);
//        mLocationSettingsRequest = builder.build();
//    }
//
//
//    /**
//     * Update the UI displaying the location data
//     * and toggling the buttons
//     */
//    private void updateLocationUI() {
//        if (mCurrentLocation != null) {
////            txtLocationResult.setText(
////                    "Lat: " + mCurrentLocation.getLatitude() + ", " +
////                            "Lng: " + mCurrentLocation.getLongitude()
////            );
////
////            // giving a blink animation on TextView
////            txtLocationResult.setAlpha(0);
////            txtLocationResult.animate().alpha(1).setDuration(300);
//
//            if (!AUtils.isNull(mCurrentLocation) && !AUtils.isNull(mCurrentLocation.getLatitude()) &&
//                    !AUtils.isNull(mCurrentLocation.getLongitude())) {
//
//                QuickUtils.prefs.save(AUtils.LOCATION,
//                        mCurrentLocation.getLatitude() + "," + +mCurrentLocation.getLongitude());
//            }
//        }
//    }
//
//
//    /**
//     * Starting location updates
//     * Check whether location settings are satisfied and then
//     * location updates will be requested
//     */
//    private void startLocationUpdates() {
//        mSettingsClient
//                .checkLocationSettings(mLocationSettingsRequest)
//                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
//                    @SuppressLint("MissingPermission")
//                    @Override
//                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
//                        Log.i(TAG, "All location settings are satisfied.");
//
////                        Toast.makeText(getApplicationContext(), "Started location updates!", Toast.LENGTH_SHORT).show();
//
//                        //noinspection MissingPermission
//                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
//                                mLocationCallback, Looper.myLooper());
//
//                        updateLocationUI();
//                    }
//                })
//                .addOnFailureListener(this, new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        int statusCode = ((ApiException) e).getStatusCode();
//                        switch (statusCode) {
//                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
//                                        "location settings ");
//                                try {
//                                    // Show the dialog by calling startResolutionForResult(), and check the
//                                    // result in onActivityResult().
//                                    ResolvableApiException rae = (ResolvableApiException) e;
//                                    rae.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
//                                } catch (IntentSender.SendIntentException sie) {
//                                    Log.i(TAG, "PendingIntent unable to execute request.");
//                                }
//                                break;
//                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                                String errorMessage = "Location settings are inadequate, and cannot be " +
//                                        "fixed here. Fix in Settings.";
//                                Log.e(TAG, errorMessage);
//
////                                Toast.makeText(HomeActivity.this, errorMessage, Toast.LENGTH_LONG).show();
//                        }
//
//                        updateLocationUI();
//                    }
//                })
//        ;
//    }
//
//    public void startLocationButtonClick() {
//        // Requesting ACCESS_FINE_LOCATION using Dexter library
//        Dexter.withActivity(this)
//                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
//                .withListener(new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted(PermissionGrantedResponse response) {
//                        startLocationUpdates();
//                    }
//
//                    @Override
//                    public void onPermissionDenied(PermissionDeniedResponse response) {
//                        if (response.isPermanentlyDenied()) {
//                            // open device settings when the permission is
//                            // denied permanently
//                            openSettings();
//                        }
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {
//                        token.continuePermissionRequest();
//                    }
//
//                }).check();
//    }
//
//
//    public void stopLocationUpdates() {
//        // Removing location updates
//        mFusedLocationClient
//                .removeLocationUpdates(mLocationCallback)
//                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
////                        Toast.makeText(getApplicationContext(), "Location updates stopped!", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    public void getLocation() {
//        if (mCurrentLocation != null) {
////            Toast.makeText(getApplicationContext(), "Lat: " + mCurrentLocation.getLatitude()
////                    + ", Lng: " + mCurrentLocation.getLongitude(), Toast.LENGTH_LONG).show();
//        } else {
////            Toast.makeText(getApplicationContext(), "Last known location is not available!", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            // Check for the integer request code originally supplied to startResolutionForResult().
//            case REQUEST_CHECK_SETTINGS:
//                switch (resultCode) {
//                    case Activity.RESULT_OK:
//                        Log.e(TAG, "User agreed to make required location settings changes.");
//                        // Nothing to do. startLocationupdates() gets called in onResume again.
//                        break;
//                    case Activity.RESULT_CANCELED:
//                        Log.e(TAG, "User chose not to make required location settings changes.");
//                        break;
//                }
//                break;
//        }
//    }
//
//    private void openSettings() {
//        Intent intent = new Intent();
//        intent.setAction(
//                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//        Uri uri = Uri.fromParts("package",
//                getPackageName(), null);
//        intent.setData(uri);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//
//        // Resuming location updates depending on button state and
//        // allowed permissions
//        if (checkPermissions()) {
//            startLocationUpdates();
//        }
//
//        updateLocationUI();
//    }
//
//    private boolean checkPermissions() {
//        int permissionState = ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION);
//        return permissionState == PackageManager.PERMISSION_GRANTED;
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
}