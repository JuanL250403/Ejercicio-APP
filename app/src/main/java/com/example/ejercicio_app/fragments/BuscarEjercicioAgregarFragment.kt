package com.example.ejercicio_app.fragments

import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ejercicio_app.R
import com.example.ejercicio_app.data.ui.ParteAnatomica
import com.example.ejercicio_app.data.ParteCuerpo
import com.example.ejercicio_app.databinding.FragmentBuscarEjercicioAgregarBinding
import com.example.ejercicio_app.fragments.adapters.BuscarEjercicioAgregarAdapter
import com.example.ejercicio_app.models.BuscarEjercicioAgregarViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BuscarEjercicioAgregarFragment : Fragment() {

    private var _binding: FragmentBuscarEjercicioAgregarBinding? = null

    private val binding get() = _binding!!

    private val vm: BuscarEjercicioAgregarViewModel by viewModels()

    private lateinit var ejercicioAdapter: BuscarEjercicioAgregarAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBuscarEjercicioAgregarBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ejercicioAdapter = BuscarEjercicioAgregarAdapter(::agregarEjercicio)

        binding.rvEjercicios.layoutManager = LinearLayoutManager(requireContext())
        binding.rvEjercicios.adapter = ejercicioAdapter

        binding.btnTrenInf.setOnClickListener {
            vm.setParteAnatomica(ParteAnatomica.TREN_INFERIOR)
            vm.setCategoria("todo")
        }

        binding.btnTrenSup.setOnClickListener {
            vm.setParteAnatomica(ParteAnatomica.TREN_SUPERIOR)
            vm.setCategoria("todo")
        }

        binding.etBusqueda.doAfterTextChanged { t ->
            vm.setBusqueda(t.toString())
        }

        vm.busqueda.observe(viewLifecycleOwner) {
            vm.buscarEjercicios()
        }

        vm.ejercicios.observe(viewLifecycleOwner) { ejs ->
            ejercicioAdapter.actualizarEjercicios(vm.ejercicios.value ?: emptyList())
        }

        vm.categoria.observe(viewLifecycleOwner) {
            renderizarCategorias(vm.categorias.value!!)
            vm.cargarEjercicioFiltrados()
            ejercicioAdapter.actualizarEjercicios(vm.ejercicios.value ?: emptyList())
        }

        vm.parteAnatomica.observe(viewLifecycleOwner) {
            vm.cargarCategorias()
            renderizarCategorias(vm.categorias.value ?: emptyList())
        }

    }

    private fun renderizarCategorias(partes: List<ParteCuerpo>) {
        binding.ltCategorias.removeAllViews()

        val botonTodo = crearBoton("todo") {
            vm.setCategoria("todo")
        }

        binding.ltCategorias.addView(botonTodo)

        for (parte in partes) {

            val boton = crearBoton(parte.nombreEspaniol) {
                vm.setCategoria(parte.nombreIngles)
            }


            binding.ltCategorias.addView(boton)
        }
    }

    private fun crearBoton(texto: String, onCLick: () -> Unit): Button {
        return Button(ContextThemeWrapper(requireContext(), R.style.Form_Button_Categoria_Parte_Anatomica))
            .apply {
                text = texto

                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                )

                setOnClickListener {
                    onCLick()
                }
            }

    }

    fun agregarEjercicio(ejercicioId: String) {
        val datos = Bundle().apply {
            putString("ejercicioId", ejercicioId)
        }

        findNavController().navigate(
            R.id.action_buscarEjercicioAgregarFragment_to_agregarEjercicioFragment,
            datos
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}