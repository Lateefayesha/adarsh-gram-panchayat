package com.appynitty.gp.webservices;


import com.appynitty.gp.pojo.ResultPojo;
import com.appynitty.gp.pojo.TankerBookingPojo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


/**
 * Created by MiTHUN on 15/5/18.
 */

public interface TankerBookingWebservice {

    @POST("api/TankerBooking/Save")
    Call<ResultPojo> saveTankerBooking(@Header("appId") String appId,
                                       @Body TankerBookingPojo tankerBookingPojo);

}