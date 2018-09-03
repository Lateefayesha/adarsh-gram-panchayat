package com.appynitty.gp.webservices;


import com.appynitty.gp.pojo.FcmIdPojo;
import com.appynitty.gp.pojo.ResultPojo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


/**
 * Created by MiTHUN on 15/5/18.
 */

public interface FcmIdWebservice {

    @POST("api/DeviceDetails/Save")
    Call<ResultPojo> saveFcmId(@Header("appId") String appId,
                               @Body FcmIdPojo fcmIdPojo);

}