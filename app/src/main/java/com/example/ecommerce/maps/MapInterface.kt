package com.example.ecommerce.maps

/*
 * Created by admin on 09-01-2018.
 */

import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition

interface MapInterface {
    fun onMapReady(googleMap : GoogleMap)

    fun onAutoCompleteListener(place : Place)

    fun onCameraIdle(cameraPosition : CameraPosition)

    fun onCameraMoveStarted()
}
