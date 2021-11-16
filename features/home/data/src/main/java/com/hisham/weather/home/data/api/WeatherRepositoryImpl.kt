package com.hisham.weather.home.data.api

import com.hisham.weather.home.domain.api.WeatherRepository
import com.hisham.weather.home.domain.entities.WeatherResponse
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
) : WeatherRepository {

    override suspend fun fetchWeather(lat: Double, lng: Double): WeatherResponse {
        return remoteDataSource.weather(lat, lng)
    }
}
