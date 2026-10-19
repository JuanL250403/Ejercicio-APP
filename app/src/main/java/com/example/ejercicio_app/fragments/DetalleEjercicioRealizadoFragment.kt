package com.example.ejercicio_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.ejercicio_app.R
import com.example.ejercicio_app.databinding.FragmentDetalleEjercicioRealizadoBinding
import com.example.ejercicio_app.models.DetalleEjercicioActualViewModel
import com.example.ejercicio_app.models.DetalleEjercicioRealizadoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.withIndex

@AndroidEntryPoint
class DetalleEjercicioRealizadoFragment : Fragment() {

    private var _binding: FragmentDetalleEjercicioRealizadoBinding? = null

    private val binding get() = _binding!!

    private val vm: DetalleEjercicioRealizadoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetalleEjercicioRealizadoBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString("ejercicioId") ?: ""

        vm.obtenerEjercicio(id)

        vm.ejercicio.observe(viewLifecycleOwner) { e ->
            binding.tvNombreEjercicio.text = e.ejercicio.name
            binding.tvPeso.text = e.peso.toString()
            binding.tvReps.text = e.repeticiones.toString()
            binding.tvSeries.text = e.series.toString()
            binding.tvDescanso.text = e.descanso.toString()

            cargarInstrucciones()
            cargarVideo()
        }
    }

    private fun cargarInstrucciones() {

        val instrucciones =
            vm.ejercicio.value?.ejercicio?.instructionSteps?.get("es") ?: emptyList()

        var listaInstrucciones: String = ""

        for ((i, instruccion) in instrucciones.withIndex()) {
            listaInstrucciones += "${i + 1}. $instruccion\n\n"
        }

        binding.tvInstrucciones.text = listaInstrucciones
    }

    private fun cargarVideo() {
        Glide.with(requireContext())
            .load("file:///android_asset/${vm.ejercicio.value?.ejercicio?.gifUrl!!}")
            .error(R.drawable.ic_no_imagen)
            .into(binding.ivVideoDemos)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}