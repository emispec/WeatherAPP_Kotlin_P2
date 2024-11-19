package com.example.appclima.ciudad

import com.example.appclima.BuildConfig
import com.example.appclima.network.ApiServicio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositorioCiudad {
    private val api = ApiServicio.crear()

    suspend fun buscarCiudades(consulta: String): List<Ciudad> = withContext(Dispatchers.IO) {
        val respuesta = api.buscarCiudad(consulta, "5", "es", "TU_API_KEY")
        respuesta.map { Ciudad(it.name, it.lat, it.lon) }
    }

    suspend fun obtenerCiudadPorCoordenadas(latitud: Double, longitud: Double): Ciudad = withContext(Dispatchers.IO) {
        val respuesta = api.buscarCiudad(consulta, "5", "es", BuildConfig.OPEN_WEATHER_MAP_API_KEY)
        val ciudad = respuesta.first()
        Ciudad(ciudad.name, ciudad.lat, ciudad.lon)
    }
}
