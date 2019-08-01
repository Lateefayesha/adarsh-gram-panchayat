package com.appynitty.ghantagaditracker.webservices;

import com.appynitty.ghantagaditracker.pojo.RegistrationDetailsPojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Ayan Dey on 24/6/19.
 */
public interface RegistrationWebservice {


    @GET("api/Get/MobileDetails")
    Call<RegistrationDetailsPojo> getHouseRegistrationDetails(@Header("appId")String appId,
                                                              @Header("FCMID")String fcmId,
                                                              @Header("ReferanceId")String refId);

    @GET("api/Get/SendOTP")
    Call<RegistrationDetailsPojo> sendOtp(@Header("appId")String appId,
                                          @Header("FCMID")String fcmId,
                                          @Header("ReferanceId")String refId,
                                          @Header("Mobile")String mobile);

    @POST("api/Save/DeviceDetails")
    Call<RegistrationDetailsPojo> deviceDetails(@Header("appId")String appId,
                                                @Header("FCMID")String fcmId,
                                                @Header("ReferanceId")String refId,
                                                @Header("DeviceID")String deviceId);

    @POST("api/Save/DeviceDetailsClear")
    Call<RegistrationDetailsPojo> logoutDevice(@Header("appId")String appId,
                                               @Header("DeviceID")String deviceId);
}
