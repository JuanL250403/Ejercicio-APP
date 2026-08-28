package com.example.ejercicio_app.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.R
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModel
import com.example.ejercicio_app.databinding.ActivityInicioSesionBinding
import com.example.ejercicio_app.models.InicioSesionViewModel
import com.google.android.material.textfield.TextInputLayout

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

        vm.estadoValidado.observe(this, { estado ->
            Toast.makeText(this@InicioSesion, getString(estado), Toast.LENGTH_SHORT).show()
        })

        vm.validado.observe(this, {validado ->
            if (validado) {
                val intent = Intent(this, Inicio::class.java)
                startActivity(intent)
                finish()
            }
        })

        vm.errorCorreo.observe(this, { e ->
            if (e.equals(0)) removerValidacionCampo(binding.ltCorreo)
            else setValidacionCampo(binding.ltCorreo, e)
        })

        vm.erroContrasena.observe(this, { e ->
            if (e.equals(0)) removerValidacionCampo(binding.ltContrasnea)
            else setValidacionCampo(binding.ltContrasnea, e)
        })

    }

    fun registrarse() {
        val intent = Intent(this, Registro::class.java)
        startActivity(intent)
        finish()
    }

    fun setValidacionCampo(input: TextInputLayout, error: Int) {
        input.isErrorEnabled = true
        input.setError(getString(error))
    }

    fun removerValidacionCampo(input: TextInputLayout) {
        input.error = null
        input.isErrorEnabled = false
    }

}