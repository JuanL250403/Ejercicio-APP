package com.example.ejercicio_app.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ejercicio_app.R
import com.example.ejercicio_app.data.ui.EjercicioDataSetUiModel
import com.example.ejercicio_app.databinding.BuscarEjercicioItemBinding

class BuscarEjercicioAgregarAdapter
    (
    private val agregarEjercicio: (String) -> Unit
    ) : RecyclerView.Adapter<BuscarEjercicioAgregarAdapter.BuscarEjercicioAgregarAdapterViewHolder>() {
    class BuscarEjercicioAgregarAdapterViewHolder(val binding: BuscarEjercicioItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var ejercicios: List<EjercicioDataSetUiModel> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BuscarEjercicioAgregarAdapterViewHolder {
        val binding =
            BuscarEjercicioItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return BuscarEjercicioAgregarAdapterViewHolder(binding)

    }

    override fun onBindViewHolder(holder: BuscarEjercicioAgregarAdapterViewHolder, position: Int) {
        val b = holder.binding

        Glide.with(b.imgEjercicio.context)
            .load("file:///android_asset/${ejercicios[position].image}")
            .error(R.drawable.ic_no_imagen)
            .into(b.imgEjercicio)

        b.tvNombreEjercicio.text = ejercicios[position].name
        b.tvCategoria.text = ejercicios[position].category
        b.tvEquipamiento.text = ejercicios[position].equipment
        b.tvTarget.text = ejercicios[position].target

        b.root.setOnClickListener {
            agregarEjercicio(ejercicios[position].id)
        }
    }

    fun actualizarEjercicios(ejercicios: List<EjercicioDataSetUiModel>) {
        this.ejercicios = ejercicios
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int = ejercicios.size
}