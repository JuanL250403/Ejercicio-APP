package com.example.ejercicio_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.ejercicio_app.R
import com.example.ejercicio_app.databinding.FragmentInicioBinding

class InicioFragment : Fragment() {

    private var _binding: FragmentInicioBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}