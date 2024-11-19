package com.example.appclima.ciudad

import android.content.Context
import android.location.Location
import android.location.LocationManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appclima.util.GestionUbicacion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VistaModeloCiudad : ViewModel() {
    private val _estado = MutableStateFlow(EstadoCiudad())
    val estado: StateFlow<EstadoCiudad> get() = _estado
    private val repositorio = RepositorioCiudad()

    fun procesarIntencion(intencion: IntencionCiudad) {
        when (intencion) {
            is IntencionCiudad.BuscarCiudad -> buscarCiudad(intencion.consulta)
            is IntencionCiudad.UsarGeolocalizacion -> usarGeolocalizacion(intencion.contexto)
            is IntencionCiudad.SeleccionarCiudad -> seleccionarCiudad(intencion.ciudad)
        }
    }

    private fun buscarCiudad(consulta: String) {
        viewModelScope.launch {
            _estado.value = _estado.value.copy(cargando = true)
            try {
                val ciudades = repositorio.buscarCiudades(consulta)
                _estado.value = _estado.value.copy(ciudades = ciudades, cargando = false)
            } catch (e: Exception) {
                _estado.value = _estado.value.copy(error = "Error al buscar ciudades", cargando = false)
            }
        }
    }

    private fun usarGeolocalizacion(contexto: Context) {
        viewModelScope.launch {
            _estado.value = _estado.value.copy(cargando = true)
            try {
                val ubicacion = GestionUbicacion.obtenerUbicacionActual(contexto)
                val ciudad = repositorio.obtenerCiudadPorCoordenadas(ubicacion.latitude, ubicacion.longitude)
                _estado.value = _estado.value.copy(ciudades = listOf(ciudad), cargando = false)
            } catch (e: Exception) {
                _estado.value = _estado.value.copy(error = "Error al obtener geolocalización", cargando = false)
            }
        }
    }

    private fun seleccionarCiudad(ciudad: Ciudad) {
    }
}
