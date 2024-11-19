package com.example.appclima.clima

data class RespuestaClimaActual(
    val main: Main,
    val weather: List<Weather>
) {
    data class Main(
        val temp: Double,
        val humidity: Int
    )

    data class Weather(
        val description: String
    )
}
