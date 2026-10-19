package com.example.ejercicio_app.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicio_app.data.network.dto.response.RutinaActualResponse
import com.example.ejercicio_app.data.ui.ParteAnatomica
import com.example.ejercicio_app.data.ui.RutinaActualUiModel
import com.example.ejercicio_app.data.ui.RutinaRealizadaUiModel
import com.example.ejercicio_app.databinding.RutinaItemBinding
import com.example.ejercicio_app.databinding.RutinaRealizadaItemBinding
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

class RutinasRealizadasListaAdapter(
    private val verDetalle: (String) -> Unit
) : RecyclerView.Adapter<RutinasRealizadasListaAdapter.RutinaListaViewHolder>() {

    private var rutinas: List<RutinaRealizadaUiModel> = listOf()

    class RutinaListaViewHolder(val binding: RutinaRealizadaItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RutinaListaViewHolder {
        val binding = RutinaRealizadaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RutinaListaViewHolder(binding)

    }

    override fun onBindViewHolder(holder: RutinaListaViewHolder, position: Int) {
        val b = holder.binding

        b.tvNombre.text = rutinas[position].nombre
        b.tvParteAnatomica.text = ParteAnatomica.obtenerParteAnatomica(rutinas[position].parteAnatomica)

        val dia = DayOfWeek.of(rutinas[position].diaSemana).getDisplayName(TextStyle.FULL, Locale.getDefault())
        val fecha = rutinas[position].fechaRealizacion.toString()

        b.tvDiaFecha.text = "$dia - $fecha"

        b.root.setOnClickListener {
            if(!rutinas[position].id.isNullOrBlank()) verDetalle(rutinas[position].id ?: "")
        }
    }

    fun actualizarLista(rutinas: List<RutinaRealizadaUiModel>) {
        this.rutinas = rutinas
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int  = rutinas.size
}