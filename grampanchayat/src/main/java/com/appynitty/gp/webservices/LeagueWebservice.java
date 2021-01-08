package com.appynitty.gp.webservices;

import com.appynitty.gp.pojo.LeageaAnswerDetailsPojo;
import com.appynitty.gp.pojo.LeagueQuestionPojo;
import com.appynitty.gp.pojo.ResultPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Ayan Dey on 1/7/19.
 */
public interface LeagueWebservice {

    @GET("api/Get/Questions")
    Call<List<LeagueQuestionPojo>> getLeagueQuestions(@Header("appId") String appId, @Header("LangId") String LangId);

    @POST("api/Save/AnswerDetails")
    Call<ResultPojo> submitLeagueAnswer(@Header("appId") String appId,
                                        @Header("ReferenceId") String ReferenceId,
                                        @Header("GGAppId") String GGAppId,
                                        @Header("Content-Type") String contentType,
                                        @Body LeageaAnswerDetailsPojo leageaAnswerDetailsPojo);
}
