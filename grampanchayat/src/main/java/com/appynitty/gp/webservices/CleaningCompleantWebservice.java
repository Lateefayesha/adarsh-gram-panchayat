package com.appynitty.gp.webservices;


import com.appynitty.gp.pojo.ResultPojo;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by MiTHUN on 16/3/18.
 */

public interface CleaningCompleantWebservice {

    @Multipart
    @POST("api/APICompliant/Save")
    Call<ResultPojo> saveCleaningCompleant(@Header("appId") String key,
                                           @Part("name") RequestBody name,
                                           @Part("number") RequestBody number,
                                           @Part("address") RequestBody address,
                                           @Part("tip") RequestBody tip,
                                           @Part("email") RequestBody email,
                                           @Part("wardNo") RequestBody wardNo,
                                           @Part("place") RequestBody location,
                                           @Part("details") RequestBody details,
                                           @Part("languageId") RequestBody languageId,
                                           @Part("createdDate") RequestBody createdDate,
                                           @Part("latlog") RequestBody latLog,
                                           @Part MultipartBody.Part imageFile1);

    @Multipart
    @POST("api/APIWaterCompliant/Save")
    Call<ResultPojo> saveDrinkingWaterCompleant(@Header("appId") String key,
                                                @Part("name") RequestBody name,
                                                @Part("number") RequestBody number,
                                                @Part("address") RequestBody address,
                                                @Part("tip") RequestBody tip,
                                                @Part("email") RequestBody email,
                                                @Part("wardNo") RequestBody wardNo,
                                                @Part("place") RequestBody location,
                                                @Part("details") RequestBody details,
                                                @Part("languageId") RequestBody languageId,
                                                @Part("createdDate") RequestBody createdDate,
                                                @Part("latlog") RequestBody latLog,
                                                @Part MultipartBody.Part imageFile1);

    @Multipart
    @POST("api/APILightCompliant/Save")
    Call<ResultPojo> saveLightCompleant(@Header("appId") String key,
                                        @Part("name") RequestBody name,
                                        @Part("number") RequestBody number,
                                        @Part("address") RequestBody address,
                                        @Part("tip") RequestBody tip,
                                        @Part("email") RequestBody email,
                                        @Part("wardNo") RequestBody wardNo,
                                        @Part("place") RequestBody location,
                                        @Part("details") RequestBody details,
                                        @Part("languageId") RequestBody languageId,
                                        @Part("createdDate") RequestBody createdDate,
                                        @Part("latlog") RequestBody latLog,
                                        @Part MultipartBody.Part imageFile1);

    @Multipart
    @POST("api/APIMaintenanceRepairsCompliant/Save")
    Call<ResultPojo> saveMaintenanceCompleant(@Header("appId") String key,
                                              @Part("name") RequestBody name,
                                              @Part("number") RequestBody number,
                                              @Part("address") RequestBody address,
                                              @Part("tip") RequestBody tip,
                                              @Part("email") RequestBody email,
                                              @Part("wardNo") RequestBody wardNo,
                                              @Part("place") RequestBody location,
                                              @Part("details") RequestBody details,
                                              @Part("languageId") RequestBody languageId,
                                              @Part("createdDate") RequestBody createdDate,
                                              @Part("latlog") RequestBody latLog,
                                              @Part MultipartBody.Part imageFile1);

}