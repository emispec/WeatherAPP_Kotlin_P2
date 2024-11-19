package com.example.appclima.ciudad

import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.appclima.R
import com.example.appclima.clima.VistaClima
import com.example.appclima.util.PreferenciasUsuario
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class VistaCiudad : AppCompatActivity() {

    private lateinit var vistaModelo: VistaModeloCiudad
    private lateinit var listaCiudades: ListView
    private lateinit var adaptador: ArrayAdapter<String>
    private val nombresCiudades = mutableListOf<String>()
    private var listaCiudadesObjeto = listOf<Ciudad>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vista_ciudad)

        vistaModelo = VistaModeloCiudad()
        listaCiudades = findViewById(R.id.listaCiudades)
        val entradaBusqueda = findViewById<EditText>(R.id.entradaBusqueda)
        val botonGeo = findViewById<Button>(R.id.botonGeo)

        adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, nombresCiudades)
        listaCiudades.adapter = adaptador

        entradaBusqueda.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val consulta = entradaBusqueda.text.toString()
                vistaModelo.procesarIntencion(IntencionCiudad.BuscarCiudad(consulta))
                true
            } else {
                false
            }
        }

        botonGeo.setOnClickListener {
            vistaModelo.procesarIntencion(IntencionCiudad.UsarGeolocalizacion(this))
        }

        listaCiudades.setOnItemClickListener { _, _, position, _ ->
            val ciudadSeleccionada = listaCiudadesObjeto[position]
            vistaModelo.procesarIntencion(IntencionCiudad.SeleccionarCiudad(ciudadSeleccionada))
            PreferenciasUsuario.guardarCiudad(this, ciudadSeleccionada)
            val intent = Intent(this, VistaClima::class.java)
            intent.putExtra("ciudad", ciudadSeleccionada)
            startActivity(intent)
            finish()
        }

        lifecycleScope.launch {
            vistaModelo.estado.collect { estado ->
                if (estado.cargando) {
                } else {
                }
                if (estado.error != null) {
                    Toast.makeText(this@VistaCiudad, estado.error, Toast.LENGTH_SHORT).show()
                }
                listaCiudadesObjeto = estado.ciudades
                nombresCiudades.clear()
                nombresCiudades.addAll(estado.ciudades.map { it.nombre })
                adaptador.notifyDataSetChanged()
            }
        }
    }
}
