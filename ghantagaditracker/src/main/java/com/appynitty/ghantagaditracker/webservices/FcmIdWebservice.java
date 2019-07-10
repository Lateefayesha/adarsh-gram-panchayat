package com.appynitty.ghantagaditracker.webservices;



import com.appynitty.ghantagaditracker.pojo.FcmIdPojo;
import com.appynitty.ghantagaditracker.pojo.ResultPojo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


/**
 * Created by Ayan on 04/06/19.
 */

public interface FcmIdWebservice {

    @POST("api/DeviceDetails/Save")
    Call<ResultPojo> saveFcmId(@Header("appId") String appId,
                               @Body FcmIdPojo fcmIdPojo);

}