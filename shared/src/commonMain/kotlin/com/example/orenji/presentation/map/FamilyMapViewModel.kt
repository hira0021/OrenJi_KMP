package com.example.orenji.presentation.map

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class FamilyMapState(
    val permissionGranted: Boolean = false,
    val isRequestingPermission: Boolean = true,
    val userLatitude: Double = -6.2088,
    val userLongitude: Double = 106.8456,
)

class FamilyMapViewModel : ViewModel() {

    private val _state = MutableStateFlow(FamilyMapState())
    val state: StateFlow<FamilyMapState> = _state.asStateFlow()

    fun onPermissionGranted() {
        _state.value = _state.value.copy(
            permissionGranted = true,
            isRequestingPermission = false,
        )
    }

    fun onPermissionDenied() {
        _state.value = _state.value.copy(
            permissionGranted = false,
            isRequestingPermission = false,
        )
    }

    fun onRequestPermission() {
        _state.value = _state.value.copy(
            isRequestingPermission = true,
        )
    }

    fun updateLocation(latitude: Double, longitude: Double) {
        _state.value = _state.value.copy(
            userLatitude = latitude,
            userLongitude = longitude,
        )
    }
}