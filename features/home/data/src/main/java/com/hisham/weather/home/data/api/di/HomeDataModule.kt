package com.hisham.weather.home.data.api.di

import com.hisham.weather.home.data.api.WeatherRepositoryImpl
import com.hisham.weather.home.data.api.location.CityNameResolverImpl
import com.hisham.weather.home.domain.api.WeatherRepository
import com.hisham.weather.home.domain.location.CityNameResolver
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class HomeDataModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindCityNameResolver(impl: CityNameResolverImpl): CityNameResolver
}