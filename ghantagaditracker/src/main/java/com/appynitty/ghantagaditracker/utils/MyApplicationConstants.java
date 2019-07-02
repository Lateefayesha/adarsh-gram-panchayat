package com.appynitty.ghantagaditracker.utils;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.mithsoft.lib.utils.MsUtils;

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

    @Override
    protected void attachBaseContext(Context base) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            super.attachBaseContext(LocaleHelper.onAttach(base, AUtils.DEFAULT_LANGUAGE_NAME));
        } else {
            super.attachBaseContext(base);
        }
    }


}
