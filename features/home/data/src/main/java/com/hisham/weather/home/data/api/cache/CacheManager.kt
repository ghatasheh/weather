package com.hisham.weather.home.data.api.cache

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.hisham.weather.home.domain.entities.WeatherUiData
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * A key-value storage to handle caching [WeatherUiData] based on [SharedPreferences]
 */
class CacheManager @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson,
) {

    /**
     * Cache [WeatherUiData]
     */
    fun store(data: WeatherUiData) {
        sharedPreferences.edit {
            putString(WEATHER_DATA_KEY, gson.toJson(data))
            putLong(TIMESTAMP_KEY, System.currentTimeMillis())
        }
    }

    /**
     * @return [WeatherUiData] if exists and not expired
     */
    fun get(): WeatherUiData? {
        return if (isValid()) {
            val json = sharedPreferences.getString(WEATHER_DATA_KEY, null)
            gson.fromJson(json, WeatherUiData::class.java)
        } else {
            invalidate()
            null
        }
    }

    /**
     * Check if the [WeatherUiData] is not expired
     */
    private fun isValid(): Boolean {
        val storedTime = sharedPreferences.getLong(TIMESTAMP_KEY, 0L)
        return storedTime + EXPIRY_TIME > System.currentTimeMillis()
    }

    /**
     * Remove [WeatherUiData] from cache
     */
    private fun invalidate() {
        sharedPreferences.edit {
            putString(WEATHER_DATA_KEY, null)
        }
    }

    companion object {
        private const val WEATHER_DATA_KEY = "weather_data"
        private const val TIMESTAMP_KEY = "timestamp"
        private val EXPIRY_TIME = TimeUnit.HOURS.toMillis(2)
    }
}
