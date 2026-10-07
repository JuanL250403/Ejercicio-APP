package com.example.ejercicio_app.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ejercicio_app.R
import com.example.ejercicio_app.data.network.dto.request.EjercicioActualRequest
import com.example.ejercicio_app.data.ui.EjercicioActualUiModel
import com.example.ejercicio_app.databinding.EjercicioItemBinding

class RutinaActualEjerciciosAdapter(
    private val verDetalle: (String) -> Unit
) : RecyclerView.Adapter<RutinaActualEjerciciosAdapter.RutinaActualEjerciciosViewHolder>() {
    class RutinaActualEjerciciosViewHolder(val binding: EjercicioItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var ejercicios: List<EjercicioActualUiModel> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RutinaActualEjerciciosViewHolder {
        val binding =
            EjercicioItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RutinaActualEjerciciosViewHolder(binding)

    }

    override fun onBindViewHolder(holder: RutinaActualEjerciciosViewHolder, position: Int) {
        val b = holder.binding

        Glide.with(b.ivEjercicio.context)
            .load("file:///android_asset/${ejercicios[position].ejercicio.image}")
            .error(R.drawable.ic_no_imagen)
            .into(b.ivEjercicio)

        b.tvNombre.text = ejercicios[position].ejercicio.name
        b.tvReps.text = ejercicios[position].repeticiones.toString()
        b.tvDescanso.text = ejercicios[position].descanso.toString()
        b.tvSeries.text = ejercicios[position].series.toString()
        b.tvPeso.text = ejercicios[position].peso.toString()

        b.root.setOnClickListener {
            verDetalle(ejercicios[position].id!!)
        }
    }

    fun actualizarEjercicios(ejercicios: List<EjercicioActualUiModel>) {
        this.ejercicios = ejercicios
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int = ejercicios.size
}