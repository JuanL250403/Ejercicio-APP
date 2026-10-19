package com.example.ejercicio_app.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejercicio_app.data.network.dto.Recurso
import com.example.ejercicio_app.data.repository.DiasSemanaRepository
import com.example.ejercicio_app.data.ui.DiaSemanaDetalleUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class DiaEntrenamientoViewModel @Inject constructor(
    private val diasSemanaRepository: DiasSemanaRepository
) : ViewModel() {

    private val _diaSemana: MutableLiveData<DiaSemanaDetalleUiModel> by lazy {
        MutableLiveData()
    }

    val diaSemana: LiveData<DiaSemanaDetalleUiModel> get() = _diaSemana


    private val _dia: MutableLiveData<Int> by lazy {
        MutableLiveData()
    }

    val dia: LiveData<Int> get() = _dia

    private val _eliminado: MutableLiveData<Boolean> by lazy {
        MutableLiveData()
    }

    val eliminado: LiveData<Boolean> get() = _eliminado

    fun setDia(dia: Int) {
        this._dia.value = dia
    }

    fun cargarDiaSemana(diaId: String) {

        viewModelScope.launch {
            diasSemanaRepository.obtenerDiaSemana(diaId).collect { r ->
                when(r) {
                    is Recurso.Exito -> {
                        _diaSemana.value = r.data
                    }
                    is Recurso.Error -> _diaSemana.value = null
                    else -> null
                }
            }
        }
    }

    fun eliminarDiaSemana() {
        viewModelScope.launch {
            diasSemanaRepository.eliminarDiaSemana(diaSemana.value?.id!!).collect { r ->
                when(r) {
                    is Recurso.Exito -> _eliminado.value = true
                    else ->  null
                }
            }
        }
    }
}