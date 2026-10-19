package com.example.ejercicio_app.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejercicio_app.data.network.dto.Recurso
import com.example.ejercicio_app.data.repository.DiasSemanaRepository
import com.example.ejercicio_app.data.ui.DiaSemanaDetalleUiModel
import com.example.ejercicio_app.data.ui.DiaSemanaUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarioEntrenamientoViewModel @Inject constructor(
    private val diasSemanaRepository: DiasSemanaRepository
) : ViewModel() {

    val dias: MutableLiveData<List<DiaSemanaUiModel>> by lazy {
        MutableLiveData()
    }

    fun obtenerDiasSemana() {

        viewModelScope.launch {
            diasSemanaRepository.obtenerDiasSemana().collect { r ->
                when (r) {
                    is Recurso.Exito -> dias.value = r.data
                    else -> null
                }
            }
        }
    }
}