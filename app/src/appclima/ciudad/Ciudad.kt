package com.example.appclima.ciudad

import java.io.Serializable

data class Ciudad(
    val nombre: String,
    val latitud: Double,
    val longitud: Double
) : Serializable
