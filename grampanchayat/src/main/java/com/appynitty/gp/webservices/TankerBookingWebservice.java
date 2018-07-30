package com.appynitty.gp.webservices;


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

public interface TankerBookingWebservice {

    @POST("api/TankerBooking/Save")
    Call<ResultPojo> saveTankerBooking(@Header("appId") String appId,
                                       @Body TankerBookingPojo tankerBookingPojo);

    @GET("api/APIBusniess/GetDetailList")
    Call<List<YoungBusinessPojo>> pullYoungBusinessList(@Header("appId") String appId,
                                                        @Header("languageId") String languageId,
                                                        @Header("fdate") String fdate);
}