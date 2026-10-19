package com.example.ejercicio_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ejercicio_app.R
import com.example.ejercicio_app.data.ui.ParteAnatomica
import com.example.ejercicio_app.databinding.FragmentDiaEntrenamientoBinding
import com.example.ejercicio_app.models.DiaEntrenamientoViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@AndroidEntryPoint
class DiaEntrenamientoFragment : Fragment() {

    private var _binding: FragmentDiaEntrenamientoBinding? = null

    private val binding get() = _binding!!

    private val vm: DiaEntrenamientoViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDiaEntrenamientoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val diaId = arguments?.getString("diaId") ?: ""
        val diaSemana = arguments?.getInt("diaSemana") ?: 1


        binding.ltAgregarRutina.setOnClickListener {
            agregarRutina()
        }

        binding.btnEliminar.setOnClickListener {
            eliminarDiaSemana()
        }

        binding.btnIniciarEntrenamiento.setOnClickListener {
            realizarEntrenamiento()
        }

        vm.setDia(diaSemana)
        vm.cargarDiaSemana(diaId)

        vm.eliminado.observe(viewLifecycleOwner) { e ->
            if (e) findNavController().navigate(R.id.action_diaEntrenamientoFragment_to_calendarioEntrenamientoFragment)
        }

        vm.dia.observe(viewLifecycleOwner) { d ->
            binding.tvDia.text = DayOfWeek.of(d).getDisplayName(TextStyle.FULL, Locale.getDefault())
        }

        vm.diaSemana.observe(viewLifecycleOwner) { d ->

            if (d != null) {

                binding.ltSinRutina.visibility = View.GONE
                binding.ltCardRutina.visibility = View.VISIBLE

                binding.ltSinRutina.visibility = View.GONE
                binding.ltCardRutina.visibility = View.VISIBLE


                binding.tvRutina.text = d.rutina.nombre
                binding.tvParteAnatomica.text =
                    ParteAnatomica.obtenerParteAnatomica(d.rutina.parteAnatomica)
                binding.tvCantidadEjercicios.text = "Ejercicios: ${d.rutina.detalle.size.toString()}"

                if(d.estado) {
                    binding.btnEliminar.visibility = View.GONE
                }

                if (!LocalDate.now().dayOfWeek.value.equals(d.diaSemana)) {
                    binding.btnIniciarEntrenamiento.visibility = View.GONE
                } else if (d.estado) {
                    binding.btnIniciarEntrenamiento.visibility = View.GONE
                }

            } else {
                binding.ltCardRutina.visibility = View.GONE
                binding.ltSinRutina.visibility = View.VISIBLE
            }
        }
    }

    fun agregarRutina() {
        val datos = Bundle().apply {
            putInt("diaSemana", vm.dia.value!!)
        }

        findNavController().navigate(
            R.id.action_diaEntrenamientoFragment_to_agregarRutinaDiaFragment,
            datos
        )
    }

    fun realizarEntrenamiento() {
        val datos = Bundle().apply {
            putString("diaId", vm.diaSemana.value?.id!!)
        }

        findNavController().navigate(
            R.id.action_diaEntrenamientoFragment_to_realizarEntrenamientoFragment,
            datos
        )
    }

    fun eliminarDiaSemana() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Eliminar dia de entrenamiento")
        builder.setMessage("¿Esta seguro de elimiar el dia de entrenamiento?")

        builder.setPositiveButton("Sí") { dialog, which ->
            vm.eliminarDiaSemana()
        }

        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}