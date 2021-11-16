package com.hisham.weather.home.domain.entities

import com.google.gson.annotations.SerializedName

sealed class WeatherResponse {
    data class Success(
        val lat: Double,
        val lon: Double,
        val timezone: String,
        val timezone_offset: Long,
        val current: NetworkCurrent,
        val hourly: List<NetworkHourly>,
        val daily: List<NetworkDaily>,
        val alerts: List<NetworkAlert>?,
    ) : WeatherResponse()

    data class Failure(
        val error: String
    ) : WeatherResponse()
}

data class Precipitation(
    @SerializedName("1h") val hour: Double
)

data class Weather(
    val id: String,
    val main: String,
    val description: String,
    val icon: String,
)

data class NetworkCurrent(
    val dt: Long,
    val sunrise: Long?,
    val sunset: Long?,
    val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    val pressure: Double,
    val humidity: Double,
    @SerializedName("dew_point") val dewPoint: Double,
    val clouds: Double,
    val uvi: Double,
    val visibility: Double,
    @SerializedName("wind_speed") val windSpeed: Double,
    @SerializedName("wind_gust") val windGust: Double?,
    @SerializedName("wind_deg") val windDeg: Int,
    val rain: Precipitation?,
    val snow: Precipitation?,
    val weather: List<Weather>
)

data class NetworkHourly(
    val dt: Long,
    val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    val pressure: Double,
    val humidity: Double,
    @SerializedName("dew_point") val dewPoint: Double,
    val clouds: Double,
    val uvi: Double,
    val visibility: Double,
    @SerializedName("wind_speed") val windSpeed: Double,
    @SerializedName("wind_gust") val windGust: Double?,
    @SerializedName("wind_deg") val windDeg: Int,
    val pop: Double,
    val rain: Precipitation?,
    val snow: Precipitation?,
    val weather: List<Weather>
)

data class NetworkDaily(
    val dt: Long,
    val sunrise: Long?,
    val sunset: Long?,
    val moonrise: Long?,
    val moonset: Long?,
    @SerializedName("moon_phase") val moonPhase: Double,
    val temp: Temp,
    @SerializedName("feels_like") val feelsLike: FeelsLike?,
    val pressure: Double,
    val humidity: Double,
    @SerializedName("dew_point") val dewPoint: Double,
    @SerializedName("wind_speed") val windSpeed: Double,
    @SerializedName("wind_gust") val windGust: Double?,
    @SerializedName("wind_deg") val windDeg: Int,
    val uvi: Double,
    val pop: Double,
    val rain: Double?,
    val snow: Double?,
    val weather: List<Weather>
) {
    data class Temp(
        val morn: Double,
        val day: Double,
        val eve: Double,
        val night: Double,
        val min: Double,
        val max: Double,
    )

    data class FeelsLike(
        val morn: Double,
        val day: Double,
        val eve: Double,
        val night: Double,
    )
}

data class NetworkAlert(
    @SerializedName("sender_name") val senderName: String,
    val event: String,
    val start: Long,
    val end: Long,
    val description: String
)
