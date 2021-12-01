package com.hisham.weather.location

import com.hisham.weather.home.domain.location.LocationDelegate
import javax.inject.Inject

class LocationDelegateImpl @Inject constructor(
    private val locationManager: LocationManager,
)  : LocationDelegate {

    override suspend fun getLocation(): Pair<Double, Double>? {
        locationManager.initLocationIfPermissionGranted()
        return locationManager.getLocation()
    }
}