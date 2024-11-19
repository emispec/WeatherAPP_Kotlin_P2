package com.example.appclima.ciudad

import android.content.Context

sealed class IntencionCiudad {
    data class BuscarCiudad(val consulta: String) : IntencionCiudad()
    data class UsarGeolocalizacion(val contexto: Context) : IntencionCiudad()
    data class SeleccionarCiudad(val ciudad: Ciudad) : IntencionCiudad()
}
