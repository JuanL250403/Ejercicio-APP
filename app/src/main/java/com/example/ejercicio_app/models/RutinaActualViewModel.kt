package com.example.ejercicio_app.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejercicio_app.activities.RutinasActuales
import com.example.ejercicio_app.data.Recurso
import com.example.ejercicio_app.data.response.RutinaActualResponse
import com.example.ejercicio_app.repository.RutinaActualRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class RutinaActualViewModel @Inject constructor(
    private val rutinaActualRepository: RutinaActualRepository
) : ViewModel() {

    val rutinas: MutableLiveData<List<RutinaActualResponse>> by lazy {
        MutableLiveData<List<RutinaActualResponse>>()
    }

    init {
        obtenerRutinas()
    }

    fun obtenerRutinas() {

        viewModelScope.launch {
            rutinaActualRepository.obtenerRutinas().collect { t ->
                when (t) {
                    is Recurso.Exito -> rutinas.value = t.data
                    else -> null
                }
            };
        }
    }
}