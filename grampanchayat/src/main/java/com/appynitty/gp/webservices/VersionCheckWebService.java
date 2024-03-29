package com.appynitty.gp.webservices;

import com.appynitty.gp.pojo.ResultPojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by Ayan Dey on 15/11/18.
 */
public interface VersionCheckWebService {

    @GET("api/Get/VersionUpdate")
    Call<ResultPojo> checkVersion(@Header("appId") String appId,
                                  @Header("version") int version);

}
