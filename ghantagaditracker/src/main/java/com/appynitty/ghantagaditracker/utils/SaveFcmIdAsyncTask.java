package com.appynitty.ghantagaditracker.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.appynitty.ghantagaditracker.controller.SyncServer;
import com.appynitty.ghantagaditracker.pojo.FcmIdPojo;
import com.mithsoft.lib.componants.MyProgressDialog;

import quickutils.core.QuickUtils;

/**
 * Created by Ayan on 04/06/19.
 */

public class SaveFcmIdAsyncTask extends AsyncTask {

    public SyncServer syncServer;
    private Context context;
    private MyProgressDialog myProgressDialog;


    public SaveFcmIdAsyncTask(Context context) {

        this.context = context;
        this.syncServer = new SyncServer(context);
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        if (AUtils.isNetWorkAvailable(context)) {
            try {

                Log.e(SaveFcmIdAsyncTask.class.getName(),"FCM ID :" + QuickUtils.prefs.getString(AUtils.FCM_ID, ""));
                Log.e(SaveFcmIdAsyncTask.class.getName(),"Device ID : " + QuickUtils.prefs.getString(AUtils.USER_ID, ""));
                FcmIdPojo fcmIdPojo = new FcmIdPojo();
                fcmIdPojo.setFcmid(QuickUtils.prefs.getString(AUtils.FCM_ID, ""));
                fcmIdPojo.setUserid(QuickUtils.prefs.getString(AUtils.USER_ID, ""));

                syncServer.saveFcmIdOnServer(fcmIdPojo);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
