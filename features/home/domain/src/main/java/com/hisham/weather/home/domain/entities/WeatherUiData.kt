package com.hisham.weather.home.domain.entities

data class WeatherUiData(
    val cityName: String,
    val date: String,
    val dayDegrees: Int,
    val nightDegrees: Int,
    val currentDegrees: Int,
    val feelsLike: Int,
    val weatherDescription: String,
    val iconUrl: String,
)
