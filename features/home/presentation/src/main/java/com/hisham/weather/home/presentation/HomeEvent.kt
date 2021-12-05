package com.hisham.weather.home.presentation

sealed class HomeEvent {
    object FetchWeather : HomeEvent()
}
