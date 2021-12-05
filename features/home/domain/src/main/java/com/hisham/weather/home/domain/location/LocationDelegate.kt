package com.hisham.weather.home.domain.location

/**
 * Delegate to access user location from different modules
 */
interface LocationDelegate {

    /**
     * Get user location
     *
     * @return Pair of latitude and longitude
     */
    suspend fun getLocation(): Pair<Double, Double>?
}
