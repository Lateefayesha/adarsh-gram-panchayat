package com.appynitty.gp.webservices;


import com.appynitty.gp.pojo.FcmIdPojo;
import com.appynitty.gp.pojo.ResultPojo;
import com.appynitty.gp.pojo.TankerBookingPojo;
import com.appynitty.gp.pojo.YoungBusinessPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;


/**
 * Created by MiTHUN on 15/5/18.
 */

public interface FcmIdWebservice {

    @POST("api/DeviceDetails/Save")
    Call<ResultPojo> saveFcmId(@Header("appId") String appId,
                                       @Body FcmIdPojo fcmIdPojo);

    @GET("api/APIBusniess/GetDetailList")
    Call<List<YoungBusinessPojo>> pullYoungBusinessList(@Header("appId") String appId,
                                                        @Header("languageId") String languageId,
                                                        @Header("fdate") String fdate);
}