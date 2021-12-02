package com.hisham.weather.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.hisham.weather.home.domain.HomeState
import com.hisham.weather.home.domain.entities.WeatherUiData
import kotlinx.coroutines.launch

const val TAG_PROGRESS = "progress"

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
) {
    val state: HomeState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    when {
        state.loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.primary),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.testTag(TAG_PROGRESS),
                    color = MaterialTheme.colors.secondary
                )
            }
        }
        state.success != null -> {
            HomeContent(state.success!!)
        }
        state.errorMessage != null -> {
            ErrorContent {
                coroutineScope.launch {
                    viewModel.handleEvent(HomeEvent.FetchWeather())
                }
            }
        }
    }
}

@Preview
@Composable
fun ComposablePreview() {
//    ErrorContent {}
    HomeContent(
        data = WeatherUiData(
            cityName = "Dublin",
            date = "October, 19 - 07:26pm",
            dayDegrees = 20,
            nightDegrees = 14,
            currentDegrees = 18,
            feelsLike = 21,
            weatherDescription = "Cloudy",
            iconUrl = "https://openweathermap.org/img/wn/04n@2x.png"
        )
    )
}

@Composable
private fun ErrorContent(retryCallback: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = stringResource(id = R.string.error_message))
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { retryCallback() }) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Composable
private fun HomeContent(data: WeatherUiData) {
    Column {
        Text(text = data.cityName)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = rememberImagePainter(
                    data = data.iconUrl,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = null,
                modifier = Modifier.size(128.dp)
            )
            Column {
                Text(
                    text = stringResource(R.string.degrees_unit, data.currentDegrees),
                    style = MaterialTheme.typography.h3
                )
                Text(text = stringResource(R.string.feels_like, data.feelsLike))
                Text(text = data.weatherDescription)
            }
        }
    }
}
