package com.localgo.artelabspa.viewmodel

import android.location.Location
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LocationViewModel : ViewModel() {

    private val _locationText = MutableStateFlow("Ubicación no disponible")
    val locationText = _locationText.asStateFlow()

    fun updateLocation(location: Location?) {
        if (location != null) {
            _locationText.value =
                "Lat: ${location.latitude}, Lon: ${location.longitude}"
        } else {
            _locationText.value = "No se pudo obtener ubicación"
        }
    }
}
