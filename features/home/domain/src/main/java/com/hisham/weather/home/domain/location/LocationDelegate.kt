package com.hisham.weather.home.domain.location

interface LocationDelegate {
    suspend fun getLocation(): Pair<Double, Double>?
}