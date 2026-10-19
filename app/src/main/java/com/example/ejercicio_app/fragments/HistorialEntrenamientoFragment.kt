package com.example.ejercicio_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicio_app.R
import com.example.ejercicio_app.databinding.FragmentHistorialEntrenamientoBinding
import com.example.ejercicio_app.fragments.adapters.RutinasRealizadasListaAdapter
import com.example.ejercicio_app.models.HistorialEntrenamientoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistorialEntrenamientoFragment : Fragment() {

    private var _binding: FragmentHistorialEntrenamientoBinding? = null

    private val binding get() = _binding!!

    private val vm: HistorialEntrenamientoViewModel by viewModels()

    private lateinit var rutinaAdapter: RutinasRealizadasListaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHistorialEntrenamientoBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rutinaAdapter = RutinasRealizadasListaAdapter(::verDetalle)

        binding.rvListaRutinas.adapter = rutinaAdapter
        binding.rvListaRutinas.layoutManager = LinearLayoutManager(requireContext())

        vm.rutinas.observe(viewLifecycleOwner) {r ->
            rutinaAdapter.actualizarLista(r)
        }

        vm.obtenerHistorial()
    }

    private fun verDetalle(id: String) {
        val datos = Bundle().apply {
            putString("rutinaId", id)
        }

        findNavController().navigate(
            R.id.action_historialEntrenamientoFragment_to_detalleRutinaRealizadaFragment,
            datos
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}