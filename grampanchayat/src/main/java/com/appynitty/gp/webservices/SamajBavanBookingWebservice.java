package com.appynitty.gp.webservices;


import com.appynitty.gp.pojo.ResultPojo;
import com.appynitty.gp.pojo.SamajBavanBookingPojo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


/**
 * Created by MiTHUN on 15/5/18.
 */

public interface SamajBavanBookingWebservice {

    @POST("api/SamjhBhavanBooking/Save")
    Call<ResultPojo> saveSamajBavanBooking(@Header("appId") String appId,
                                           @Body SamajBavanBookingPojo samajBavanBookingPojo);

}