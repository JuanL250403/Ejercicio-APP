package com.example.ejercicio_app.fragments

import android.os.Bundle
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ejercicio_app.R
import com.example.ejercicio_app.databinding.FragmentRealizarEntrenamientoBinding
import com.example.ejercicio_app.fragments.adapters.RutinaActualEjerciciosAdapter
import com.example.ejercicio_app.models.RealizarEntrenamientoSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

@AndroidEntryPoint
class RealizarEntrenamientoFragment : Fragment() {

    private var _binding: FragmentRealizarEntrenamientoBinding? = null

    private val binding get() = _binding!!

    private lateinit var ejerciciosAdapter: RutinaActualEjerciciosAdapter

    private val vmShared: RealizarEntrenamientoSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRealizarEntrenamientoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val diaSemanaId = arguments?.getString("diaId") ?: ""

        ejerciciosAdapter = RutinaActualEjerciciosAdapter(::verDetalle)

        val diaSemanaExistente = vmShared.diaSemana.value ?: null

        if (diaSemanaExistente == null) {
            vmShared.obtenerDiaSemana(diaSemanaId)
        }

        binding.rvEjercicios.adapter = ejerciciosAdapter
        binding.rvEjercicios.layoutManager = LinearLayoutManager(requireContext())

        binding.btnTemporizador.setOnClickListener {
            if (vmShared.iniciado.value!! && vmShared.corriendo.value!!) {
                vmShared.setCorriendo(false)
                vmShared.setIniciado(false)
            } else {
                vmShared.setIniciado(true)
                vmShared.setCorriendo(true)
            }
        }

        binding.btnTerminar.setOnClickListener {
            vmShared.guardarRutinaRealizada()
            vmShared.setIniciado(false)
            vmShared.setCorriendo(false)
        }

        vmShared.iniciado.observe(viewLifecycleOwner) {i ->
            if(i) {
                binding.btnTemporizador.visibility = View.GONE
                binding.btnTerminar.visibility = View.VISIBLE
            } else {
                binding.btnTerminar.visibility = View.GONE
                binding.btnTemporizador.visibility = View.VISIBLE
            }
        }

        vmShared.diaSemana.observe(viewLifecycleOwner) { d ->

            val dia = DayOfWeek.of(d.diaSemana).getDisplayName(TextStyle.FULL, Locale.getDefault())

            binding.tvDiaRutina.text = "${dia} - ${d.rutina.nombre}"
            ejerciciosAdapter.actualizarEjercicios(d.rutina.detalle.map { d -> d.ejercicio })
        }

        vmShared.tiempo.observe(viewLifecycleOwner) { t ->
            binding.tvCronometro.text = DateUtils.formatElapsedTime(t)
        }

        vmShared.guardado.observe(viewLifecycleOwner) { g ->
            if (g) {
                findNavController().navigate(R.id.action_realizarEntrenamientoFragment_to_calendarioEntrenamientoFragment)
                vmShared._guardado.value = false
            }
        }

        vmShared._guardado.value = false
    }

    private fun verDetalle(id: String) {
        val datos = Bundle().apply {
            putString("ejercicioId", id)
        }

        findNavController().navigate(
            R.id.action_realizarEntrenamientoFragment_to_detalleEjercicioFragment,
            datos
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}