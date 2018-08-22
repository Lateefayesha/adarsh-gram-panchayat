package com.appynitty.gp.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.appynitty.gp.controller.SyncServer;
import com.appynitty.gp.pojo.FcmIdPojo;
import com.mithsoft.lib.componants.MyProgressDialog;

import quickutils.core.QuickUtils;

/**
 * Created by MiTHUN on 24/1/18.
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
