package com.example.androidlesson26.source.api

import com.example.androidlesson26.models.responses.get.NamazTimeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface IApiManager {


    @Headers( "Content-Type: application/json;charset=UTF-8")

    @GET("pray/all")
    suspend fun getAllNamazTimeByRegion(
        @Query("data.city") city: String
    ): Response<NamazTimeResponse>


}