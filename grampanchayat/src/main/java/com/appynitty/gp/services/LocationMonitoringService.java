package com.appynitty.gp.services;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.appynitty.gp.utils.AUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 30/6/18.
 */

public class LocationMonitoringService extends Service implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    private static final String TAG = LocationMonitoringService.class.getSimpleName();
    GoogleApiClient mLocationClient;
    LocationRequest mLocationRequest = new LocationRequest();


//    public static final String ACTION_LOCATION_BROADCAST = LocationMonitoringService.class.getName() + "LocationBroadcast";
//    public static final String EXTRA_LATITUDE = "extra_latitude";
//    public static final String EXTRA_LONGITUDE = "extra_longitude";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mLocationClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


        mLocationRequest.setInterval(AUtils.LOCATION_INTERVAL);
        mLocationRequest.setFastestInterval(AUtils.FASTEST_LOCATION_INTERVAL);


        int priority = LocationRequest.PRIORITY_HIGH_ACCURACY; //by default
        //PRIORITY_BALANCED_POWER_ACCURACY, PRIORITY_LOW_POWER, PRIORITY_NO_POWER are the other priority modes


        mLocationRequest.setPriority(priority);
        mLocationClient.connect();

//        tunOnGps();
        //Make it stick to the notification panel so it is less prone to get cancelled by the Operating System.
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /*
     * LOCATION CALLBACKS
     */
    @Override
    public void onConnected(Bundle dataBundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient, mLocationRequest, this);

//        Log.d(TAG, "Connected to Google API");
    }

    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
    @Override
    public void onConnectionSuspended(int i) {
//        Log.d(TAG, "Connection suspended");
    }


    //to get the location change
    @Override
    public void onLocationChanged(Location location) {
//        Log.d(TAG, "Location changed");

        if (location != null) {
//            Log.d(TAG, "== location != null");

            //Send result to activities

            if (!AUtils.isNullString(String.valueOf(location.getLatitude())) && !AUtils.isNullString(String.valueOf(location.getLongitude()))) {

                QuickUtils.prefs.save(AUtils.LOCATION, "" + String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude()));

//                Toast.makeText(this, "" + String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude()), Toast.LENGTH_SHORT).show();

//                LocationModel locationByCoordinates = QuickUtils.location.getLocationByCoordinates(location.getLatitude(), location.getLongitude());
//                Toast.makeText(this, "" + locationByCoordinates.toString(), Toast.LENGTH_SHORT).show();
            }
//            sendMessageToUI(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
        }
    }

//    private void tunOnGps() {
//
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                .addLocationRequest(mLocationRequest);
//        builder.setAlwaysShow(true); //this is the key ingredient to show dialog always when GPS is off
//        LocationServices.SettingsApi.checkLocationSettings(mLocationClient, builder.build());
//    }


//    private void sendMessageToUI(String lat, String lng) {
//
//        Log.d(TAG, "Sending info...");
//
//        Intent intent = new Intent(ACTION_LOCATION_BROADCAST);
//        intent.putExtra(EXTRA_LATITUDE, lat);
//        intent.putExtra(EXTRA_LONGITUDE, lng);
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
//
//
//    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
//        Log.d(TAG, "Failed to connect to Google API");

    }


}