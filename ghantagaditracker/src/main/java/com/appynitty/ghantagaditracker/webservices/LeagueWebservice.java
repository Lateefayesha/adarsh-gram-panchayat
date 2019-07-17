package com.appynitty.ghantagaditracker.webservices;

import com.appynitty.ghantagaditracker.pojo.LeageaAnswerDetailsPojo;
import com.appynitty.ghantagaditracker.pojo.LeagueAnswerPojo;
import com.appynitty.ghantagaditracker.pojo.LeagueQuestionPojo;
import com.appynitty.ghantagaditracker.pojo.ResultPojo;

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
    Call<List<LeagueQuestionPojo>> getLeagueQuestions(@Header("appId")String appId);

    @POST("api/Save/AnswerDetails")
    Call<ResultPojo> submitLeagueAnswer(@Header("appId")String appId,
                                        @Header("ReferenceId")String ReferenceId,
                                        @Header("GGAppId") String GGAppId,
                                        @Header("Content-Type")String contentType,
                                        @Body LeageaAnswerDetailsPojo leageaAnswerDetailsPojo);
}
