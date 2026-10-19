package com.example.ejercicio_app.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejercicio_app.data.network.dto.Recurso
import com.example.ejercicio_app.data.repository.RutinaActualRepository
import com.example.ejercicio_app.data.repository.RutinaRealizadaRepository
import com.example.ejercicio_app.data.ui.RutinaRealizadaUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistorialEntrenamientoViewModel @Inject constructor(
    private val rutinaRealizadaRepository: RutinaRealizadaRepository
) : ViewModel() {

    private val _rutinas: MutableLiveData<List<RutinaRealizadaUiModel>> by lazy {
        MutableLiveData()
    }

    val rutinas: LiveData<List<RutinaRealizadaUiModel>> get() = _rutinas

    fun obtenerHistorial() {

        viewModelScope.launch {
            rutinaRealizadaRepository.obtenerRutinas().collect { r ->
                when (r) {
                    is Recurso.Exito -> _rutinas.value = r.data
                    else -> null
                }
            }
        }

    }

}
