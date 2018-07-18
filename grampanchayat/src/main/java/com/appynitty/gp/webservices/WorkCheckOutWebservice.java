package com.appynitty.gp.webservices;


import com.appynitty.gp.pojo.WorkCheckOutPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;


/**
 * Created by MiTHUN on 15/5/18.
 */

public interface WorkCheckOutWebservice {

    @POST("Notes/Save")
    Call<List<WorkCheckOutPojo>> saveNoteList(@Header("OrgId") String key,
                                              @Body List<WorkCheckOutPojo> notesPojoList);

    @GET("APIWork/GetDetailList")
    Call<List<WorkCheckOutPojo>> pullWorkCheckOutList(@Header("appId") String appId,
                                                      @Header("languageId") String languageId,
                                                      @Header("fdate") String fdate);
}