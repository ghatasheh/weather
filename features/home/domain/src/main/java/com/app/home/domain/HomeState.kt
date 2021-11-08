package com.app.home.domain

class HomeState(
    val loading: Boolean = false,
    val success: Boolean? = null,
    val errorMessage: String? = null
) {

    fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()

    class Builder(state: HomeState) {
        var loading: Boolean = state.loading
        var success: Boolean? = state.success
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
