package com.appynitty.gp.webservices;

import com.appynitty.gp.pojo.UpcomingEventsPojo;
import com.appynitty.gp.pojo.WorkCheckOutPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by Richali Pradhan Gupte on 05-10-2018.
 */
public interface UpcomingEventWebservice {

    @GET("api/Get/Event")
    Call<List<UpcomingEventsPojo>> pullUpcomingEventList(@Header("appId") String appId);
}
