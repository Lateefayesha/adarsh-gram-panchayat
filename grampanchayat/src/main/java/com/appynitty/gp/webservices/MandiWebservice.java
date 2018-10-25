package com.appynitty.gp.webservices;

import com.appynitty.gp.pojo.MandiPojo;
import com.appynitty.gp.pojo.WorkCheckOutPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by Richali Pradhan Gupte on 02-10-2018.
 */
public interface MandiWebservice {

    @GET("api/Get/MandiRate")
    Call<List<MandiPojo>> pullMandiList(@Header("appId") String appId,
                                        @Header("date") String date);

    @GET("api/Get/DefaultMandiRate")
    Call<List<MandiPojo>> pullDefaultMandiList(@Header("appId") String appId);
}
