package com.appynitty.ghantagaditracker.utils;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;

import quickutils.core.QuickUtils;


/**
 * Created by Ayan on 16/3/18.
 */

public class MyApplicationConstants extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


//        init QuickUtils lib
        QuickUtils.init(getApplicationContext());

//          init Firebase
        FirebaseApp.initializeApp(getApplicationContext());

//        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "MYRIADPRO-REGULAR.OTF"); // font from assets: "assets/fonts/Roboto-Regular.ttf
    }


}
