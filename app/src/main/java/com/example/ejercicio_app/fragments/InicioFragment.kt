package com.example.ejercicio_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.ejercicio_app.R
import com.example.ejercicio_app.databinding.FragmentInicioBinding
import com.example.ejercicio_app.models.InicioViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InicioFragment : Fragment() {

    private var _binding: FragmentInicioBinding? = null

    private val binding get() = _binding!!

    private val vm: InicioViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInicioBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnRutinas.setOnClickListener {
            navegarOpcion(R.id.rutinasActualesFragment)
        }

        binding.btnCalendario.setOnClickListener {
            navegarOpcion(R.id.calendarioEntrenamientoFragment)
        }

        binding.btnHistorial.setOnClickListener {
            navegarOpcion(R.id.historialEntrenamientoFragment)
        }

        vm.dia.observe(viewLifecycleOwner) { d ->
            binding.tvRutina.text = d.rutina.nombre
        }

        binding.ltRutinaHoy.setOnClickListener {
            if (vm.dia.value != null) realizarEntrenamiento()
        }

        vm.obtenerRutinaDeDia()
    }

    private fun navegarOpcion(fragmentId: Int) {
        val navOptions = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setRestoreState(true)
            .setPopUpTo(
                findNavController().graph.startDestinationId,
                inclusive = false,
                saveState = true
            )
            .build()

        findNavController().navigate(fragmentId, null, navOptions)
    }

    fun realizarEntrenamiento() {
        val datos = Bundle().apply {
            putString("diaId", vm.dia.value?.id!!)
        }

        findNavController().navigate(
            R.id.action_inicioFragment_to_realizarEntrenamientoFragment,
            datos
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}