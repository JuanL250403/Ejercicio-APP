package com.example.ejercicio_app.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejercicio_app.data.network.dto.Recurso
import com.example.ejercicio_app.data.repository.RutinaActualRepository
import com.example.ejercicio_app.data.ui.RutinaActualUiModel
import com.example.ejercicio_app.utils.EstadoAppService
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DetalleRutinaActualViewModel @Inject constructor(
    private val estadoAppService: EstadoAppService,
    private val rutinaActualRepository: RutinaActualRepository
) :
    ViewModel() {
    val _rutina: MutableLiveData<RutinaActualUiModel> by lazy {
        MutableLiveData()
    }

    val rutina: LiveData<RutinaActualUiModel> get() = _rutina

    val _eliminado: MutableLiveData<Boolean> by lazy {
        MutableLiveData()
    }

    val eliminado: LiveData<Boolean> get() = _eliminado


    fun obtenerRutina(id: String) {
        viewModelScope.launch {
            rutinaActualRepository.obtenerRutina(id).collect { r ->
                when (r) {
                    is Recurso.Exito -> {
                        _rutina.value = r.data
                    }

                    else -> null
                }
            }
        }
    }

    fun eliminarRutina() {
        viewModelScope.launch {
            rutinaActualRepository.eliminarRutina(rutina.value?.id!!).collect { r ->
                when (r) {
                    is Recurso.Cargando -> estadoAppService.estaCargando()
                    is Recurso.Exito -> {
                        _eliminado.value = true
                        estadoAppService.noEstaCargando()
                    }
                    is Recurso.Error -> estadoAppService.noEstaCargando()
                    else -> null
                }
            }
        }
    }
}