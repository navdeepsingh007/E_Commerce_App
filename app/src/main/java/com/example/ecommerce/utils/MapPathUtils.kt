package com.example.ecommerce.utils

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline


object MapPathUtils {
    var polylineFinal: Polyline? = null
    // var delegate: GeocodingResponseListener? = null


    fun removePolyline(): Polyline? {
        return polylineFinal
    }


    fun bearingBetweenLocations(sourceLatLng: LatLng, destinationLatLng: LatLng): Float {
        val source = Location("source")
        source.latitude = sourceLatLng.latitude
        source.longitude = sourceLatLng.longitude

        val destination = Location("destination")
        destination.latitude = destinationLatLng.latitude
        destination.longitude = destinationLatLng.longitude
        return source.bearingTo(destination)
    }

}