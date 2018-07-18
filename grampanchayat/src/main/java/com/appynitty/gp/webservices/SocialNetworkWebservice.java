package com.appynitty.gp.webservices;


import com.appynitty.gp.pojo.OurGramPanchayatPojo;
import com.appynitty.gp.pojo.SocialNetworkPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;


/**
 * Created by MiTHUN on 15/5/18.
 */

public interface SocialNetworkWebservice {

    @POST("Notes/Save")
    Call<List<OurGramPanchayatPojo>> saveNoteList(@Header("OrgId") String key,
                                                  @Body List<OurGramPanchayatPojo> notesPojoList);

    @GET("APISocial/GetDetailList")
    Call<List<SocialNetworkPojo>> pullSocialNetworkList(@Header("appId") String appId,
                                                        @Header("languageId") String languageId,
                                                        @Header("fdate") String fdate);
}