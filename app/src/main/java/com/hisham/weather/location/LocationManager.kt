package com.hisham.weather.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationManager @Inject constructor(
    @ApplicationContext context: Context,
) {

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    private val locationCallback by lazy {
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                println("Hisham onLocationResult: $locationResult")
                locationResult?.lastLocation
            }

            override fun onLocationAvailability(locationAvailability: LocationAvailability) {
                println("Hisham location available: ${locationAvailability.isLocationAvailable}")
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

    @SuppressLint("MissingPermission")
    suspend fun getLocation(): Pair<Double, Double>? {
        return fusedLocationClient.lastLocation.await()?.run {
            Pair(latitude, longitude)
        }
    }

    suspend fun location() = suspendCancellableCoroutine<Pair<Double, Double>> {
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                println("Hisham onLocationResult: $locationResult")
            }

            override fun onLocationAvailability(locationAvailability: LocationAvailability) {
                println("Hisham location available: ${locationAvailability.isLocationAvailable}")
            }
        }
    }


    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates() {
        Log.d(TAG, "Starting")
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun removeLocationUpdates() {
        Log.d(TAG, "Stopping")
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    companion object {
        private const val TAG = "LocationManager"
    }
}