package com.example.appclima.clima

data class EstadoClima(
    val climaActual: DetallesClima? = null,
    val pronostico: List<PronosticoClima> = emptyList(),
    val cargando: Boolean = false,
    val error: String? = null
)
