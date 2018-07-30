package com.appynitty.gp.webservices;


import com.appynitty.gp.pojo.ContactUsPojo;
import com.appynitty.gp.pojo.OurGramPanchayatPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;


/**
 * Created by MiTHUN on 15/5/18.
 */

public interface ContactUsWebservice {


    @GET("api/APIContactUs/GetDetailList")
    Call<ContactUsPojo> pullContactUsList(@Header("appId") String appId,
                                          @Header("languageId") String languageId,
                                          @Header("fdate") String fdate);
}