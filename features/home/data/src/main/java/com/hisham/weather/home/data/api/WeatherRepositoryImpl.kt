package com.hisham.weather.home.data.api

import com.hisham.weather.home.data.api.mapper.toUi
import com.hisham.weather.home.domain.api.WeatherRepository
import com.hisham.weather.home.domain.entities.WeatherResponse
import com.hisham.weather.home.domain.entities.WeatherUiData
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: WeatherLocalDataSource,
) : WeatherRepository {

    override suspend fun fetchWeather(
        lat: Double,
        lng: Double,
        cityName: String,
    ): Result<WeatherUiData> {
        val local = localDataSource.getWeatherIfExists()

        return if (local != null && local.cityName == cityName) {
            Result.success(local)
        } else {
            handleRemote(lat, lng, cityName)
        }
    }

    private suspend fun handleRemote(
        lat: Double,
        lng: Double,
        cityName: String,
    ): Result<WeatherUiData> {
        return when (val response = remoteDataSource.weather(lat, lng)) {
            is WeatherResponse.Success -> {
                val weatherUiData = response.toUi(cityName)
                localDataSource.store(weatherUiData)
                Result.success(weatherUiData)
            }
            is WeatherResponse.Failure -> Result.failure(Exception(response.error))
        }
    }
}
