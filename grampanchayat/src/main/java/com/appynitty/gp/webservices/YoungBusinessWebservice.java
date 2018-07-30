package com.appynitty.gp.webservices;


import com.appynitty.gp.pojo.ApplyBusinessPojo;
import com.appynitty.gp.pojo.ResultPojo;
import com.appynitty.gp.pojo.YoungBusinessPojo;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by MiTHUN on 15/5/18.
 */

public interface YoungBusinessWebservice {

    @POST("api/APIBusniess/Application")
    Call<ResultPojo> saveYoungBusinessApplication(@Header("appId") String appId,
                                                  @Body ApplyBusinessPojo applyBusinessPojo);

    @GET("api/APIBusniess/GetDetailList")
    Call<List<YoungBusinessPojo>> pullYoungBusinessList(@Header("appId") String appId,
                                                        @Header("languageId") String languageId,
                                                        @Header("fdate") String fdate);


    @Multipart
    @POST("api/APIBusniess/Save")
    Call<ResultPojo> saveBusinessApplication(@Header("appId") String key,
                                             @Part("yBId") RequestBody yBId,
                                             @Part("name") RequestBody name,
                                             @Part("number") RequestBody number,
                                             @Part("address") RequestBody address,
                                             @Part("education") RequestBody education,
                                             @Part("email") RequestBody email,
                                             @Part("adharrNo") RequestBody adharCardNo,
                                             @Part("skills") RequestBody otherSkill,
                                             @Part("businessField") RequestBody interestedBusiness,
                                             @Part("aboutYourSelf") RequestBody aboutYourself,
                                             @Part("languageId") RequestBody languageId,
                                             @Part("createdDate") RequestBody createdDate,
                                             @Part("availableResources") RequestBody resourceAvailable,
                                             @Part("helprequired") RequestBody helpRequired,
                                             @Part("latlog") RequestBody latlog1,
                                             @Part("latlog1") RequestBody latlog2,
                                             @Part("latlog2") RequestBody latlog3,
                                             @Part("latlog3") RequestBody latlog4,
                                             @Part MultipartBody.Part imageFileMultiBody1,
                                             @Part MultipartBody.Part imageFileMultiBody2,
                                             @Part MultipartBody.Part imageFileMultiBody3,
                                             @Part MultipartBody.Part imageFileMultiBody4);

}