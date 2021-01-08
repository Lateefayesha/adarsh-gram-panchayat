package com.appynitty.gp.webservices;

import com.appynitty.gp.pojo.PropertyTaxPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by Ayan Dey on 22/10/18.
 */
public interface PropertyTaxDetailsWebservice {

    @GET("api/Get/PropertyTax")
    Call<PropertyTaxPojo>fetchPropertyDetails(@Header("appId") String appId,
                                              @Header("HouseNo") String propertyNo);
}
