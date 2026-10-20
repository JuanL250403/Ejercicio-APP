package com.example.ejercicio_app.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.jwt.JWT
import com.example.ejercicio_app.data.network.TokenManager
import com.example.ejercicio_app.data.network.dto.Recurso
import com.example.ejercicio_app.data.repository.SesionRepository
import com.example.ejercicio_app.data.ui.UsuarioUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import javax.inject.Inject

@HiltViewModel
class PerfilViewModel @Inject constructor(
    private val sesionRepository: SesionRepository
) : ViewModel() {

    private val _perfil: MutableLiveData<UsuarioUiModel> by lazy {
        MutableLiveData()
    }

    private val _sesionCerrada: MutableLiveData<Boolean> by lazy {
        MutableLiveData()
    }

    val sesionCerrada: LiveData<Boolean> get() = _sesionCerrada

    val perfil: LiveData<UsuarioUiModel> get() = _perfil


    fun obtenerUsuario() {

        _perfil.value = sesionRepository.obtenerUsuario()!!

    }

    fun cerrarSesion() {
        viewModelScope.launch {
            sesionRepository.cerrarSesion().collect { r ->
                when (r) {
                    is Recurso.Exito -> _sesionCerrada.value = true
                    else -> null
                }
            }
        }
    }

}
