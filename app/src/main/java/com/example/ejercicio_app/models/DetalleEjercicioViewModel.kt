package com.example.ejercicio_app.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejercicio_app.data.network.dto.Recurso
import com.example.ejercicio_app.data.repository.EjercicioActualRepository
import com.example.ejercicio_app.data.repository.EjercicioRepository
import com.example.ejercicio_app.data.ui.EjercicioActualUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DetalleEjercicioViewModel @Inject constructor(
    private val ejercicioActualRepository: EjercicioActualRepository
) : ViewModel() {

    val _ejercicio: MutableLiveData<EjercicioActualUiModel> by lazy {
        MutableLiveData()
    }

    val ejercicio: LiveData<EjercicioActualUiModel> get() = _ejercicio

    fun obtenerEjercicio(id: String) {
        viewModelScope.launch {
            ejercicioActualRepository.obtenerEjercicio(id).collect { r ->
                when(r) {
                    is Recurso.Exito -> _ejercicio.value = r.data
                    else -> null
                }
            }
        }
    }
}