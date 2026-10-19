package com.example.ejercicio_app.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejercicio_app.data.network.dto.Recurso
import com.example.ejercicio_app.data.repository.RutinaRealizadaRepository
import com.example.ejercicio_app.data.ui.RutinaRealizadaUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DetalleRutinaRealizadaViewModel @Inject constructor(
    private val rutinaRealizadaRepository: RutinaRealizadaRepository
) : ViewModel() {

    private val _rutina: MutableLiveData<RutinaRealizadaUiModel> by lazy {
        MutableLiveData()
    }

    val rutina: LiveData<RutinaRealizadaUiModel> get() = _rutina

    fun obtenerRutina(id: String) {
        viewModelScope.launch {
            rutinaRealizadaRepository.obtenerRutina(id).collect { r ->
                when(r) {
                    is Recurso.Exito -> _rutina.value = r.data
                    else -> null
                }
            }
        }
    }

}