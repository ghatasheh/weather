package com.hisham.weather.home.data.api.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hisham.weather.home.domain.location.LocationDelegate
import com.hisham.weather.home.domain.usecases.FetchWeatherUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

/**
 * Worker to handle fetch user location and weather data
 */
@HiltWorker
class FetchWeatherWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val fetchWeatherUseCase: FetchWeatherUseCase,
    private val locationDelegate: LocationDelegate,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Timber.tag("FetchWeatherWorker").d("doWork")
        val location = locationDelegate.getLocation() ?: return Result.retry()

        val result = fetchWeatherUseCase.execute(location.first, location.second)

        if (result.isSuccess) {
            Timber.tag("FetchWeatherWorker").d("doWork success")
            return Result.success()
        }

        Timber.tag("FetchWeatherWorker").d("doWork failure")
        return Result.failure()
    }
}
