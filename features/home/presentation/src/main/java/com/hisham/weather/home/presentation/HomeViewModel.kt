package com.hisham.weather.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hisham.weather.home.domain.HomeState
import com.hisham.weather.home.domain.location.LocationDelegate
import com.hisham.weather.home.domain.usecases.FetchWeatherUseCase
import com.hisham.weather.threading.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchWeatherUseCase: FetchWeatherUseCase,
    private val locationDelegate: LocationDelegate,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState(loading = true))
    val uiState: StateFlow<HomeState> = _uiState

    init {
        initialise()
    }

    private fun initialise() {
        viewModelScope.launch {
            val location = locationDelegate.getLocation()
            if (location != null) {
                load(location.first, location.second)
            } else {
                _uiState.value = _uiState.value.build {
                    errorMessage = "Failed to load location"
                    loading = false
                }
            }
        }
    }

    fun handleEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.FetchWeather -> {
                load(event.lat, event.lng)
            }
        }
    }

    private fun load(lat: Double, lng: Double) = viewModelScope.launch(ioDispatcher) {
        _uiState.value = _uiState.value.build { loading = true }

        val result = fetchWeatherUseCase.execute(lat, lng)
        _uiState.value = _uiState.value.build {
            loading = false
            if (result.isSuccess) {
                success = result.getOrNull()
            } else {
                errorMessage = result.exceptionOrNull()?.message
            }
        }
    }
}
