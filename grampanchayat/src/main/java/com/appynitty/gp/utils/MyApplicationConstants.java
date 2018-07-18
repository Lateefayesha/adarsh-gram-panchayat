package com.appynitty.gp.utils;

import android.app.Application;

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

//        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "MYRIADPRO-REGULAR.OTF"); // font from assets: "assets/fonts/Roboto-Regular.ttf
    }

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//    }
}
