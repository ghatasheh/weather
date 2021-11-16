package com.hisham.weather.home.domain.location

interface CityNameResolver {
    fun cityNameByLatLng(lat: Double, lng: Double): String
}
