package com.hisham.weather.home.data.api

import com.hisham.weather.home.data.BuildConfig
import com.hisham.weather.home.domain.entities.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("onecall")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric",
        @Query("exclude") exclude: String = "minutely",
        @Query("appid") appId: String = BuildConfig.API_KEY,
    ): WeatherResponse.Success
}