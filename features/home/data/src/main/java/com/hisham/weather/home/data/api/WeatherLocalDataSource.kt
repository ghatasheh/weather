package com.hisham.weather.home.data.api

import com.hisham.weather.home.data.api.cache.CacheManager
import com.hisham.weather.home.domain.entities.WeatherUiData
import javax.inject.Inject

/**
 * DataSource class to handle reading/writing from [CacheManager]
 */
class WeatherLocalDataSource @Inject constructor(
    private val cacheManager: CacheManager,
) {

    fun getWeatherIfExists(): WeatherUiData? = cacheManager.get()

    fun store(weatherUiData: WeatherUiData) = cacheManager.store(weatherUiData)
}
