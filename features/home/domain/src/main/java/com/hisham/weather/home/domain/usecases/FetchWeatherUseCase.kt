package com.hisham.weather.home.domain.usecases

import com.hisham.weather.home.domain.api.WeatherRepository
import com.hisham.weather.home.domain.entities.WeatherResponse
import com.hisham.weather.home.domain.entities.WeatherUiData
import com.hisham.weather.home.domain.location.CityNameResolver
import com.hisham.weather.home.domain.location.LocationDelegate
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class FetchWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val cityNameResolver: CityNameResolver,
) {

    suspend fun execute(lat: Double, lng: Double): Result<WeatherUiData> {
        val city = cityNameResolver.cityNameByLatLng(lat, lng)

        return when (val response = weatherRepository.fetchWeather(lat, lng)) {
            is WeatherResponse.Success -> Result.success(
                toUiData(
                    response,
                    city,
                )
            )
            is WeatherResponse.Failure -> Result.failure(Exception(response.error))
        }
    }

    private fun toUiData(response: WeatherResponse.Success, cityName: String): WeatherUiData =
        with(response) {
            val weather = current.weather.first()
            val firstDayTemp = daily.first().temp

            return WeatherUiData(
                cityName = cityName,
                date = formatDate(current.dt),
                dayDegrees = firstDayTemp.day.toInt(),
                nightDegrees = firstDayTemp.night.toInt(),
                currentDegrees = current.temp.toInt(),
                feelsLike = current.feelsLike.toInt(),
                weatherDescription = weather.description,
                iconUrl = "https://openweathermap.org/img/wn/${weather.icon}@2x.png"
            )
        }

    private fun formatDate(timestamp: Long): String {
        return SimpleDateFormat("dd MMM, HH:mm a").format(Date(timestamp))
    }
}
