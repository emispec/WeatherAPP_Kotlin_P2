package com.example.appclima

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appclima.ciudad.VistaCiudad
import com.example.appclima.clima.VistaClima
import com.example.appclima.util.PreferenciasUsuario

class ActividadPrincipal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ciudadGuardada = PreferenciasUsuario.obtenerCiudadGuardada(this)

        if (ciudadGuardada == null) {
            val intent = Intent(this, VistaCiudad::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, VistaClima::class.java)
            intent.putExtra("ciudad", ciudadGuardada)
            startActivity(intent)
        }
        finish()
    }
}
