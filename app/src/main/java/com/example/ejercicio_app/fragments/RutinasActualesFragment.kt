package com.example.ejercicio_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ejercicio_app.R
import com.example.ejercicio_app.fragments.adapters.RutinasActualesListaAdapter
import com.example.ejercicio_app.databinding.FragmentRutinasActualesBinding
import com.example.ejercicio_app.models.RutinaActualViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RutinasActualesFragment : Fragment() {

    private var _binding: FragmentRutinasActualesBinding? = null

    private val binding get() = _binding!!

    private lateinit var rutinasAdapter: RutinasActualesListaAdapter

    private val vm: RutinaActualViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentRutinasActualesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rutinasAdapter = RutinasActualesListaAdapter(::verDetalle)

        binding.rvListaRutinas.layoutManager = LinearLayoutManager(requireContext())
        binding.rvListaRutinas.adapter = rutinasAdapter


        vm.rutinas.observe(viewLifecycleOwner) { r ->
            rutinasAdapter.actualizarLista(r)

        }

        binding.etBuscar.doAfterTextChanged { i ->
            vm.obtenerRutinas(i.toString())
        }

        binding.btnAgregar.setOnClickListener {
            findNavController().navigate(R.id.action_rutinasActualesFragment_to_agregarRutinaActualFragment)
        }

        vm.obtenerRutinas()
    }

    private fun verDetalle(rutinaId: String) {
        val datos = Bundle().apply {
            putString("rutinaId", rutinaId)
        }

        findNavController().navigate(
            R.id.action_rutinasActualesFragment_to_detalleRutinaActualFragment,
            datos
        )
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

}