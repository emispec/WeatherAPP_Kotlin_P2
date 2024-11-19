package com.example.appclima.network

import com.example.appclima.ciudad.RespuestaCiudad
import com.example.appclima.clima.RespuestaClimaActual
import com.example.appclima.clima.RespuestaPronostico
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServicio {

    @GET("geo/1.0/direct")
    suspend fun buscarCiudad(
        @Query("q") consulta: String,
        @Query("limit") limite: String,
        @Query("lang") idioma: String,
        @Query("appid") apiKey: String
    ): List<RespuestaCiudad>

    @GET("geo/1.0/reverse")
    suspend fun buscarCiudadPorCoordenadas(
        @Query("lat") latitud: Double,
        @Query("lon") longitud: Double,
        @Query("limit") limite: String,
        @Query("lang") idioma: String,
        @Query("appid") apiKey: String
    ): List<RespuestaCiudad>

    @GET("data/2.5/weather")
    suspend fun obtenerClimaActual(
        @Query("lat") latitud: Double,
        @Query("lon") longitud: Double,
        @Query("units") unidades: String,
        @Query("lang") idioma: String,
        @Query("appid") apiKey: String
    ): RespuestaClimaActual

    @GET("data/2.5/onecall")
    suspend fun obtenerPronostico(
        @Query("lat") latitud: Double,
        @Query("lon") longitud: Double,
        @Query("exclude") excluir: String,
        @Query("units") unidades: String,
        @Query("lang") idioma: String,
        @Query("appid") apiKey: String
    ): RespuestaPronostico

    companion object {
        fun crear(): ApiServicio {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiServicio::class.java)
        }
    }
}
