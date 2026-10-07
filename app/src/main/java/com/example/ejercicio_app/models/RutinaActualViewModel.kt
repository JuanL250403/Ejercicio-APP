package com.example.ejercicio_app.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejercicio_app.data.network.dto.Recurso
import com.example.ejercicio_app.data.network.dto.response.RutinaActualResponse
import com.example.ejercicio_app.data.repository.RutinaActualRepository
import com.example.ejercicio_app.data.ui.RutinaActualUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class RutinaActualViewModel @Inject constructor(
    private val rutinaActualRepository: RutinaActualRepository
) : ViewModel() {

    val rutinas: MutableLiveData<List<RutinaActualUiModel>> by lazy {
        MutableLiveData()
    }

    init {
        obtenerRutinas()
    }

    fun obtenerRutinas(nombre: String? = "") {

        viewModelScope.launch {
            rutinaActualRepository.obtenerRutinas(nombre).collect { r ->
                when (r) {
                    is Recurso.Exito -> rutinas.value = r.data
                    else -> null
                }
            };
        }
    }


}