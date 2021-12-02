package com.hisham.weather.home.domain.usecases

import com.hisham.weather.home.domain.api.WeatherRepository
import com.hisham.weather.home.domain.entities.WeatherUiData
import com.hisham.weather.home.domain.location.CityNameResolver
import javax.inject.Inject

class FetchWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val cityNameResolver: CityNameResolver,
) {

    suspend fun execute(lat: Double, lng: Double): Result<WeatherUiData> {
        val city = cityNameResolver.cityNameByLatLng(lat, lng)
        return weatherRepository.fetchWeather(lat, lng, city)
    }
}
