package com.bilgehankalkan.semihozturkhackathon.service;

import com.bilgehankalkan.semihozturkhackathon.service.models.RequestBody;
import com.bilgehankalkan.semihozturkhackathon.service.models.ResponseBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Bilgehan on 11.02.2018.
 */

public interface ApiInterface {

    String SEARCH_RECORDS = "searchRecords";

    @Headers("Content-Type: application/json")
    @POST(SEARCH_RECORDS)
    Call<ResponseBody> getSearchRecords(@Body RequestBody requestBody);
}
