package com.appynitty.gp.webservices;


import com.appynitty.gp.pojo.OurGramPanchayatPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;


/**
 * Created by MiTHUN on 15/5/18.
 */

public interface OurGramPanchayatWebservice {

    @GET("api/APIProfile/GetDetailList")
    Call<List<OurGramPanchayatPojo>> pullOurGramPanchayatList(@Header("appId") String appId,
                                                              @Header("languageId") String languageId,
                                                              @Header("fdate") String fdate);
}