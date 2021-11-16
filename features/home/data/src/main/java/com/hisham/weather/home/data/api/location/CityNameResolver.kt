package com.hisham.weather.home.data.api.location

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.hisham.weather.home.domain.location.CityNameResolver
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject

class CityNameResolverImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : CityNameResolver {

    override fun cityNameByLatLng(lat: Double, lng: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: List<Address> = geocoder.getFromLocation(lat, lng, 1)

        return addresses[0].getAddressLine(0)
    }
}
