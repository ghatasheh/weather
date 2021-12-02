package com.hisham.weather.design.widgets

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.hisham.weather.design.R

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionRequestContent(
    permission: String,
    permissionRequestMessage: String,
    permissionNotAvailableMessage: String,
    navigateToDestination: () -> Unit,
    navigateToSettingsScreen: () -> Unit,
) {
    // Track if the user doesn't want to see the rationale any more.
    var doNotShowRationale by rememberSaveable { mutableStateOf(false) }

    val permissionState = rememberPermissionState(permission)
    PermissionRequired(
        permissionState = permissionState,
        permissionNotGrantedContent = {
            if (doNotShowRationale) {
                Text(stringResource(R.string.feature_not_available))
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(permissionRequestMessage)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Button(onClick = { permissionState.launchPermissionRequest() }) {
                            Text(stringResource(R.string.ok))
                        }
                        Spacer(Modifier.width(8.dp))
                        Button(onClick = { doNotShowRationale = true }) {
                            Text(stringResource(R.string.nope))
                        }
                    }
                }
            }
        },
        permissionNotAvailableContent = {
            Column {
                Text(permissionNotAvailableMessage)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = navigateToSettingsScreen) {
                    Text(stringResource(R.string.open_settings))
                }
            }
        }
    ) {
        Text(stringResource(R.string.permission_granted))
        navigateToDestination()
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MultiplePermissionRequestContent(
    permissions: List<String>,
    permissionRequestMessage: String,
    permissionNotAvailableMessage: String,
    navigateToDestination: () -> Unit,
) {
    val multiplePermissionsState = rememberMultiplePermissionsState(permissions)

    val context = LocalContext.current
    val packageName = context.packageName
    MultiplePermissionRequestContent_(
        multiplePermissionsState = multiplePermissionsState,
        permissionRequestMessage = permissionRequestMessage,
        permissionNotAvailableMessage = permissionNotAvailableMessage,
        navigateToDestination = navigateToDestination,
        navigateToSettingsScreen = {
            context.startActivity(
                Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", packageName, null)
                )
            )
        }
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun MultiplePermissionRequestContent_(
    multiplePermissionsState: MultiplePermissionsState,
    permissionRequestMessage: String,
    permissionNotAvailableMessage: String,
    navigateToDestination: () -> Unit,
    navigateToSettingsScreen: () -> Unit,
) {
    // Track if the user doesn't want to see the rationale any more.
    var doNotShowRationale by rememberSaveable { mutableStateOf(false) }

    when {
        // If all permissions are granted, then show screen with the feature enabled
        multiplePermissionsState.allPermissionsGranted -> {
            Text(stringResource(R.string.permission_granted))
            navigateToDestination()
        }
        // If the user denied any permission but a rationale should be shown, or the user sees
        // the permissions for the first time, explain why the feature is needed by the app and
        // allow the user decide if they don't want to see the rationale any more.
        multiplePermissionsState.shouldShowRationale ||
                !multiplePermissionsState.permissionRequested ->
        {
            if (doNotShowRationale) {
                Text(stringResource(R.string.feature_not_available))
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    val revokedPermissionsText = getPermissionsText(
                        multiplePermissionsState.revokedPermissions
                    )
                    Text(permissionRequestMessage)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Button(
                            onClick = {
                                multiplePermissionsState.launchMultiplePermissionRequest()
                            }
                        ) {
                            Text(stringResource(R.string.ok))
                        }
                        Spacer(Modifier.width(8.dp))
                        Button(onClick = { doNotShowRationale = true }) {
                            Text(stringResource(R.string.nope))
                        }
                    }
                }
            }
        }
        // If the criteria above hasn't been met, the user denied some permission. Let's present
        // the user with a FAQ in case they want to know more and send them to the Settings screen
        // to enable them the future there if they want to.
        else -> {
            Column {
                val revokedPermissionsText = getPermissionsText(
                    multiplePermissionsState.revokedPermissions
                )
                Text(
                    "$revokedPermissionsText denied. See this FAQ with " +
                            "information about why we need this permission. Please, grant us " +
                            "access on the Settings screen."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = navigateToSettingsScreen) {
                    Text("Open Settings")
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
private fun getPermissionsText(permissions: List<PermissionState>): String {
    val revokedPermissionsSize = permissions.size
    if (revokedPermissionsSize == 0) return ""

    val textToShow = StringBuilder().apply {
        append("The ")
    }

    for (i in permissions.indices) {
        textToShow.append(permissions[i].permission)
        when {
            revokedPermissionsSize > 1 && i == revokedPermissionsSize - 2 -> {
                textToShow.append(", and ")
            }
            i == revokedPermissionsSize - 1 -> {
                textToShow.append(" ")
            }
            else -> {
                textToShow.append(", ")
            }
        }
    }
    textToShow.append(if (revokedPermissionsSize == 1) "permission is" else "permissions are")
    return textToShow.toString()
}