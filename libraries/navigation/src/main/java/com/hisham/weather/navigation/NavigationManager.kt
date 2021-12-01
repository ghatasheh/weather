package com.hisham.weather.navigation

import kotlinx.coroutines.flow.MutableStateFlow

class NavigationManager {

    var commands = MutableStateFlow(PermissionDirection.Location)

    fun navigate(
        directions: NavigationCommand
    ) {
        commands.value = directions
    }
}
