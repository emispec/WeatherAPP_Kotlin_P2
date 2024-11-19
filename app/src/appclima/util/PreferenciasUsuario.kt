package com.example.appclima.util

import android.content.Context
import com.example.appclima.ciudad.Ciudad

object PreferenciasUsuario {
    private const val NOMBRE_PREFERENCIAS = "preferencias_usuario"
    private const val CIUDAD_NOMBRE = "ciudad_nombre"
    private const val CIUDAD_LATITUD = "ciudad_latitud"
    private const val CIUDAD_LONGITUD = "ciudad_longitud"

    fun guardarCiudad(contexto: Context, ciudad: Ciudad) {
        val preferencias = contexto.getSharedPreferences(NOMBRE_PREFERENCIAS, Context.MODE_PRIVATE)
        with(preferencias.edit()) {
            putString(CIUDAD_NOMBRE, ciudad.nombre)
            putFloat(CIUDAD_LATITUD, ciudad.latitud.toFloat())
            putFloat(CIUDAD_LONGITUD, ciudad.longitud.toFloat())
            apply()
        }
    }

    fun obtenerCiudadGuardada(contexto: Context): Ciudad? {
        val preferencias = contexto.getSharedPreferences(NOMBRE_PREFERENCIAS, Context.MODE_PRIVATE)
        val nombre = preferencias.getString(CIUDAD_NOMBRE, null)
        val latitud = preferencias.getFloat(CIUDAD_LATITUD, Float.NaN)
        val longitud = preferencias.getFloat(CIUDAD_LONGITUD, Float.NaN)

        return if (nombre != null && !latitud.isNaN() && !longitud.isNaN()) {
            Ciudad(nombre, latitud.toDouble(), longitud.toDouble())
        } else {
            null
        }
    }

    fun eliminarCiudadGuardada(contexto: Context) {
        val preferencias = contexto.getSharedPreferences(NOMBRE_PREFERENCIAS, Context.MODE_PRIVATE)
        with(preferencias.edit()) {
            remove(CIUDAD_NOMBRE)
            remove(CIUDAD_LATITUD)
            remove(CIUDAD_LONGITUD)
            apply()
        }
    }
}
