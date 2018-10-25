package com.appynitty.gp.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.preference.PreferenceManager;

import com.google.firebase.FirebaseApp;

import java.util.Locale;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 16/3/18.
 */

public class MyApplicationConstants extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        init QuickUtils lib
        QuickUtils.init(getApplicationContext());
        FirebaseApp.initializeApp(getApplicationContext());

//        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "MYRIADPRO-REGULAR.OTF"); // font from assets: "assets/fonts/Roboto-Regular.ttf
    }

    @Override
    protected void attachBaseContext(Context base) {

        super.attachBaseContext(LocaleHelper.onAttach(base, AUtils.DEFAULT_LANGUAGE_NAME));
    }

}
