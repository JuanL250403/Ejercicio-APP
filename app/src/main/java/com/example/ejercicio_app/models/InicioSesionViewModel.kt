package com.example.ejercicio_app.models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ejercicio_app.data.UsuarioInicioSesion

class InicioSesionViewModel: ViewModel() {
    val correo: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val contrasena: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun setCorreo(correoNuevo: String) {
        correo.value = correoNuevo
    }

    fun setContrasnea(contrasenaNueva: String) {
        contrasena.value = contrasenaNueva
    }

    fun iniciarSesion() {
        val usuario = UsuarioInicioSesion(correo.value ?: "", contrasena.value ?: "")

        Log.d("inicio", usuario.toString())
    }
}