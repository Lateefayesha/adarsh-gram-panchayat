package com.appynitty.ghantagaditracker.webservices;

import com.appynitty.ghantagaditracker.pojo.ActiveUserListPojo;
import com.appynitty.ghantagaditracker.pojo.AreaListPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ayan Dey on 20/11/18.
 */

public interface GhantaGadiTrackerWebservice {

    @GET("api/Get/Area")
    Call<List<AreaListPojo>> fetchAreaList(@Query("AppId") String appId);


    @GET("api/Get/AllCurrentUser")
    Call<List<ActiveUserListPojo>> fetchAllActiveUserList(@Query("AppId") String appId);


    @GET("api/Get/AreaCurrentUser")
    Call<List<ActiveUserListPojo>> fetchAreawiseUserList(@Query("AppId") String appId, @Query("area") String area);


}
