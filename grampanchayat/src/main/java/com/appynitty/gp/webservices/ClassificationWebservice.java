package com.appynitty.gp.webservices;

import com.appynitty.gp.pojo.ClassificationPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by Richali Pradhan Gupte on 13-10-2018.
 */
public interface ClassificationWebservice {
    @GET("api/Get/AdsClassification")
    Call<List<ClassificationPojo>> pullClassificationList(@Header("appId") String appId);
}
