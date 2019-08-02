package com.android.searchfiltersample.rest;

import com.android.searchfiltersample.model.BankDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/banks")
    Call<List<BankDetails>> getBankDetails(@Query("city") String city);
}