package com.example.appclima.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat

object GestionUbicacion {
    fun obtenerUbicacionActual(contexto: Context): Location {
        val locationManager = contexto.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            throw SecurityException("Permiso de ubicación negado")
        }
        val providers = locationManager.getProviders(true)
        var mejorUbicacion: Location? = null
        for (provider in providers) {
            val ubicacion = locationManager.getLastKnownLocation(provider)
            if (ubicacion != null && (mejorUbicacion == null || ubicacion.accuracy < mejorUbicacion.accuracy)) {
                mejorUbicacion = ubicacion
            }
        }
        return mejorUbicacion ?: throw Exception("No se pudo obtener la ubicación")
    }
}
