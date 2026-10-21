package com.example.ejercicio_app.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EstadoAppService @Inject constructor() {
    private val _cargando: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }

    val cargando: LiveData<Boolean> get() = _cargando

    fun estaCargando(){
        _cargando.value = true
    }

    fun noEstaCargando() {
        _cargando.value = false
    }
}