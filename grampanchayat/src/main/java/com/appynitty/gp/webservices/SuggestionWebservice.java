package com.appynitty.gp.webservices;


import com.appynitty.gp.pojo.ResultPojo;
import com.appynitty.gp.pojo.SuggestionPojo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


/**
 * Created by MiTHUN on 15/5/18.
 */

public interface SuggestionWebservice {

    @POST("api/FeedBack/Save")
    Call<ResultPojo> saveSuggestion(@Header("appId") String appId,
                                    @Body SuggestionPojo suggestionPojo);

}