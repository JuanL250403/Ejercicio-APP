package com.example.ejercicio_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ejercicio_app.R
import com.example.ejercicio_app.data.ui.ParteAnatomica
import com.example.ejercicio_app.databinding.FragmentAgregarRutinaActualBinding
import com.example.ejercicio_app.fragments.adapters.RutinaActualEjerciciosAdapter
import com.example.ejercicio_app.fragments.adapters.RutinaActualEjerciciosAgregarAdapter
import com.example.ejercicio_app.models.AgregarRutinaActualViewModel
import com.example.ejercicio_app.models.RegistrarRutinaSharedViewModel
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgregarRutinaActualFragment : Fragment() {

    private var _binding: FragmentAgregarRutinaActualBinding? = null

    private val binding get() = _binding!!

    private val vm: AgregarRutinaActualViewModel by viewModels()

    private val vmShared: RegistrarRutinaSharedViewModel by activityViewModels()

    private lateinit var ejerciciosAdapter: RutinaActualEjerciciosAgregarAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAgregarRutinaActualBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ejerciciosAdapter = RutinaActualEjerciciosAgregarAdapter()

        binding.rvEjercicios.layoutManager = LinearLayoutManager(requireContext())
        binding.rvEjercicios.adapter = ejerciciosAdapter

        vmShared.ejercicios.observe(viewLifecycleOwner) { ejs ->
            ejerciciosAdapter.actualizarEjercicios(ejs)
            vm.ejercicios = ejs
        }

        vm.errorNombre.observe(viewLifecycleOwner) { e ->
            validarCampo(binding.ltNombreRutina, e)
        }

        vm.errorParteAnatomica.observe(viewLifecycleOwner) { e ->
            if (!e.equals(0)) Toast.makeText(requireContext(), getString(e), Toast.LENGTH_SHORT)
                .show()

        }

        vm.errorEjercicios.observe(viewLifecycleOwner) { e ->
            if (!e.equals(0)) Toast.makeText(requireContext(), getString(e), Toast.LENGTH_SHORT)
                .show()
        }

        vm.guardada.observe(viewLifecycleOwner) {g ->
            if(g) {
                vmShared.limpiarEjercicios()
                findNavController().navigate(R.id.action_agregarRutinaActualFragment_to_rutinasActualesFragment)
            }
        }

        binding.etNombreRutina.doAfterTextChanged { i ->
            vm.setNombre(i.toString())
        }


        binding.btnTrenSup.setOnClickListener {
            binding.btnTrenSup.setStrokeColor(ContextCompat.getColor(requireContext(),R.color.primario))
            binding.btnTrenInf.setStrokeColor(ContextCompat.getColor(requireContext(),R.color.terciario))
            binding.btnCuerpoComp.setStrokeColor(ContextCompat.getColor(requireContext(),R.color.terciario))
            vm.parteAnatomica = ParteAnatomica.TREN_SUPERIOR
        }

        binding.btnTrenInf.setOnClickListener {
            binding.btnTrenSup.setStrokeColor(ContextCompat.getColor(requireContext(),R.color.terciario))
            binding.btnTrenInf.setStrokeColor(ContextCompat.getColor(requireContext(),R.color.primario))
            binding.btnCuerpoComp.setStrokeColor(ContextCompat.getColor(requireContext(),R.color.terciario))
            vm.parteAnatomica = ParteAnatomica.TREN_INFERIOR
        }

        binding.btnCuerpoComp.setOnClickListener {
            binding.btnTrenSup.setStrokeColor(ContextCompat.getColor(requireContext(),R.color.terciario))
            binding.btnTrenInf.setStrokeColor(ContextCompat.getColor(requireContext(),R.color.terciario))
            binding.btnCuerpoComp.setStrokeColor(ContextCompat.getColor(requireContext(),R.color.primario))
            vm.parteAnatomica = ParteAnatomica.CUERPO_COMPLETO
        }

        binding.tvAgregar.setOnClickListener {
            findNavController().navigate(R.id.action_agregarRutinaActualFragment_to_buscarEjercicioAgregarFragment)
        }

        binding.btnGuardar.setOnClickListener {
            vm.guardarRutina()


        }
    }

    private fun validarCampo(input: TextInputLayout, error: Int) {
        if (!error.equals(0)) {
            input.isErrorEnabled = true
            input.setError(getString(error))
        } else {
            input.isErrorEnabled = false
            input.error = null
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}