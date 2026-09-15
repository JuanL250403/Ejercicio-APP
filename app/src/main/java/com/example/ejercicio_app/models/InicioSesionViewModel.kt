package com.example.ejercicio_app.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejercicio_app.R
import com.example.ejercicio_app.data.Recurso
import com.example.ejercicio_app.data.request.UsuarioInicioSesion
import com.example.ejercicio_app.network.TokenManager
import com.example.ejercicio_app.network.services.AuthService
import com.example.ejercicio_app.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class InicioSesionViewModel @Inject constructor(
    private val authRepository: AuthRepository
    ) : ViewModel() {

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

            authRepository.iniciarSesion(usuario).collect { t ->
                when(t) {
                    is Recurso.Exito -> {
                        _estadoValidado.value = t.data
                        _validado.value = true
                    }
                    is Recurso.Error -> _estadoValidado.value = t.mensaje
                    else -> {}
                }
            }
        }

    }
}