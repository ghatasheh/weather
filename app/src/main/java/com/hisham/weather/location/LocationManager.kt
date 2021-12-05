package com.hisham.weather.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

/**
 * Class to handle integration with [LocationServices]
 */
class LocationManager @Inject constructor(
    @ApplicationContext context: Context,
) {

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    private val locationCallback by lazy {
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                Timber.tag(TAG).d("onLocationResult $locationResult")
                locationResult?.lastLocation?.let {
                    Timber.tag(TAG)
                        .d("onLocationResult lastLocation ${it.latitude} | ${it.longitude}")
                }
            }

            override fun onLocationAvailability(locationAvailability: LocationAvailability) {
                Timber.tag(TAG).d("onLocationAvailability $locationAvailability")
            }
        }
    }

    private val locationRequest by lazy {
        LocationRequest.create().apply {
            interval = 600000
            fastestInterval = 300000
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
    }

    init {
        initLocationIfPermissionGranted()
    }

    /**
     * Start listening to user location if the location permissions were granted
     */
    fun initLocationIfPermissionGranted(): Boolean {
        val permGranted = ContextCompat.checkSelfPermission(
            fusedLocationClient.applicationContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (permGranted) {
            requestLocationUpdates()
        } else {
            removeLocationUpdates()
        }

        return permGranted
    }

    /**
     *
     * @return Last known location for the user
     */
    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    suspend fun getLocation(): Pair<Double, Double>? {
        return fusedLocationClient.lastLocation.await()?.run {
            Pair(latitude, longitude)
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates() {
        Timber.tag(TAG).d("requestLocationUpdates")
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun removeLocationUpdates() {
        Timber.tag(TAG).d("removeLocationUpdates")
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    companion object {
        private const val TAG = "LocationManager"
    }
}
