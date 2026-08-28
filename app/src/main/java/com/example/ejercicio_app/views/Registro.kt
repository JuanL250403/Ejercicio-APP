package com.example.ejercicio_app.views

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.ejercicio_app.databinding.ActivityRegistroBinding
import com.example.ejercicio_app.models.RegistroViewModel
import com.google.android.material.textfield.TextInputLayout

class Registro : AppCompatActivity() {
    lateinit var binding: ActivityRegistroBinding

    val vm: RegistroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etNombre.doAfterTextChanged { i ->
            vm.setNombre(i.toString())
        }

        binding.etCorreo.doAfterTextChanged { i ->
            vm.setCorreo(i.toString())
        }

        binding.etContrasena.doAfterTextChanged { i ->
            vm.setContrasena(i.toString())
        }

        binding.etPeso.doAfterTextChanged { i ->
            vm.setPeso(i.toString().toDoubleOrNull() ?: 0.0)
        }

        binding.etAltura.doAfterTextChanged { i ->
            vm.setAltura(i.toString().toIntOrNull() ?: 0)
        }

        binding.etFechaNacimiento.setOnClickListener {
            seleccionarFecha()
        }

        vm.errorNombre.observe(this, { e ->
            if(e.equals(0)) removerValidacionCampo(binding.ltNombre)
            else setValidacionCampo(binding.ltNombre, e)
        })

        vm.erroContrasena.observe(this, {e ->
            if(e.equals(0)) removerValidacionCampo(binding.ltContrasena)
            else setValidacionCampo(binding.ltContrasena, e)
        })

        vm.errorCorreo.observe(this, {e ->
            if(e.equals(0)) removerValidacionCampo(binding.ltCorreo)
            else setValidacionCampo(binding.ltCorreo, e)
        })

        vm.errorFechaNacimiento.observe(this, {e ->
            if(e.equals(0)) removerValidacionCampo(binding.ltFechaNacimiento)
            else setValidacionCampo(binding.ltFechaNacimiento, e)
        })

        vm.errorPeso.observe(this, {e ->
            if(e.equals(0)) removerValidacionCampo(binding.ltPeso)
            else setValidacionCampo(binding.ltPeso, e)
        })

        vm.errorAltura.observe(this, {e ->
            if(e.equals(0)) removerValidacionCampo(binding.ltAltura)
            else setValidacionCampo(binding.ltAltura, e)
        })

        vm.estadoRegistro.observe(this, {estado ->
            Toast.makeText(this@Registro, getString(estado), Toast.LENGTH_SHORT).show()
        })

        binding.btnRegistrar.setOnClickListener {
            vm.registrarUsuario()
        }

        binding.btnIniciarSesion.setOnClickListener {
            val intent = Intent(this, InicioSesion::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun seleccionarFecha() {
        val calendario = Calendar.getInstance()

        val anio = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this, null, anio, mes, dia)

        datePicker.setOnDateSetListener { picker, anio, mes, dia ->
            val fecha = "$anio-${"%02d".format(mes)}-${"%02d".format(dia)}"
            vm.setFechaNacimiento(fecha)
            binding.etFechaNacimiento.setText(fecha)
        }

        datePicker.show()
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