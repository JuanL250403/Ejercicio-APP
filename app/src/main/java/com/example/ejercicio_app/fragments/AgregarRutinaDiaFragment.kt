package com.example.ejercicio_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ejercicio_app.R
import com.example.ejercicio_app.databinding.FragmentAgregarRutinaDiaBinding
import com.example.ejercicio_app.fragments.adapters.RutinasActualesListaAgregarDiaAdapter
import com.example.ejercicio_app.models.AgregarRutinaDiaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgregarRutinaDiaFragment : Fragment() {

    private var _binding: FragmentAgregarRutinaDiaBinding? = null

    private val binding get() = _binding!!

    private val vm: AgregarRutinaDiaViewModel by viewModels()

    private lateinit var rutinasAdapter: RutinasActualesListaAgregarDiaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAgregarRutinaDiaBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val diaSemana = arguments?.getInt("diaSemana") ?: 1

        rutinasAdapter = RutinasActualesListaAgregarDiaAdapter(::agregarRutina)

        binding.etBuscar.doAfterTextChanged { t ->
            vm.obtenerRutinas(t.toString())
        }

        binding.rvListaRutinas.layoutManager = LinearLayoutManager(requireContext())
        binding.rvListaRutinas.adapter = rutinasAdapter

        vm.setDia(diaSemana)

        vm.rutinas.observe(viewLifecycleOwner) { r ->
            rutinasAdapter.actualizarLista(r)
        }

        vm.guardado.observe(viewLifecycleOwner) { g ->
            if (g) findNavController().navigate(R.id.action_agregarRutinaDiaFragment_to_calendarioEntrenamientoFragment)
        }
    }

    fun agregarRutina(rutinaId: String) {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Agregar rutina")
        builder.setMessage("¿Esta seguro de agregar esta rutina?")

        builder.setPositiveButton("Sí") { dialog, which ->
            vm.agregarRutina(rutinaId)
        }

        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

}