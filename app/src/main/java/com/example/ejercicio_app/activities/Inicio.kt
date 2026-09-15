package com.example.ejercicio_app.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ejercicio_app.R
import com.example.ejercicio_app.databinding.ActivityInicioBinding
import com.example.ejercicio_app.databinding.ActivityInicioSesionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Inicio : AppCompatActivity() {

    private lateinit var binding: ActivityInicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInicioBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnRutinas.setOnClickListener {
            navegarRutinasActuales()
        }
    }

    fun navegarRutinasActuales() {
        val rutinas = Intent(this, RutinasActuales::class.java)

        startActivity(rutinas)
        finish()
    }
}