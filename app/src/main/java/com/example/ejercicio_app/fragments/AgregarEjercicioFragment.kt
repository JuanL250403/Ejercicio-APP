package com.example.ejercicio_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.ejercicio_app.R
import com.example.ejercicio_app.databinding.FragmentAgregarEjercicioBinding
import com.example.ejercicio_app.models.AgregarEjercicioViewModel
import com.example.ejercicio_app.models.RegistrarRutinaSharedViewModel
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgregarEjercicioFragment : Fragment() {

    private var _binding: FragmentAgregarEjercicioBinding? = null

    private val binding get() = _binding!!

    private val vmShared: RegistrarRutinaSharedViewModel by activityViewModels()

    private val vm: AgregarEjercicioViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAgregarEjercicioBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString("ejercicioId") ?: ""

        vm.cargarEjercicio(id)

        vm.ejercicio.observe(viewLifecycleOwner) { ej ->
            cargarInstrucciones()
            binding.tvNombreEjercicio.text = ej.name
            cargarVideo()
        }

        binding.etPeso.doAfterTextChanged { i ->
            vm.setPeso(i.toString().toIntOrNull() ?: 0)
        }

        binding.etDescanso.doAfterTextChanged { i ->
            vm.setDescanso(i.toString().toIntOrNull() ?: 0)
        }

        binding.etSeries.doAfterTextChanged { i ->
            vm.setSeries(i.toString().toIntOrNull() ?: 0)
        }

        binding.etReps.doAfterTextChanged { i ->
            vm.setReps(i.toString().toIntOrNull() ?: 0)
        }

        vm.errorPeso.observe(viewLifecycleOwner) { e ->
            validarCampo(binding.ltPeso, e)
        }

        vm.errorDescanso.observe(viewLifecycleOwner) { e ->
            validarCampo(binding.ltDescanso, e)
        }

        vm.errorReps.observe(viewLifecycleOwner) { e ->
            validarCampo(binding.ltReps, e)
        }

        vm.erroSeries.observe(viewLifecycleOwner) { e ->
            validarCampo(binding.ltSeries, e)
        }

        binding.btnAgregar.setOnClickListener {
            agregarEjercicio()
        }

    }

    private fun validarCampo(input: TextInputLayout, error: Int) {
        if(!error.equals(0)) {
            input.isErrorEnabled = true
            input.setError(getString(error))
        } else {
            input.isErrorEnabled = false
            input.error = null
        }

    }

    private fun agregarEjercicio(){
        if (!vm.datosValidos()) return

        val ejercicioCreado = vm.crearEjercicio()

        vmShared.agregarEjercicio(ejercicioCreado)

        if(vm.datosValidos()) findNavController().navigate(R.id.action_agregarEjercicioFragment_to_agregarRutinaActualFragment)
    }

    private fun cargarInstrucciones() {

        val instrucciones = vm.ejercicio.value?.instructionSteps?.get("es") ?: emptyList()

        var listaInstrucciones: String = ""

        for ((i, instruccion) in instrucciones.withIndex()) {
            listaInstrucciones += "${i + 1}. $instruccion\n\n"
        }

        binding.tvInstrucciones.text = listaInstrucciones
    }

    private fun cargarVideo() {
        Glide.with(requireContext())
            .load("file:///android_asset/${vm.ejercicio.value?.gifUrl!!}")
            .error(R.drawable.ic_no_imagen)
            .into(binding.ivVideoDemos)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}