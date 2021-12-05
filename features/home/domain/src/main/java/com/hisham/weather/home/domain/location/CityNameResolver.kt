package com.hisham.weather.home.domain.location

/**
 * Interface to resolve city name by latitude and longitude
 */
interface CityNameResolver {

    /**
     * @param lat latitude
     * @param lng longitude
     *
     * @return city name or empty string when failure
     */
    fun cityNameByLatLng(lat: Double, lng: Double): String
}
