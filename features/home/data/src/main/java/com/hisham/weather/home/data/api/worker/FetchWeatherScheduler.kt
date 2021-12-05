package com.hisham.weather.home.data.api.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Class to help updating weather data every 2-hours
 */
class FetchWeatherScheduler @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    /**
     * Run [FetchWeatherWorker] every 2 hours with 2 hours delay to avoid calling the API twice (From Viewmodel and Worker)
     */
    fun schedule() {
        Timber.tag("FetchWeatherScheduler").d("Start scheduling")
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED) // Only on Wifi
            .build()

        val request = PeriodicWorkRequestBuilder<FetchWeatherWorker>(2, TimeUnit.HOURS)
            .setConstraints(constraints)
            // first time it will be fetched when the user starts the app, after that use the worker to update the cache
            .setInitialDelay(2, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                FetchWeatherScheduler::class.java.simpleName,
                ExistingPeriodicWorkPolicy.REPLACE,
                request
            )
    }
}
