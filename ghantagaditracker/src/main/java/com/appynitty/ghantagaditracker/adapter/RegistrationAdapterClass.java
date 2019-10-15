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
 * Created by Ayan Dey on 24/6/19.
 */
public class RegistrationAdapterClass {

    public static final int RESPONSE_REGISTRATION_DETAILS = 0;
    public static final int RESPONSE_REGISTRATION_OTP = 1;
    public static final int RESPONSE_REGISTRATION_VERIFIED = 3;
    private RegistrationListner registrationListner;

    public void setRegistrationListner(RegistrationListner mListner) {
        this.registrationListner = mListner;
    }

    public void callRegistrationDetails(String refId) {
        RegistrationWebservice webservice = AUtils.createService(RegistrationWebservice.class, AUtils.SERVER_URL_SBA);
        webservice.getHouseRegistrationDetails(
                Prefs.getString(AUtils.APP_ID_GG, ""),
                Prefs.getString(AUtils.FCM_ID, ""),
                refId)
                .enqueue(new Callback<RegistrationDetailsPojo>() {
                    @Override
                    public void onResponse(@NonNull Call<RegistrationDetailsPojo> call, @NonNull Response<RegistrationDetailsPojo> response) {
                        if (response.code() == 200)
                            registrationListner.onSuccessCallback(response.body(), RESPONSE_REGISTRATION_DETAILS);
                        else
                            registrationListner.onFailureCallback();
                    }

                    @Override
                    public void onFailure(@NonNull Call<RegistrationDetailsPojo> call, @NonNull Throwable t) {
                        t.printStackTrace();
                        Log.d(AUtils.TAG_SERVER_ERROR, "RegistrationAdapterClass callRegistrationDetails");
                        registrationListner.onErrorCallback();
                    }
                });
    }

    public void callRegistrationOtp(String refId, String mobile) {
        RegistrationWebservice webservice = AUtils.createService(RegistrationWebservice.class, AUtils.SERVER_URL_SBA);
        webservice.sendOtp(
                Prefs.getString(AUtils.APP_ID_GG, ""),
                Prefs.getString(AUtils.FCM_ID, ""),
                refId, mobile)
                .enqueue(new Callback<RegistrationDetailsPojo>() {
                    @Override
                    public void onResponse(@NonNull Call<RegistrationDetailsPojo> call, @NonNull Response<RegistrationDetailsPojo> response) {
                        if (response.code() == 200)
                            registrationListner.onSuccessCallback(response.body(), RESPONSE_REGISTRATION_OTP);
                        else
                            registrationListner.onFailureCallback();
                    }

                    @Override
                    public void onFailure(@NonNull Call<RegistrationDetailsPojo> call, @NonNull Throwable t) {
                        t.printStackTrace();
                        Log.d(AUtils.TAG_SERVER_ERROR, "RegistrationAdapterClass callRegistrationDetails");
                        registrationListner.onErrorCallback();
                    }
                });
    }

    public void callSaveDeviceDetails(String refId) {
        RegistrationWebservice webservice = AUtils.createService(RegistrationWebservice.class, AUtils.SERVER_URL_SBA);
        webservice.deviceDetails(
                Prefs.getString(AUtils.APP_ID_GG, ""),
                Prefs.getString(AUtils.FCM_ID, ""),
                refId, Prefs.getString(AUtils.USER_ID, ""))
                .enqueue(new Callback<RegistrationDetailsPojo>() {
                    @Override
                    public void onResponse(@NonNull Call<RegistrationDetailsPojo> call, @NonNull Response<RegistrationDetailsPojo> response) {
                        if (response.code() == 200)
                            registrationListner.onSuccessCallback(response.body(), RESPONSE_REGISTRATION_VERIFIED);
                        else
                            registrationListner.onFailureCallback();
                    }

                    @Override
                    public void onFailure(@NonNull Call<RegistrationDetailsPojo> call, @NonNull Throwable t) {
                        t.printStackTrace();
                        Log.d(AUtils.TAG_SERVER_ERROR, "RegistrationAdapterClass callRegistrationDetails");
                        registrationListner.onErrorCallback();
                    }
                });
    }

    public interface RegistrationListner {
        void onSuccessCallback(RegistrationDetailsPojo detailsPojo, int CallType);

        void onFailureCallback();

        void onErrorCallback();
    }
}
