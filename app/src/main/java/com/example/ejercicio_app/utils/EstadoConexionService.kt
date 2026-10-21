package com.example.ejercicio_app.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EstadoConexionService @Inject constructor() {
    private val _estaConectado: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }

    val estaConectado: LiveData<Boolean> get() = _estaConectado

    fun setEsstadoConexion(estado: Boolean) {
        _estaConectado.postValue(estado)
    }
}