package com.hisham.weather.home.presentation

sealed class HomeEvent {
    data class FetchWeather(
        val lat: Double = 53.3459353776223,
        val lng: Double = -6.265959414459697
    ) : HomeEvent()
}
