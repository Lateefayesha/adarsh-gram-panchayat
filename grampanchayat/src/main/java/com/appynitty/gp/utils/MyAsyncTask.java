package com.appynitty.gp.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import com.appynitty.gp.R;
import com.appynitty.gp.controller.SyncServer;
import com.riaylibrary.custom_component.MyProgressDialog;

/**
 * Created by MiTHUN on 24/1/18.
 */

public class MyAsyncTask extends AsyncTask {

    public SyncServer syncServer;
    private Context context;
    private boolean isNetworkAvail = false;
    private boolean isShowPrgressDialog;
    private MyProgressDialog myProgressDialog;
    private AsynTaskListener asynTaskListener;


    public MyAsyncTask(Context context, boolean isShowPrgressDialog, AsynTaskListener asynTaskListener) {

        this.asynTaskListener = asynTaskListener;
        this.context = context;
        this.syncServer = new SyncServer(context);
        this.isShowPrgressDialog = isShowPrgressDialog;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        myProgressDialog = new MyProgressDialog(context, R.drawable.progress_bar, false);
        if (isShowPrgressDialog) {
            myProgressDialog.show();
        }
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        if (AUtils.isInternetAvailable(AUtils.mApplicationConstant)) {
            try {

                isNetworkAvail = true;
                asynTaskListener.doInBackgroundOpration(syncServer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (myProgressDialog.isShowing()) {
            myProgressDialog.dismiss();
        }
        if (isNetworkAvail) {

            asynTaskListener.onFinished();
        } else {

            if (isShowPrgressDialog) {
                AUtils.warning(context, context.getString(R.string.noInternet), Toast.LENGTH_SHORT);
            }
        }
    }

    public interface AsynTaskListener {

        void doInBackgroundOpration(SyncServer syncServer);

        void onFinished();
    }
}
