package com.example.ejercicio_app.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejercicio_app.data.network.dto.Recurso
import com.example.ejercicio_app.data.repository.DiasSemanaRepository
import com.example.ejercicio_app.data.repository.RutinaActualRepository
import com.example.ejercicio_app.data.ui.DiaSemanaAgregarUiModel
import com.example.ejercicio_app.data.ui.RutinaActualUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AgregarRutinaDiaViewModel @Inject constructor(
    private val rutinaActualRepository: RutinaActualRepository,
    private val diasSemanaRepository: DiasSemanaRepository
) : ViewModel() {

    private val _rutinas: MutableLiveData<List<RutinaActualUiModel>> by lazy {
        MutableLiveData()
    }

    val rutinas: LiveData<List<RutinaActualUiModel>> get() = _rutinas

    private var dia: Int = 1

    private val _guardado: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }

    val guardado: LiveData<Boolean> get() = _guardado

    init {
        obtenerRutinas()
    }

    fun setDia(dia: Int) {
        this.dia = dia
    }

    fun obtenerRutinas(nombre: String? = "") {

        viewModelScope.launch {
            rutinaActualRepository.obtenerRutinas(nombre).collect { r ->
                when (r) {
                    is Recurso.Exito -> _rutinas.value = r.data
                    else -> null
                }
            };
        }
    }

    fun agregarRutina(rutinaId: String) {
        val diaSemanaCrear = DiaSemanaAgregarUiModel(
            diaSemana = dia,
            rutinaId = rutinaId
        )

        viewModelScope.launch {
            diasSemanaRepository.guardarDiaSemana(diaSemanaCrear).collect { r ->
                when(r) {
                    is Recurso.Exito -> _guardado.value = true
                    else -> null
                }
            }
        }


    }


}