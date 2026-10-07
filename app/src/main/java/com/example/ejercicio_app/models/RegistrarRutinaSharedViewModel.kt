package com.example.ejercicio_app.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ejercicio_app.data.network.dto.request.EjercicioActualRequest
import com.example.ejercicio_app.data.ui.EjercicioActualUiModel
import dagger.hilt.android.lifecycle.HiltViewModel


class RegistrarRutinaSharedViewModel: ViewModel() {

    val ejercicios: MutableLiveData<List<EjercicioActualUiModel>> by lazy {
        MutableLiveData(emptyList())
    }

    fun agregarEjercicio(ejercicio: EjercicioActualUiModel) {
        val ejerciciosEdit = ejercicios.value.orEmpty().toMutableList()

        ejerciciosEdit.add(ejercicio)

        ejercicios.value = ejerciciosEdit
    }

    fun limpiarEjercicios() {
        ejercicios.value = listOf()
    }

}