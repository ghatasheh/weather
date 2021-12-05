package com.hisham.weather.home.data.api.mapper

import android.annotation.SuppressLint
import com.hisham.weather.home.domain.entities.WeatherResponse
import com.hisham.weather.home.domain.entities.WeatherUiData
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Map [WeatherResponse.Success] to [WeatherUiData]
 */
fun WeatherResponse.Success.toUi(cityName: String): WeatherUiData {
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

@SuppressLint("SimpleDateFormat")
private fun formatDate(timestamp: Long): String {
    return SimpleDateFormat("dd MMM, HH:mm a").format(Date(timestamp))
}
