package com.example.appclima.clima

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.appclima.R

class AdaptadorPronostico(
    private val contexto: Context,
    private val pronosticos: List<PronosticoClima>
) : androidx.recyclerview.widget.RecyclerView.Adapter<AdaptadorPronostico.ViewHolder>() {

    class ViewHolder(vista: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(vista) {
        val textoFecha: TextView = vista.findViewById(R.id.textoFecha)
        val textoTempMax: TextView = vista.findViewById(R.id.textoTempMax)
        val textoTempMin: TextView = vista.findViewById(R.id.textoTempMin)
    }

    override fun onCreateViewHolder(padre: ViewGroup, vistaType: Int): ViewHolder {
        val vista = LayoutInflater.from(contexto).inflate(R.layout.item_pronostico, padre, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, posicion: Int) {
        val pronostico = pronosticos[posicion]
        holder.textoFecha.text = pronostico.fecha
        holder.textoTempMax.text = "Máx: ${pronostico.tempMaxima}°C"
        holder.textoTempMin.text = "Mín: ${pronostico.tempMinima}°C"
    }

    override fun getItemCount(): Int {
        return pronosticos.size
    }
}
