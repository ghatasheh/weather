package com.hisham.weather.home.domain.api

import com.hisham.weather.home.domain.entities.WeatherUiData

interface WeatherRepository {

    suspend fun fetchWeather(
        lat: Double,
        lng: Double,
        cityName: String,
    ): Result<WeatherUiData>
}
