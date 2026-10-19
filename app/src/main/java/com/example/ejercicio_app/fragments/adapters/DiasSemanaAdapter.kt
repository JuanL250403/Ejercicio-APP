package com.example.ejercicio_app.fragments.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.R
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicio_app.data.ui.DiaSemanaDetalleUiModel
import com.example.ejercicio_app.data.ui.DiaSemanaUiModel
import com.example.ejercicio_app.databinding.DiaSemanaItemBinding
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class DiasSemanaAdapter(
    private val verDetalle: (String, Int) -> Unit
) : RecyclerView.Adapter<DiasSemanaAdapter.DiaSemanaViewHolder>() {
    class DiaSemanaViewHolder(val binding: DiaSemanaItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var dias: List<DiaSemanaUiModel> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DiaSemanaViewHolder {
        val binding =
            DiaSemanaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DiaSemanaViewHolder(binding)

    }

    override fun onBindViewHolder(holder: DiaSemanaViewHolder, position: Int) {
        val b = holder.binding

        val dia = dias.firstOrNull { d -> d.diaSemana == position + 1 }

        b.tvDia.text =
            DayOfWeek.of(position + 1).getDisplayName(TextStyle.FULL, Locale.getDefault())

        b.tvRutina.text = dia?.rutina ?: "Sin rutina"

        if (dia != null) {
            b.root.alpha = 1.0f
            b.tvEstado.visibility = View.VISIBLE
            if (LocalDate.now().dayOfWeek.value == dia.diaSemana && !dia.estado) {
                b.tvEstado.text = "En curso"
            } else {
                b.tvEstado.text = if (dia.estado) "Completado" else "No realizada"
            }
        } else {
            b.root.alpha = 0.6f
            b.tvEstado.visibility = View.GONE
        }

        b.root.setOnClickListener {

            verDetalle(dia?.id ?: "", position + 1)

            
        }


    }

    fun actualizarDias(dias: List<DiaSemanaUiModel>) {
        this.dias = dias
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int = 7
}