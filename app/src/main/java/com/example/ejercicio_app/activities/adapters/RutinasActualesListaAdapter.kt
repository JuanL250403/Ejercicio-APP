package com.example.ejercicio_app.activities.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicio_app.activities.RutinasActuales
import com.example.ejercicio_app.data.response.RutinaActualResponse
import com.example.ejercicio_app.databinding.RutinaItemBinding

class RutinasActualesListaAdapter() : RecyclerView.Adapter<RutinasActualesListaAdapter.RutinaListaViewHolder>() {

    private var rutinas: List<RutinaActualResponse> = listOf()

    class RutinaListaViewHolder(val binding: RutinaItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RutinaListaViewHolder {
        val binding = RutinaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RutinaListaViewHolder(binding)

    }

    override fun onBindViewHolder(holder: RutinaListaViewHolder, position: Int) {
        val b = holder.binding

        b.tvNombre.text = rutinas[position].nombre
        b.tvParteAnatomica.text = rutinas[position].parteAnatomica
    }

    fun actualizarLista(rutinas: List<RutinaActualResponse>) {
        this.rutinas = rutinas
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int  = rutinas.size
}