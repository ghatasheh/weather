package com.hisham.weather.utils

import timber.log.Timber


/**
 * A tree which has empty implementation.
 */
class NoOpTree : Timber.Tree() {
    override fun log(
        priority: Int,
        tag: String?,
        message: String,
        t: Throwable?,
    ) {
    }
}