package com.hisham.weather.home.domain

import com.hisham.weather.home.domain.entities.WeatherUiData

/**
 * Class represents [HomeScreen] state
 */
class HomeState(
    val loading: Boolean = false,
    val success: WeatherUiData? = null,
    val errorMessage: String? = null
) {

    fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()

    class Builder(state: HomeState) {
        var loading: Boolean = state.loading
        var success: WeatherUiData? = state.success
        var errorMessage: String? = state.errorMessage

        fun build(): HomeState {
            return HomeState(
                loading = loading,
                success = success,
                errorMessage = errorMessage
            )
        }
    }
}
