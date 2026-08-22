package com.example.ejercicio_app.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModel
import com.example.ejercicio_app.databinding.ActivityInicioSesionBinding
import com.example.ejercicio_app.models.InicioSesionViewModel

class InicioSesion : AppCompatActivity() {
    private lateinit var binding: ActivityInicioSesionBinding

    private val vm: InicioSesionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInicioSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.correo.doAfterTextChanged { i ->
            vm.setCorreo(i.toString())
        }

        binding.contrasena.doAfterTextChanged { i ->
            vm.setContrasnea(i.toString())
        }

        binding.registrarse.setOnClickListener {
            registrarse()
        }

        binding.entrar.setOnClickListener {
            vm.iniciarSesion()
        }
    }

    fun registrarse() {
        val intent = Intent(this, Registro::class.java)

        startActivity(intent)
    }
}