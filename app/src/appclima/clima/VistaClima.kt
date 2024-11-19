package com.example.appclima.clima

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.appclima.R
import com.example.appclima.ciudad.Ciudad
import com.example.appclima.ciudad.VistaCiudad
import com.example.appclima.util.PreferenciasUsuario
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class VistaClima : AppCompatActivity() {

    private lateinit var vistaModelo: VistaModeloClima
    private lateinit var ciudad: Ciudad
    private lateinit var textoTemperatura: TextView
    private lateinit var textoHumedad: TextView
    private lateinit var textoEstado: TextView
    private lateinit var listaPronostico: ListView
    private lateinit var botonCambiarCiudad: Button
    private lateinit var botonCompartir: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vista_clima)

        ciudad = intent.getSerializableExtra("ciudad") as Ciudad

        vistaModelo = VistaModeloClima(ciudad)

        textoTemperatura = findViewById(R.id.textoTemperatura)
        textoHumedad = findViewById(R.id.textoHumedad)
        textoEstado = findViewById(R.id.textoEstado)
        listaPronostico = findViewById(R.id.listaPronostico)
        botonCambiarCiudad = findViewById(R.id.botonCambiarCiudad)
        botonCompartir = findViewById(R.id.botonCompartir)

        botonCambiarCiudad.setOnClickListener {
            PreferenciasUsuario.eliminarCiudadGuardada(this)
            val intent = Intent(this, VistaCiudad::class.java)
            startActivity(intent)
            finish()
        }

        botonCompartir.setOnClickListener {
            val mensaje = "Clima en ${ciudad.nombre}: ${textoTemperatura.text}, ${textoEstado.text}"
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, mensaje)
            startActivity(Intent.createChooser(intent, "Compartir Clima"))
        }

        lifecycleScope.launch {
            vistaModelo.estado.collect { estado ->
                if (estado.cargando) {
                } else {
                }
                if (estado.error != null) {
                    Toast.makeText(this@VistaClima, estado.error, Toast.LENGTH_SHORT).show()
                }
                estado.climaActual?.let { clima ->
                    textoTemperatura.text = "${clima.temperatura}°C"
                    textoHumedad.text = "Humedad: ${clima.humedad}%"
                    textoEstado.text = clima.estado
                }
                val adaptadorPronostico = AdaptadorPronostico(this@VistaClima, estado.pronostico)
                listaPronostico.adapter = adaptadorPronostico
            }
        }
    }
}
