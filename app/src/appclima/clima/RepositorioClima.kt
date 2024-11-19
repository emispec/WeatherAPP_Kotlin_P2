package com.example.appclima.clima

import com.example.appclima.BuildConfig
import com.example.appclima.ciudad.Ciudad
import com.example.appclima.network.ApiServicio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class RepositorioClima {
    private val api = ApiServicio.crear()

    suspend fun obtenerClimaActual(ciudad: Ciudad): DetallesClima = withContext(Dispatchers.IO) {
        val respuesta = api.obtenerClimaActual(
            ciudad.latitud,
            ciudad.longitud,
            "metric",
            "es",
            BuildConfig.OPEN_WEATHER_MAP_API_KEY
        )

        DetallesClima(
            temperatura = respuesta.main.temp,
            humedad = respuesta.main.humidity,
            estado = respuesta.weather.first().description.capitalize()
        )
    }

    suspend fun obtenerPronostico(ciudad: Ciudad): List<PronosticoClima> = withContext(Dispatchers.IO) {
        val respuesta = api.obtenerPronostico(
            ciudad.latitud,
            ciudad.longitud,
            "current,minutely,hourly,alerts",
            "metric",
            "es",
            "TU_API_KEY"
        )
        val formatoFecha = SimpleDateFormat("dd/MM", Locale.getDefault())
        respuesta.daily.take(7).map { dia ->
            PronosticoClima(
                fecha = formatoFecha.format(Date(dia.dt * 1000)),
                tempMinima = dia.temp.min,
                tempMaxima = dia.temp.max
            )
        }
    }
}
