package com.example.ejercicio_app.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejercicio_app.R
import com.example.ejercicio_app.data.UsuarioRegistro
import com.example.ejercicio_app.network.EjercicioApi
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

class RegistroViewModel : ViewModel() {

    val nombre: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private val _errorNombre: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val errorNombre: LiveData<Int> get() = _errorNombre

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

    val fechaNacimiento: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private val _errorFechaNacimiento: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val errorFechaNacimiento: LiveData<Int> get() = _errorFechaNacimiento

    val peso: MutableLiveData<Double> by lazy {
        MutableLiveData<Double>()
    }

    private val _errorPeso: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val errorPeso: LiveData<Int> get() = _errorPeso

    val altura: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    private val _errorAltura: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val errorAltura: LiveData<Int> get() = _errorAltura

    val _estadoRegistro: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val estadoRegistro: LiveData<Int> get() = _estadoRegistro

    init {
        nombre.value = null
        correo.value = null
        contrasena.value = null
        fechaNacimiento.value = null
        peso.value = 0.0
        altura.value = 0
    }

    fun setNombre(nombreNuevo: String) {
        nombre.value = nombreNuevo
        validarNombre()
    }

    fun setCorreo(correoNuevo: String) {
        correo.value = correoNuevo
        validarCorreo()
    }

    fun setContrasena(contrasenaNueva: String) {
        contrasena.value = contrasenaNueva
        validaContrasena()
    }

    fun setFechaNacimiento(fechaNacimientoNuevo: String) {
        fechaNacimiento.value = fechaNacimientoNuevo
        validaFechaNacimiento()
    }

    fun setPeso(pesoNuevo: Double) {
        peso.value = pesoNuevo
        validarPeso()
    }

    fun setAltura(alturaNueva: Int) {
        altura.value = alturaNueva
        validarAltura()
    }

    private fun validarNombre(): Boolean {
        if (nombre.value.isNullOrBlank()) {
            _errorNombre.value = R.string.nombre_vacio
            return false
        }

        _errorNombre.value = 0
        return true
    }

    private fun validaFechaNacimiento(): Boolean {
        if (fechaNacimiento.value.isNullOrBlank()) {
            _errorFechaNacimiento.value = R.string.fecha_nacimiento_vacio
            return false
        }
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        if (date.parse(fechaNacimiento.value).after(Date())) {
            _errorFechaNacimiento.value = R.string.fecha_nacimiento_invalida
            return false
        }

        _errorFechaNacimiento.value = 0
        return true
    }

    private fun validarCorreo(): Boolean {
        Log.d("prueba", correo.value.toString())
        Log.d("prueba", Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$").matches(correo.value.toString()).toString())
        if (correo.value.isNullOrBlank()) {
            _erroCorreo.value = R.string.correo_vacio
            return false
        }
        if (!Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$").matches(correo.value.toString())) {
            _erroCorreo.value = R.string.correo_invalido
            return false
        }
        _erroCorreo.value = 0
        return true
    }

    private fun validaContrasena(): Boolean {
        if (contrasena.value.isNullOrBlank()) {
            _errorContrasena.value = R.string.contrasena_vacia
            return false
        }
        _errorContrasena.value = 0
        return true
    }

    private fun validarPeso(): Boolean {
        val pesoValue = peso.value ?: 0.0
        if (pesoValue <= 0.0) {
            _errorPeso.value = R.string.peso_invalido
            return false
        }

        _errorPeso.value = 0
        return true
    }

    private fun validarAltura(): Boolean {
        val alturaValue = altura.value ?: 0
        if (alturaValue <= 0) {
            _errorAltura.value = R.string.altura_invalida
            return false
        }

        _errorAltura.value = 0
        return true
    }

    private fun validarFormulario(): Boolean {
        return validarNombre() and validarCorreo() and validaContrasena() and validaFechaNacimiento() and validarPeso() and validarAltura()
    }

    fun registrarUsuario() {

        if (!validarFormulario()) return

        val usuario = UsuarioRegistro(
            nombre = nombre.value.toString(),
            correo = correo.value.toString(),
            contrasenia = contrasena.value.toString(),
            fechaNacimiento = fechaNacimiento.value.toString(),
            peso = peso.value?.toDouble() ?: 0.0,
            altura = altura.value?.toInt() ?: 0
        )

        viewModelScope.launch {
            try {
                val respuesta = EjercicioApi.usuarioService.registrarUsuario(usuario)

                if(respuesta.isSuccessful) {
                    _estadoRegistro.value = R.string.usuario_registrado
                } else if(respuesta.code() == 403) {
                    _estadoRegistro.value = R.string.correo_registrado
                }
            } catch (e: Exception) {

            }
        }

    }
}