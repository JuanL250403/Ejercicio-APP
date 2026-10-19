package com.example.ejercicio_app.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejercicio_app.data.network.dto.Recurso
import com.example.ejercicio_app.data.repository.EjercicioActualRepository
import com.example.ejercicio_app.data.repository.EjercicioRealizadoRepository
import com.example.ejercicio_app.data.ui.EjercicioActualUiModel
import com.example.ejercicio_app.data.ui.EjercicioRealizadoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DetalleEjercicioRealizadoViewModel @Inject constructor(
    private val ejercicioRealizadoRepository: EjercicioRealizadoRepository
) : ViewModel() {

    val _ejercicio: MutableLiveData<EjercicioRealizadoUiModel> by lazy {
        MutableLiveData()
    }

    val ejercicio: LiveData<EjercicioRealizadoUiModel> get() = _ejercicio

    fun obtenerEjercicio(id: String) {
        viewModelScope.launch {
            ejercicioRealizadoRepository.obtenerEjercicio(id).collect { r ->
                when(r) {
                    is Recurso.Exito -> _ejercicio.value = r.data
                    else -> null
                }
            }
        }
    }
}