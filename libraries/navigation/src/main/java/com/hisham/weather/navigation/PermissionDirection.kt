package com.hisham.weather.navigation

import androidx.navigation.NamedNavArgument

object PermissionDirection {

    val Location = object : NavigationCommand {
        override val arguments: List<NamedNavArgument>
            get() = emptyList()

        override val destination: String
            get() = "location"
    }
}
