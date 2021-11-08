package com.app.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.app.home.domain.HomeState

const val TAG_PROGRESS = "progress"

@Composable
fun HomeScreen(
    state: HomeState,
) {
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
        state.success == true -> {
            Column {
                Button(onClick = {}) {
                    Text("Test")
                }
            }
        }
        state.errorMessage != null -> {
        }
    }
}
