package com.example.ejercicio_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ejercicio_app.R
import com.example.ejercicio_app.databinding.FragmentCalendarioEntrenamienoBinding
import com.example.ejercicio_app.fragments.adapters.DiasSemanaAdapter
import com.example.ejercicio_app.models.CalendarioEntrenamientoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalendarioEntrenamientoFragment : Fragment() {

    private var _binding: FragmentCalendarioEntrenamienoBinding? = null

    private val binding get() = _binding!!

    private val vm: CalendarioEntrenamientoViewModel by viewModels()

    private lateinit var diasSemanaAdapter: DiasSemanaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarioEntrenamienoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        diasSemanaAdapter = DiasSemanaAdapter(::verDetalle)

        binding.rvDiasSemana.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDiasSemana.adapter = diasSemanaAdapter

        vm.dias.observe(viewLifecycleOwner) { d ->
            diasSemanaAdapter.actualizarDias(d)
        }

        vm.obtenerDiasSemana()

    }

    private fun verDetalle(diaId: String, diaSemana: Int) {
        val datos = Bundle().apply {
            putString("diaId", diaId)
            putInt("diaSemana", diaSemana)
        }

        findNavController().navigate(
            R.id.action_calendarioEntrenamientoFragment_to_diaEntrenamientoFragment,
            datos
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}