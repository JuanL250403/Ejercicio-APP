package com.example.ejercicio_app.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicio_app.data.network.dto.response.RutinaActualResponse
import com.example.ejercicio_app.data.ui.ParteAnatomica
import com.example.ejercicio_app.data.ui.RutinaActualUiModel
import com.example.ejercicio_app.databinding.RutinaItemBinding

class RutinasActualesListaAgregarDiaAdapter(
    private val agregarRutina: (String) -> Unit
) : RecyclerView.Adapter<RutinasActualesListaAgregarDiaAdapter.RutinaListaViewHolder>() {

    private var rutinas: List<RutinaActualUiModel> = listOf()

    class RutinaListaViewHolder(val binding: RutinaItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RutinaListaViewHolder {
        val binding = RutinaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RutinaListaViewHolder(binding)

    }

    override fun onBindViewHolder(holder: RutinaListaViewHolder, position: Int) {
        val b = holder.binding

        b.tvNombre.text = rutinas[position].nombre
        b.tvParteAnatomica.text = ParteAnatomica.obtenerParteAnatomica(rutinas[position].parteAnatomica)

        b.root.setOnClickListener {
            agregarRutina(rutinas[position].id!!)
        }
    }

    fun actualizarLista(rutinas: List<RutinaActualUiModel>) {
        this.rutinas = rutinas
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int  = rutinas.size
}