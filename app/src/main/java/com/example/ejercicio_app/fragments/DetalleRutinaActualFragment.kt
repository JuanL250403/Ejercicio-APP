package com.example.ejercicio_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ejercicio_app.R
import com.example.ejercicio_app.data.ui.DetalleRutinaActualUiModel
import com.example.ejercicio_app.data.ui.ParteAnatomica
import com.example.ejercicio_app.databinding.FragmentDetalleRutinaActualBinding
import com.example.ejercicio_app.fragments.adapters.RutinaActualEjerciciosAdapter
import com.example.ejercicio_app.models.DetalleRutinaActualViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetalleRutinaActualFragment : Fragment() {

    private var _binding: FragmentDetalleRutinaActualBinding? = null

    private val binding get() = _binding!!

    private val vm: DetalleRutinaActualViewModel by viewModels()

    private lateinit var adapter: RutinaActualEjerciciosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetalleRutinaActualBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getString("rutinaId") ?: ""

        adapter = RutinaActualEjerciciosAdapter(::verDetalle)

        binding.rvEjercicios.layoutManager = LinearLayoutManager(requireContext())
        binding.rvEjercicios.adapter = adapter

        binding.btnEliminar.setOnClickListener {
            eliminarRutina()
        }

        vm.obtenerRutina(id)

        vm.eliminado.observe(viewLifecycleOwner) {e ->
            if(e) findNavController().navigate(R.id.action_detalleRutinaActualFragment_to_rutinasActualesFragment)
        }

        vm.rutina.observe(viewLifecycleOwner) { r ->
            binding.tvNombreRutina.text = r.nombre
            binding.tvParteAnatomica.text = ParteAnatomica.obtenerParteAnatomica(r.parteAnatomica)

            adapter.actualizarEjercicios(r.detalle.map { d -> d.ejercicio })
        }
    }

    private fun eliminarRutina() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Eliminar rutina")
        builder.setMessage("¿Esta seguro de eliminar la rutina?")

        builder.setPositiveButton("Sí") { dialog, which ->
            vm.eliminarRutina()
        }

        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun verDetalle(id: String) {
        val datos = Bundle().apply {
            putString("ejercicioId", id)
        }

        findNavController().navigate(
            R.id.action_detalleRutinaActualFragment_to_detalleEjercicioFragment,
            datos
        )
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}