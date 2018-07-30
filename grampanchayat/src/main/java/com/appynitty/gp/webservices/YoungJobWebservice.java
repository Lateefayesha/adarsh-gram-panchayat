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

public interface YoungJobWebservice {

    @Multipart
    @POST("api/APIJob/Save")
    Call<ResultPojo> saveYoungJobApplication(@Header("appId") String key,
                                             @Part("Name") RequestBody name,
                                             @Part("Number") RequestBody number,
                                             @Part("Address") RequestBody address,
                                             @Part("Education") RequestBody education,
                                             @Part("Email") RequestBody email,
                                             @Part("AdhaarNo") RequestBody adharCardNo,
                                             @Part("Skills") RequestBody otherSkill,
                                             @Part("JobField") RequestBody interestedJob,
                                             @Part("AboutYourSelf") RequestBody aboutYourself,
                                             @Part("LanguageId") RequestBody languageId,
                                             @Part("createdDate") RequestBody createdDate,
//                                             @Part("ResumeUrl") RequestBody resumeUrl,
//                                             @Part("ImageUrl") RequestBody imageUrl,
                                             @Part("Specialization") RequestBody specialization,
                                             @Part("latlog") RequestBody latlog1,
                                             @Part("latlog1") RequestBody latlog2,
                                             @Part("latlog2") RequestBody latlog3,
                                             @Part("latlog3") RequestBody latlog4,
                                             @Part MultipartBody.Part imageFileMultiBody1,
                                             @Part MultipartBody.Part imageFileMultiBody2,
                                             @Part MultipartBody.Part imageFileMultiBody3,
                                             @Part MultipartBody.Part imageFileMultiBody4,
                                             @Part MultipartBody.Part resumeFileMultiBody);
}