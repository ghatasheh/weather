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
