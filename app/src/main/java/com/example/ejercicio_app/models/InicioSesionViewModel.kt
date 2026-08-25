package com.example.ejercicio_app.models

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejercicio_app.BuildConfig
import com.example.ejercicio_app.R
import com.example.ejercicio_app.data.AuthResponse
import com.example.ejercicio_app.data.UsuarioInicioSesion
import com.example.ejercicio_app.network.EjercicioApi
import com.example.ejercicio_app.views.InicioSesion
import kotlinx.coroutines.launch
import java.io.IOException

class InicioSesionViewModel : ViewModel() {

    val correo: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private val _erroCorreo: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val errorCorreo: LiveData<Int> get() = _erroCorreo

    val contrasena: MutableLiveData<String> by lazy {

        MutableLiveData<String>()
    }

    private val _errorContrasena: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val erroContrasena: LiveData<Int> get() = _errorContrasena

    private val _estadoValidado: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val estadoValidado: LiveData<Int> get() = _estadoValidado

    private val _validado: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val validado: LiveData<Boolean> get() = _validado

    init {
        _validado.value = false
        correo.value = null
        contrasena.value = null
    }


    fun setCorreo(correoNuevo: String) {
        correo.value = correoNuevo
        validarCorreo()
    }

    fun setContrasnea(contrasenaNueva: String) {
        contrasena.value = contrasenaNueva
        validaContrasena()
    }

    private fun validarCorreo(): Boolean {
        if(correo.value.isNullOrBlank()) {
            _erroCorreo.value = R.string.correo_vacio
            return false
        }
        if(!Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$").matches(correo.value.toString())) {
            _erroCorreo.value = R.string.correo_invalido
            return false
        }
        _erroCorreo.value = 0
        return true
    }

    private fun validaContrasena(): Boolean {
        if(contrasena.value.isNullOrBlank()) {
            _errorContrasena.value = R.string.contrasena_vacia
            return false
        }
        _errorContrasena.value = 0
        return true
    }

    private fun validarFormulario(): Boolean {
        val validado = validarCorreo() and validaContrasena()
        return validado
    }

    fun iniciarSesion(){

        if(!validarFormulario()) return

        val usuario = UsuarioInicioSesion(correo.value ?: "", contrasena.value ?: "")

        viewModelScope.launch {

            try {
                val respuesta = EjercicioApi.authService.iniciarSesion(usuario)

                if (respuesta.isSuccessful) {
                    _estadoValidado.value = R.string.sesion_iniciada
                    _validado.value = true
                } else  {
                    _estadoValidado.value = R.string.credenciales_invalidas
                }

            } catch (e: Exception) {
                Log.d("inicio", e.message.toString())
            }

        }

    }
}