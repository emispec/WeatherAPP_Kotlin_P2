package com.example.appclima.clima

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appclima.ciudad.Ciudad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VistaModeloClima(private val ciudad: Ciudad) : ViewModel() {
    private val _estado = MutableStateFlow(EstadoClima())
    val estado: StateFlow<EstadoClima> get() = _estado
    private val repositorio = RepositorioClima()

    init {
        procesarIntencion(IntencionClima.CargarClima)
    }

    fun procesarIntencion(intencion: IntencionClima) {
        when (intencion) {
            is IntencionClima.CargarClima -> cargarClima()
        }
    }

    private fun cargarClima() {
        viewModelScope.launch {
            _estado.value = _estado.value.copy(cargando = true)
            try {
                val climaActual = repositorio.obtenerClimaActual(ciudad)
                val pronostico = repositorio.obtenerPronostico(ciudad)
                _estado.value = _estado.value.copy(
                    climaActual = climaActual,
                    pronostico = pronostico,
                    cargando = false
                )
            } catch (e: Exception) {
                _estado.value = _estado.value.copy(error = "Error al cargar el clima", cargando = false)
            }
        }
    }
}
