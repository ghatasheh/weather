package com.app.navigation

import androidx.navigation.NamedNavArgument

object HomeDirection {

    val Home = object : NavigationCommand {
        override val arguments: List<NamedNavArgument>
            get() = emptyList()

        override val destination: String
            get() = "home"
    }
}
