package com.hisham.weather.location

import androidx.annotation.RequiresPermission
import com.hisham.weather.home.domain.location.LocationDelegate
import javax.inject.Inject

/**
 * Implementation of [LocationDelegate]
 */
class LocationDelegateImpl @Inject constructor(
    private val locationManager: LocationManager,
) : LocationDelegate {

    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    override suspend fun getLocation(): Pair<Double, Double>? {
        if (locationManager.initLocationIfPermissionGranted()) {
            locationManager.getLocation()
        }
        return null
    }
}
