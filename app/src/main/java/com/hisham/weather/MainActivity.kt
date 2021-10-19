/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hisham.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hisham.weather.home.domain.HomeState
import com.hisham.weather.home.presentation.HomeEvent
import com.hisham.weather.home.presentation.HomeScreen
import com.hisham.weather.home.presentation.HomeViewModel
import com.hisham.weather.navigation.HomeDirection
import com.hisham.weather.navigation.NavigationManager
import com.hisham.weather.ui.theme.WeatherTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WeatherTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = HomeDirection.Home.destination,
                ) {
                    composable(HomeDirection.Home.destination) {
                        val vm = hiltViewModel<HomeViewModel>()
                        HomeScreen(
                            viewModel = vm,
                        )
                    }
                }

                navigationManager.commands.collectAsState().value.also { command ->
                    if (command.destination.isNotEmpty()) {
                        navController.navigate(command.destination)
                    }
                }
            }
        }
    }
}