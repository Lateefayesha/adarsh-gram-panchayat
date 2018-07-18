package com.appynitty.gp.webservices;


import com.appynitty.gp.pojo.DistrictPojo;
import com.appynitty.gp.pojo.GramPanchayatPojo;
import com.appynitty.gp.pojo.StatePojo;
import com.appynitty.gp.pojo.TahsilPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;


/**
 * Created by MiTHUN on 15/5/18.
 */

public interface SmartGpWebservice {

    @GET("APICommon/GetStateList")
    Call<List<StatePojo>> pullStateList();

    @GET("APICommon/GetDistrictList")
    Call<List<DistrictPojo>> pullDistrictList(@Header("state") String stateId);

    @GET("APICommon/GetTehsilList")
    Call<List<TahsilPojo>> pullTahsilList(@Header("district") String districtId);

    @GET("APICommon/GetGramPanchayatList")
    Call<List<GramPanchayatPojo>> pullGramPanchayatList(@Header("state") String stateId,
                                                        @Header("district") String districtId,
                                                        @Header("tehsil") String tahsilId);

}