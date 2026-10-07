package com.example.ejercicio_app.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainConexionViewModel: ViewModel() {
    private val _estaConectado: MutableLiveData<Boolean> by lazy {
        MutableLiveData()
    }

    val estaConectado: LiveData<Boolean> get() = _estaConectado

    fun setEsstadoConexion(estado: Boolean) {
        _estaConectado.postValue(estado)
    }
}