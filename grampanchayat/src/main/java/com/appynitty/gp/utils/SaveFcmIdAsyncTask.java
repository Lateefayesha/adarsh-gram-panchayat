package com.appynitty.gp.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.appynitty.gp.controller.SyncServer;
import com.appynitty.gp.pojo.FcmIdPojo;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.custom_component.MyProgressDialog;

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

        if (AUtils.isInternetAvailable(AUtils.mApplicationConstant)) {
            try {

                Log.e(SaveFcmIdAsyncTask.class.getName(),"FCM ID :" + Prefs.getString(AUtils.FCM_ID, ""));
                Log.e(SaveFcmIdAsyncTask.class.getName(),"Device ID : " + Prefs.getString(AUtils.USER_ID, ""));
                FcmIdPojo fcmIdPojo = new FcmIdPojo();
                fcmIdPojo.setFcmid(Prefs.getString(AUtils.FCM_ID, ""));
                fcmIdPojo.setUserid(Prefs.getString(AUtils.USER_ID, ""));

                syncServer.saveFcmIdOnServer(fcmIdPojo);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
