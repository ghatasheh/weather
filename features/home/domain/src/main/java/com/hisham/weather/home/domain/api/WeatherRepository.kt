package com.hisham.weather.home.domain.api

import com.hisham.weather.home.domain.entities.WeatherResponse

interface WeatherRepository {

    suspend fun fetchWeather(lat: Double, lng: Double): WeatherResponse
}
