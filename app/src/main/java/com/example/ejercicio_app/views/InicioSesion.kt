package com.example.ejercicio_app.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.R
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

        binding.etCorreo.doAfterTextChanged { i ->
            vm.setCorreo(i.toString())
        }

        binding.etContrasena.doAfterTextChanged { i ->
            vm.setContrasnea(i.toString())
        }

        binding.registrarse.setOnClickListener {
            registrarse()
        }

        binding.btnEntrar.setOnClickListener {
            vm.iniciarSesion()
        }

        vm.autenticado.observe(this, {autenticado ->
            val mensaje = if(autenticado) "Autenticado" else "Credneciales incorrectas"

            Toast.makeText(this@InicioSesion, mensaje, Toast.LENGTH_SHORT).show()

            if(autenticado) {
                val intent = Intent(this, Inicio::class.java)
                startActivity(intent)
                finish()
            }
        })

        vm.errorCorreo.observe(this, {e ->
            if(e.equals(0)) binding.ltCorreo.error = null
            else binding.ltCorreo.setError(getString(e))
        })

        vm.erroContrasena.observe(this, {e ->
            if(e.equals(0)) binding.ltContrasnea.error = null
            else binding.ltContrasnea.setError(getString(e))
        })

    }

    fun registrarse() {
        val intent = Intent(this, Registro::class.java)

        startActivity(intent)
    }

    fun navegarInicio() {

    }
}