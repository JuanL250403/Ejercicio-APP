package com.example.ejercicio_app.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ejercicio_app.activities.adapters.RutinasActualesListaAdapter
import com.example.ejercicio_app.databinding.ActivityRutinasBinding
import com.example.ejercicio_app.models.RutinaActualViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RutinasActuales : AppCompatActivity() {
    private lateinit var binding: ActivityRutinasBinding

    private lateinit var rutinasAdapter: RutinasActualesListaAdapter

    private val vm: RutinaActualViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRutinasBinding.inflate(layoutInflater)


        rutinasAdapter = RutinasActualesListaAdapter()

        binding.rvListaRutinas.layoutManager = LinearLayoutManager(this)
        binding.rvListaRutinas.adapter = rutinasAdapter

        setContentView(binding.root)


        vm.rutinas.observe(this, {r ->
            rutinasAdapter.actualizarLista(r)
        })
    }
}