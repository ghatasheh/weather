package com.hisham.weather.di

import com.hisham.weather.home.domain.location.LocationDelegate
import com.hisham.weather.location.LocationDelegateImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Singleton
    @Binds
    abstract fun bindsLocationDelegate(impl: LocationDelegateImpl): LocationDelegate
}
