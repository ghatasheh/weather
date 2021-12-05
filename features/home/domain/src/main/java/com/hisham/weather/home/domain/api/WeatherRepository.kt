package com.hisham.weather.home.domain.api

import com.hisham.weather.home.domain.entities.WeatherUiData

/**
 * Repository to handle fetching/caching [WeatherUiData]
 */
interface WeatherRepository {

    /**
     * Get weather data for the provided geo-point either from network or cache
     *
     * @param lat latitude
     * @param lng longitude
     * @param cityName city name
     *
     * @return [Result] of [WeatherUiData]
     */
    suspend fun fetchWeather(
        lat: Double,
        lng: Double,
        cityName: String,
    ): Result<WeatherUiData>
}
