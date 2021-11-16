package com.hisham.weather.home.data.api

import com.hisham.weather.home.domain.entities.WeatherResponse
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(
    private val api: WeatherApi,
) {
    suspend fun weather(lat: Double, lng: Double): WeatherResponse {
        return try {
            api.getForecast(lat, lng)
        } catch (e: Exception) {
            WeatherResponse.Failure(e.message ?: "Weather API failed")
        }
    }
}
