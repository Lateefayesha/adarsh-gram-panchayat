package com.appynitty.ghantagaditracker.adapter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.appynitty.ghantagaditracker.pojo.RegistrationDetailsPojo;
import com.appynitty.ghantagaditracker.utils.AUtils;
import com.appynitty.ghantagaditracker.webservices.RegistrationWebservice;
import com.pixplicity.easyprefs.library.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ayan Dey on 31/7/19.
 */
public class LogoutAdapterClass {

    private LogoutAdapterListner logoutAdapterListner;


    public LogoutAdapterListner getLogoutAdapterListner() {
        return logoutAdapterListner;
    }

    public void setLogoutAdapterListner(LogoutAdapterListner logoutAdapterListner) {
        this.logoutAdapterListner = logoutAdapterListner;
    }

    public void callLogoutApi(){
        RegistrationWebservice webservice = AUtils.createService(RegistrationWebservice.class, AUtils.SERVER_URL_SBA);
        webservice.logoutDevice(Prefs.getString(AUtils.APP_ID_GG, ""),
                Prefs.getString(AUtils.USER_ID, ""))
                .enqueue(new Callback<RegistrationDetailsPojo>() {
                    @Override
                    public void onResponse(@NonNull Call<RegistrationDetailsPojo> call, @NonNull Response<RegistrationDetailsPojo> response) {
                        if(response.code() == 200)
                            getLogoutAdapterListner().onSuccessCallback(response.body());
                        else
                            getLogoutAdapterListner().onFailureCallback();
                    }

                    @Override
                    public void onFailure(@NonNull Call<RegistrationDetailsPojo> call,@NonNull Throwable t) {
                        t.printStackTrace();
                        Log.d(AUtils.TAG_SERVER_ERROR, "RegistrationAdapterClass callRegistrationDetails");
                        getLogoutAdapterListner().onErrorCallback();
                    }
                });
    }

    public interface LogoutAdapterListner{
        void onSuccessCallback(RegistrationDetailsPojo pojo);
        void onFailureCallback();
        void onErrorCallback();
    }
}
