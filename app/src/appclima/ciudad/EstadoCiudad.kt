package com.example.appclima.ciudad

data class EstadoCiudad(
    val ciudades: List<Ciudad> = emptyList(),
    val cargando: Boolean = false,
    val error: String? = null
)
