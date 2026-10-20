package com.example.ejercicio_app.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.ejercicio_app.R
import com.example.ejercicio_app.activities.InicioSesion
import com.example.ejercicio_app.databinding.FragmentPerfilBinding
import com.example.ejercicio_app.models.PerfilViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null

    private val binding get() = _binding!!

    private val vm: PerfilViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPerfilBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.perfil.observe(viewLifecycleOwner) { p ->
            binding.tvNombre.text = p.nombre
            binding.tvPeso.text = "${p.peso} KG"
            binding.tvEdad.text = p.edad.toString()
            binding.tvAltura.text = "${p.altura} CM"
        }

        binding.btnCerrarSesion.setOnClickListener {
            vm.cerrarSesion()
            val inicioSesion = Intent(requireContext(), InicioSesion::class.java)

            startActivity(inicioSesion)
            requireActivity().finish()
        }

        vm.obtenerUsuario()

    }


}