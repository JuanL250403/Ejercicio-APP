package com.example.ejercicio_app.fragments

import android.os.Bundle
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ejercicio_app.R
import com.example.ejercicio_app.data.ui.ParteAnatomica
import com.example.ejercicio_app.databinding.FragmentDetalleRutinaRealizadaBinding
import com.example.ejercicio_app.fragments.adapters.RutinaRealizadaEjerciciosAdapter
import com.example.ejercicio_app.models.DetalleRutinaRealizadaViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

@AndroidEntryPoint
class DetalleRutinaRealizadaFragment : Fragment() {

    private var _binding: FragmentDetalleRutinaRealizadaBinding? = null
    private val binding get() = _binding!!

    private lateinit var ejercicioAdapter: RutinaRealizadaEjerciciosAdapter
    private val vm: DetalleRutinaRealizadaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetalleRutinaRealizadaBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString("rutinaId") ?: ""

        ejercicioAdapter = RutinaRealizadaEjerciciosAdapter(::verDetalle)

        binding.rvEjercicios.layoutManager = LinearLayoutManager(requireContext())
        binding.rvEjercicios.adapter = ejercicioAdapter

        vm.obtenerRutina(id)

        vm.rutina.observe(viewLifecycleOwner) { r ->
            binding.tvNombreRutina.text = r.nombre
            binding.tvParteAnatomica.text = ParteAnatomica.obtenerParteAnatomica(r.parteAnatomica)

            val dia = DayOfWeek.of(r.diaSemana).getDisplayName(TextStyle.FULL, Locale.getDefault())

            binding.tvDiaFecha.text = "$dia - ${r.fechaRealizacion}"
            binding.tvTiempoRealizado.text = "${DateUtils.formatElapsedTime(r.tiempoRealizacion.toLong())}"

            ejercicioAdapter.actualizarEjercicios(r.detalle.map { d -> d.ejercicio })
        }
    }

    private fun verDetalle(id: String) {
        val datos = Bundle().apply {
            putString("ejercicioId", id)
        }

        findNavController().navigate(
            R.id.action_detalleRutinaRealizadaFragment_to_detalleEjercicioRealizadoFragment,
            datos
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}