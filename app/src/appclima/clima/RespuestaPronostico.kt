package com.example.appclima.clima

data class RespuestaPronostico(
    val daily: List<DiaPronostico>
) {
    data class DiaPronostico(
        val dt: Long,
        val temp: Temp
    )

    data class Temp(
        val min: Double,
        val max: Double
    )
}
