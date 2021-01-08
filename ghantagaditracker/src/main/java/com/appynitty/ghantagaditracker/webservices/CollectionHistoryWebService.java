package com.appynitty.ghantagaditracker.webservices;

import com.appynitty.ghantagaditracker.pojo.CollectionHistoryPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by Ayan Dey on 15/7/19.
 */
public interface CollectionHistoryWebService {

    @GET("api/AdminMenuDashboard/Get/HouseGarbageCollection")
    Call<List<CollectionHistoryPojo>> getCollectionHistory (@Header("appId")String appId,
                                                            @Header("fdate")String startDate,
                                                            @Header("tdate")String endDate,
                                                            @Header("UserId")String userId,
                                                            @Header("gcType")String gcType,
                                                            @Header("Offset")String offset,
                                                            @Header("Fetch_Next")String limit,
                                                            @Header("SearchString")String searchString);
}
