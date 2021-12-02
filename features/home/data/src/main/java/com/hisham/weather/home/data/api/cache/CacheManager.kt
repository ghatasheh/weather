package com.hisham.weather.home.data.api.cache

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.hisham.weather.home.domain.entities.WeatherUiData
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CacheManager @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson,
) {

    fun store(data: WeatherUiData) {
        sharedPreferences.edit {
            putString(WEATHER_DATA_KEY, gson.toJson(data))
            putLong(TIMESTAMP_KEY, System.currentTimeMillis())
        }
    }

    fun get(): WeatherUiData? {
        return if (isValid()) {
            val json = sharedPreferences.getString(WEATHER_DATA_KEY, null)
            gson.fromJson(json, WeatherUiData::class.java)
        } else {
            invalidate()
            null
        }
    }

    private fun isValid(): Boolean {
        val storedTime = sharedPreferences.getLong(TIMESTAMP_KEY, 0L)
        return storedTime + EXPIRY_TIME > System.currentTimeMillis()
    }

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