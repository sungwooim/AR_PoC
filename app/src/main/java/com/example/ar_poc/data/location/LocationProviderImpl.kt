package com.example.ar_poc.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.SystemClock
import com.example.ar_poc.Config
import com.example.ar_poc.domain.location.LocationProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.tasks.await

class LocationProviderImpl(
    private val context: Context,
    private val devModeProvider: () -> Boolean = { false }
) : LocationProvider {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    companion object {
        /** Max age of a cached location before we request a fresh fix (3 minutes). */
        private const val MAX_LOCATION_AGE_MS = 3 * 60 * 1000L
    }

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Location? {
        if (devModeProvider()) {
            return Location("Mock").apply {
                latitude = Config.MOCK_LATITUDE
                longitude = Config.MOCK_LONGITUDE
                accuracy = 5f
                time = System.currentTimeMillis()
            }
        }

        return try {
            // 1. Try last-known location first — fast, works indoors
            val lastLocation = fusedLocationClient.lastLocation.await()
            if (lastLocation != null && isLocationFresh(lastLocation) && 
                lastLocation.latitude != 0.0 && lastLocation.longitude != 0.0) {
                return lastLocation
            }

            // 2. Fall back to a fresh high-accuracy fix
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                null
            ).await()
        } catch (e: Exception) {
            null
        }
    }

    private fun isLocationFresh(location: Location): Boolean {
        val ageMs = System.currentTimeMillis() - location.time
        return ageMs < MAX_LOCATION_AGE_MS && location.accuracy < 50f
    }

    override fun getPalaceName(location: Location): String? {
        if (location.provider == "Mock") return "경복궁 (Gyeongbokgung)"

        val lat = location.latitude
        val lng = location.longitude

        // Tightened Gyeongbokgung boundary (measured values from Google Maps)
        // North: 37.5836, South: 37.5765, West: 126.9742, East: 126.9795
        return if (lat in 37.5765..37.5836 && lng in 126.9742..126.9795) {
            "경복궁 (Gyeongbokgung)"
        } else {
            null
        }
    }
}
